package com.sbnz.CityExplorer.service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.drools.template.ObjectDataCompiler;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbnz.CityExplorer.converter.ActivityDTOConverter;
import com.sbnz.CityExplorer.converter.RegistrationDTOConverter;
import com.sbnz.CityExplorer.dto.ActivityDTO;
import com.sbnz.CityExplorer.dto.UserSatisfactionDTO;
import com.sbnz.CityExplorer.dto.PopularityDTO;
import com.sbnz.CityExplorer.dto.RatingRangeDTO;
import com.sbnz.CityExplorer.dto.ReportDTO;
import com.sbnz.CityExplorer.dto.UserActivitiesDTO;
import com.sbnz.CityExplorer.model.Activity;
import com.sbnz.CityExplorer.model.RegisteredUser;
import com.sbnz.CityExplorer.model.User;
import com.sbnz.CityExplorer.repository.ActivityRepository;
import com.sbnz.CityExplorer.repository.UserRepository;

@Service
public class ReportService {
	@Autowired
	DroolsService droolsService;
	@Autowired
	ActivityRepository activityRepository;
	@Autowired
	UserRepository userRepository;

	public List<ActivityDTO> getAlarmedActivities() {
		List<Activity> alarmedActivities = activityRepository.findAlarmedActivities();
		List<ActivityDTO> activityDTOs = new ArrayList<ActivityDTO>();
		
		for (Activity activity : alarmedActivities) {
			ActivityDTO dto = ActivityDTOConverter.convertToDTO(activity);
			dto.setReport(new ReportDTO());
			dto.getReport().setNumOfRatings(activity.getRatings().size());
			activityDTOs.add(dto);
		}
		
		return activityDTOs;
	}

	public PopularityDTO getPopularityReport() {
		KieSession kSession = droolsService.getKieContainer().newKieSession("rulesSession");
		List<User> users = userRepository.findAll();
		for (User user : users) {
			if (user instanceof RegisteredUser) {
				kSession.insert((RegisteredUser) user);
			}
		}
		List<Activity> activities = activityRepository.findAll();
		for (Activity activity : activities) {
			kSession.insert(activity);
		}

		Activity mostRecommendedActivity = null;
		Activity leastRecommendedActivity = null;
		kSession.setGlobal("mostRecommendedActivity", mostRecommendedActivity);
		kSession.setGlobal("leastRecommendedActivity", leastRecommendedActivity);
		kSession.getAgenda().getAgendaGroup("popularity").setFocus();
		kSession.fireAllRules();

		mostRecommendedActivity = (Activity) kSession.getGlobal("mostRecommendedActivity");
		leastRecommendedActivity = (Activity) kSession.getGlobal("leastRecommendedActivity");
		
		kSession.destroy();
		PopularityDTO popularityDto = new PopularityDTO(ActivityDTOConverter.convertToDTO(mostRecommendedActivity),
				ActivityDTOConverter.convertToDTO(leastRecommendedActivity));
		return popularityDto;
	}

	public Set<UserSatisfactionDTO> getDissatisfiedUsers() {
		return getUserSatisfactionReports("DISSATISFIED");
	}


	public Set<UserSatisfactionDTO> getSatisfiedUsers() {
		return getUserSatisfactionReports("SATISFIED");
	}

	public Set<UserSatisfactionDTO> getUserSatisfactionReports( String mode ) {
		KieSession kSession = droolsService.getKieContainer().newKieSession("rulesSession");
		List<User> users = userRepository.findAll();
		for (User user : users) {
			if (user instanceof RegisteredUser) {
				RegisteredUser registeredUser = (RegisteredUser) user;
				System.out.println("\tInserted: " + user.getUsername() + " with " + registeredUser.getRecommendedActivities().size()
						+ " recommended.");
				kSession.insert(registeredUser);
			}
		}
		
		String globalName, agendaName;
		if (mode.equals("SATISFIED")) {
			globalName = "satisfiedUsers";
			agendaName = "satisfied";
		} else {
			globalName = "dissatisfiedUsers";
			agendaName = "dissatisfied";
		}

		List<UserActivitiesDTO> resultUsers = new ArrayList<UserActivitiesDTO>();
		kSession.setGlobal(globalName, resultUsers);
		kSession.getAgenda().getAgendaGroup(agendaName).setFocus();
		kSession.fireAllRules();

		Set<UserSatisfactionDTO> resultUserDTOs = new HashSet<UserSatisfactionDTO>();
		for (UserActivitiesDTO userInfo : resultUsers) {
			UserSatisfactionDTO dto = new UserSatisfactionDTO();
			dto.setUser(RegistrationDTOConverter.convertToDTO(userInfo.getUser()));
			for (Activity userActivity : userInfo.getActivities()) {
				dto.getActivities().add(ActivityDTOConverter.convertToDTO(userActivity));
			}
			resultUserDTOs.add(dto);
		}
		kSession.destroy();
		return resultUserDTOs;
	}

	public List<ActivityDTO> getActivitiesByRatingRange(RatingRangeDTO dto) {
		// Creating KieSession and inserting rule from template
		InputStream template = ReportService.class.getResourceAsStream("/templates/getActivitiesByRatingRange.drt");
		ObjectDataCompiler converter = new ObjectDataCompiler();
		List<RatingRangeDTO> data = new ArrayList<RatingRangeDTO>();
		data.add(dto);
		String drl = converter.compile(data, template);
		System.out.println("\n\n" + drl + "\n\n");
		KieSession kieSession = droolsService.createKieSessionFromDRL(drl);

		// Application of rules and collecting result
		List<Activity> result = new ArrayList<>();
		for (Activity activity : activityRepository.findAll()) {
			kieSession.insert(activity);
		}
		kieSession.setGlobal("result", result);
		kieSession.fireAllRules();
		return (result.stream().map(activity -> {
			ActivityDTO activityDTO = ActivityDTOConverter.convertToDTO(activity);
			return activityDTO;
		})).collect(Collectors.toList());
	}

}
