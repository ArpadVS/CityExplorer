package com.sbnz.CityExplorer.service;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.drools.template.ObjectDataCompiler;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.QueryResults;
import org.kie.api.runtime.rule.QueryResultsRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.sbnz.CityExplorer.converter.ActivityDTOConverter;
import com.sbnz.CityExplorer.dto.ActivityDTO;
import com.sbnz.CityExplorer.dto.RatingDTO;
import com.sbnz.CityExplorer.dto.ReportDTO;
import com.sbnz.CityExplorer.dto.SearchDTO;
import com.sbnz.CityExplorer.dto.UserRequirementsDTO;
import com.sbnz.CityExplorer.events.RatingEvent;
import com.sbnz.CityExplorer.model.Activity;
import com.sbnz.CityExplorer.model.ActivityRequirements;
import com.sbnz.CityExplorer.model.Rating;
import com.sbnz.CityExplorer.model.RegisteredUser;
import com.sbnz.CityExplorer.repository.ActivityRepository;
import com.sbnz.CityExplorer.repository.RatingRepository;
import com.sbnz.CityExplorer.repository.UserRepository;
import com.sbnz.CityExplorer.util.ScoreCalculator;

@Service
public class ActivityService {
	@Autowired
	ActivityRepository activityRepository;
	@Autowired
	ScoreCalculator scoreCalculator;
	@Autowired
	UserRepository userRepository;
	@Autowired
	RatingRepository ratingRepository;
	@Autowired
	DroolsService droolsService;

	public List<ActivityDTO> getAll() {
		List<Activity> activities = activityRepository.findAll();
		return (activities.stream().map(activity -> {
			ActivityDTO dto = ActivityDTOConverter.convertToDTO(activity);
			return dto;
		})).collect(Collectors.toList());
	}
	
