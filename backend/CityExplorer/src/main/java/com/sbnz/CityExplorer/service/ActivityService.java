package com.sbnz.CityExplorer.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
import com.sbnz.CityExplorer.dto.ReportDTO;
import com.sbnz.CityExplorer.model.Activity;
import com.sbnz.CityExplorer.model.Rating;
import com.sbnz.CityExplorer.model.RegisteredUser;
import com.sbnz.CityExplorer.repository.ActivityRepository;
import com.sbnz.CityExplorer.repository.UserRepository;

@Service
public class ActivityService {
	@Autowired
	ActivityRepository activityRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	DroolsService droolsService;
	
	public List<ActivityDTO> getAll() {
		List<Activity> activities =  activityRepository.findAll();
		return (activities.stream().map(activity -> {
			ActivityDTO dto = ActivityDTOConverter.covertToDTO(activity);
			return dto;
		})).collect(Collectors.toList());
	}

	public ActivityDTO getActivity(Long id) {
		Optional<Activity> restaurantOpt = activityRepository.findById(id);
		KieSession kieSession = droolsService.getRulesSession();
		kieSession.getAgenda().getAgendaGroup("queries").setFocus();
		kieSession.insert(restaurantOpt.get());

		ActivityDTO restDTO = null;
		QueryResults results = kieSession.getQueryResults("User ratings by activity", id);
		
		//getting values from query
		for (QueryResultsRow qrr : results) {
			Activity activity = (Activity) qrr.get("$activity");
			//total number of ratings
			int ratingNum = ((Number) qrr.get("$ratingNum")).intValue();
			//sum of every rating
			int ratingSum = (int) qrr.get("$ratingSum");
			
			//total number of 1-2-3-4-5 ratings
			int ones = ((Number) qrr.get("$ones")).intValue();
			int twos = ((Number) qrr.get("$twos")).intValue();
			int threes = ((Number) qrr.get("$threes")).intValue();
			int fours = ((Number) qrr.get("$fours")).intValue();
			int fives = ((Number) qrr.get("$fives")).intValue();
			
			//checking if current user already gave a rating
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
			ReportDTO reportDTO = new ReportDTO(ratingSum/ratingNum, ratingNum, ones, twos, threes, fours, fives,
					userReview);
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
}
