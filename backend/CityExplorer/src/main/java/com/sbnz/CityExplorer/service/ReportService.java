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

	public List<ActivityDTO> getAlarms() {
		List<Activity> alarmed = activityRepository.findAlarmedActivities();
		List<ActivityDTO> dtos = new ArrayList<ActivityDTO>();
		
		for (Activity a : alarmed) {
			ActivityDTO asd = ActivityDTOConverter.convertToDTO(a);
			asd.setReport(new ReportDTO());
			asd.getReport().setNumOfRatings(a.getRatings().size());
			dtos.add(asd);
		}
		
		return dtos;
	}

	public PopularityDTO getPopularityReport() {

		// inserting facts
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
		// initiating globals
		Activity mostRecommendedActivity = null;
		Activity leastRecommendedActivity = null;
		kSession.setGlobal("mostRecommendedActivity", mostRecommendedActivity);
		kSession.setGlobal("leastRecommendedActivity", leastRecommendedActivity);
		kSession.getAgenda().getAgendaGroup("popularity").setFocus();
		kSession.fireAllRules();
		// result
		mostRecommendedActivity = (Activity) kSession.getGlobal("mostRecommendedActivity");
		leastRecommendedActivity = (Activity) kSession.getGlobal("leastRecommendedActivity");
		
		kSession.destroy();
		PopularityDTO popularityDto = new PopularityDTO(ActivityDTOConverter.convertToDTO(mostRecommendedActivity),
				ActivityDTOConverter.convertToDTO(leastRecommendedActivity));
		return popularityDto;
	}

	public Set<UserSatisfactionDTO> getDissatisfiedUsers() {
		// inserting facts
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
		
		List<UserActivitiesDTO> dissatisfiedUsers = new ArrayList<UserActivitiesDTO>();
		// globals
		kSession.setGlobal("dissatisfiedUsers", dissatisfiedUsers);
		kSession.getAgenda().getAgendaGroup("dissatisfied").setFocus();
		kSession.fireAllRules();

		// result
		Set<UserSatisfactionDTO> dissatisfiedUsersResult = new HashSet<UserSatisfactionDTO>();
		for (UserActivitiesDTO userInfo : dissatisfiedUsers) {
			UserSatisfactionDTO dto = new UserSatisfactionDTO();
			dto.setUser(RegistrationDTOConverter.convertToDTO(userInfo.getUser()));
			for (Activity userActivity : userInfo.getActivities()) {
				dto.getActivities().add(ActivityDTOConverter.convertToDTO(userActivity));
			}
			dissatisfiedUsersResult.add(dto);
		}
		kSession.destroy();
		return dissatisfiedUsersResult;
	}

	public Set<UserSatisfactionDTO> getSatisfiedUsers() {
		// inserting facts
		KieSession kSession = droolsService.getKieContainer().newKieSession("rulesSession");
		List<User> users = userRepository.findAll();
		for (User user : users) {
			if (user instanceof RegisteredUser) {
				RegisteredUser registeredUser = (RegisteredUser) user;
				System.out.println("\t\tInserted: " + user.getUsername() + " with " + registeredUser.getRecommendedActivities().size()
						+ " recommended.");
				kSession.insert(registeredUser);
			}
		}
		
		List<UserActivitiesDTO> satisfiedUsers = new ArrayList<UserActivitiesDTO>();
		// globals
		kSession.setGlobal("satisfiedUsers", satisfiedUsers);
		kSession.getAgenda().getAgendaGroup("satisfied").setFocus();
		kSession.fireAllRules();

		// result
		Set<UserSatisfactionDTO> satisfiedUsersResult = new HashSet<UserSatisfactionDTO>();
		for (UserActivitiesDTO userInfo : satisfiedUsers) {
			UserSatisfactionDTO dto = new UserSatisfactionDTO();
			dto.setUser(RegistrationDTOConverter.convertToDTO(userInfo.getUser()));
			for (Activity userActivity : userInfo.getActivities()) {
				dto.getActivities().add(ActivityDTOConverter.convertToDTO(userActivity));
			}
			satisfiedUsersResult.add(dto);
		}
		kSession.destroy();
		return satisfiedUsersResult;
	}

	public List<ActivityDTO> getActivitiesByRatingRange(RatingRangeDTO dto) {
		// Creating KieSession and inserting rule from template
		InputStream template = ReportService.class
				.getResourceAsStream("/templates/getActivitiesByRatingRange.drt");
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