	public boolean saveActivity(ActivityDTO dto) {
		Activity activity = ActivityDTOConverter.convertFromDTO(dto);
		System.out.println(activity.toString() + "created");
		try {
			activityRepository.save(activity);
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
	}

	public ActivityDTO getActivity(Long id) {
		Optional<Activity> ActivityOpt = activityRepository.findById(id);
		KieSession kieSession = droolsService.getRulesSession();
		kieSession.getAgenda().getAgendaGroup("queries").setFocus();
		kieSession.insert(ActivityOpt.get());

		ActivityDTO restDTO = null;
		QueryResults results = kieSession.getQueryResults("User ratings by activity", id);

		// getting values from query
		for (QueryResultsRow queryResult : results) {
			Activity activity = (Activity) queryResult.get("$activity");
			
			int totalRatingNum = ((Number) queryResult.get("$ratingNum")).intValue();
			int ratingSum = (int) queryResult.get("$ratingSum");

			// total number of 1-2-3-4-5 ratings
			int ones = ((Number) queryResult.get("$ones")).intValue();
			int twos = ((Number) queryResult.get("$twos")).intValue();
			int threes = ((Number) queryResult.get("$threes")).intValue();
			int fours = ((Number) queryResult.get("$fours")).intValue();
			int fives = ((Number) queryResult.get("$fives")).intValue();

			// checking if current user already gave a rating
			int userReview = 0;
			RegisteredUser loggedUser = getCurrentRegisteredUser();
			if (loggedUser != null) {
				for (Rating rating : activity.getRatings()) {
					if (rating.getRegisteredUser().getId() == loggedUser.getId()) {
						userReview = rating.getRating();
						break;
					}
				}
			}
			double average = 0;
			
			if (totalRatingNum != 0) {
				average = ratingSum * 1.0 / totalRatingNum;
			}
			ReportDTO reportDTO = new ReportDTO(average, totalRatingNum, ones, twos, threes, fours, fives, userReview);
			restDTO = ActivityDTOConverter.convertToDTO(activity, reportDTO);
		}
		return restDTO;
	}

	private RegisteredUser getCurrentRegisteredUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken) && authentication != null) {
			String username = authentication.getName();
			try {
				return (RegisteredUser) userRepository.findOneByUsername(username);
			} catch (ClassCastException e) {
				//return null if admin
				return null;
			}
		}
		return null;
	}

	public List<ActivityDTO> search(SearchDTO searchDto) {
		List<Activity> activities = activityRepository.findAll();
		System.out.println("Number of activities before search = " + activities.size());
		List<Activity> result = new ArrayList<Activity>();
		if (!searchDto.getName().equals("")) {
			InputStream template = ActivityService.class.getResourceAsStream("/templates/search.drt");
			if (template == null) {
				System.out.println("Template is null");
			}
			ObjectDataCompiler converter = new ObjectDataCompiler();
			List<SearchDTO> data = new ArrayList<>();
			data.add(searchDto);
			String drl = converter.compile(data, template);
			System.out.println("\n" + drl + "\n");
			KieSession kieSession = droolsService.createKieSessionFromDRL(drl);
			for (Activity activity : activities) {
				kieSession.insert(activity);
			}
			kieSession.setGlobal("result", result);
			kieSession.fireAllRules();
		} else {
			result = activities;
		}
		return (result.stream().map(activity -> {
			ActivityDTO dto = ActivityDTOConverter.convertToDTO(activity);
			return dto;
		})).collect(Collectors.toList());
	}

	public ActivityDTO getRecommendation(UserRequirementsDTO dto) {
		dto.setDate(LocalDate.now());
		ActivityRequirements requirements = new ActivityRequirements();
		Activity bestScored = new Activity();
		List<Activity> activities = activityRepository.findAll();

		// getting agenda for processing user info
		KieSession kieSession = droolsService.getKieContainer().newKieSession("rulesSession");
		kieSession.getAgenda().getAgendaGroup("recommend").setFocus();
		kieSession.setGlobal("best", bestScored);
		kieSession.setGlobal("calculator", scoreCalculator);
		kieSession.insert(dto);
		kieSession.insert(requirements);

		for (Activity activity : activities) {
			kieSession.insert(activity);
		}
		kieSession.fireAllRules();

		bestScored = (Activity) kieSession.getGlobal("best");
		ActivityDTO retVal = ActivityDTOConverter.convertToDTO(bestScored);
		RegisteredUser currentUser = getCurrentRegisteredUser();
		if (!currentUser.getRecommendedActivities().contains(bestScored)) {
			currentUser.getRecommendedActivities().add(bestScored);
			userRepository.save(currentUser);
		}
		kieSession.destroy();
		this.droolsService.releaseRulesSession();
		return retVal;
	}

	public boolean rateActivity(RatingDTO dto) {
		Activity activity = activityRepository.findById(dto.getActivityId()).get();
		RegisteredUser logged = getCurrentRegisteredUser();

		// User can rate only when something already recommended-checking that
		boolean alreadyRecommended = false;
		Rating newRating = null;

		// check if already recommended
		for (Activity recommendedActivity : logged.getRecommendedActivities()) {
			if (recommendedActivity.getId() == dto.getActivityId()) {
				alreadyRecommended = true;

				// checking if user already rated activity
				for (Rating oldRating : activity.getRatings()) {
					if (oldRating.getRegisteredUser().getId() == logged.getId()) {
						newRating = oldRating;
						break;
					}
				}
				break;
			}
		}

		if (alreadyRecommended) {
			// if no rating from before
			if (newRating == null) {
				newRating = new Rating(dto.getRating(), activity, logged);
			} else {
				newRating.setRating(dto.getRating());
				System.out.println("changedmyrating");
			}
			ratingRepository.save(newRating);
			activity.getRatings().add(newRating);
			RatingEvent ratingEvent = new RatingEvent(new Date(), newRating, logged.getId());

			KieSession kieSession = droolsService.getEventsSession();
			kieSession.insert(ratingEvent);

			kieSession.getAgenda().getAgendaGroup("rating").setFocus();
			kieSession.fireAllRules();
			activityRepository.save(activity);

		} else {
			System.out.println("Can't rate activity which was not recommended before");
		}
		return alreadyRecommended;
	}
}
