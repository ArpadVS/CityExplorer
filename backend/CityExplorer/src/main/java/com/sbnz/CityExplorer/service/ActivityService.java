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
	ScoreCalculator sc;
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

	public ActivityDTO getActivity(Long id) {
		Optional<Activity> ActivityOpt = activityRepository.findById(id);
		KieSession kieSession = droolsService.getRulesSession();
		kieSession.getAgenda().getAgendaGroup("queries").setFocus();
		kieSession.insert(ActivityOpt.get());

		ActivityDTO restDTO = null;
		QueryResults results = kieSession.getQueryResults("User ratings by activity", id);

		// getting values from query
		for (QueryResultsRow qrr : results) {
			Activity activity = (Activity) qrr.get("$activity");
			// total number of ratings
			int ratingNum = ((Number) qrr.get("$ratingNum")).intValue();
			// sum of every rating
			int ratingSum = (int) qrr.get("$ratingSum");

			// total number of 1-2-3-4-5 ratings
			int ones = ((Number) qrr.get("$ones")).intValue();
			int twos = ((Number) qrr.get("$twos")).intValue();
			int threes = ((Number) qrr.get("$threes")).intValue();
			int fours = ((Number) qrr.get("$fours")).intValue();
			int fives = ((Number) qrr.get("$fives")).intValue();

			// checking if current user already gave a rating
			int userReview = 0;
			RegisteredUser loggedUser = getCurrentUser();
			if (loggedUser != null) {
				for (Rating r : activity.getRatings()) {
					if (r.getRegisteredUser().getId() == loggedUser.getId()) {
						userReview = r.getRating();
						break;
					}
				}
			}
			double average = 0;
			
			if (ratingNum != 0) {
				average = ratingSum * 1.0 / ratingNum;
			}
			System.out.println("rating is " + average);
			ReportDTO reportDTO = new ReportDTO(average, ratingNum, ones, twos, threes, fours, fives, userReview);
			restDTO = ActivityDTOConverter.convertToDTO(activity, reportDTO);
		}
		return restDTO;
	}

	private RegisteredUser getCurrentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken) && authentication != null) {
			String username = authentication.getName();
			return (RegisteredUser) userRepository.findOneByUsername(username);
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
		KieSession ks = droolsService.getKieContainer().newKieSession("rulesSession");
		ks.getAgenda().getAgendaGroup("recommend").setFocus();
		ks.setGlobal("best", bestScored);
		ks.setGlobal("calc", sc);
		ks.insert(dto);
		ks.insert(requirements);
		//ks.insert(sc);
		for (Activity a : activities) {
			ks.insert(a);
		}
		ks.fireAllRules();

		bestScored = (Activity) ks.getGlobal("best");
		ActivityDTO retVal = ActivityDTOConverter.convertToDTO(bestScored);
		RegisteredUser u = getCurrentUser();
		if (!u.getRecommendedActivities().contains(bestScored)) {
			u.getRecommendedActivities().add(bestScored);
			userRepository.save(u);
		}

		ks.dispose();
		return retVal;
	}

	public boolean rateActivity(RatingDTO dto) {
		Activity activity = activityRepository.findById(dto.getActivityId()).get();
		RegisteredUser logged = getCurrentUser();

		// User can rate only when something already recommended-checking that
		boolean alreadyRecommended = false;
		Rating newRating = null;

		// check if already recommended
		for (Activity a : logged.getRecommendedActivities()) {
			if (a.getId() == dto.getActivityId()) {
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

			KieSession ks = droolsService.getEventsSession();
			ks.insert(ratingEvent);

			ks.getAgenda().getAgendaGroup("rating").setFocus();
			ks.fireAllRules();
			activityRepository.save(activity);

		} else {
			System.out.println("Cant rate activity which was not recommended before");
		}
		return alreadyRecommended;
	}
}
