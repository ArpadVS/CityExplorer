package com.sbnz.CityExplorer.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbnz.CityExplorer.converter.ActivityDTOConverter;
import com.sbnz.CityExplorer.converter.RegistrationDTOConverter;
import com.sbnz.CityExplorer.dto.ActivityDTO;
import com.sbnz.CityExplorer.dto.DissatisfiedUsersDTO;
import com.sbnz.CityExplorer.dto.PopularityDTO;
import com.sbnz.CityExplorer.dto.RatingRange;
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
			dtos.add(ActivityDTOConverter.convertToDTO(a));
		}
		return dtos;
	}

	public PopularityDTO getPopularityReport() {

		// inserting facts
		KieSession kSession = droolsService.getRulesSession();
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
		PopularityDTO popularityDto = new PopularityDTO(ActivityDTOConverter.convertToDTO(mostRecommendedActivity),
				ActivityDTOConverter.convertToDTO(leastRecommendedActivity));
		return popularityDto;
	}

	public Set<DissatisfiedUsersDTO> getDissatisfiedUsers() {
		// inserting facts
		KieSession kSession = droolsService.getRulesSession();
		List<User> users = userRepository.findAll();
		for (User user : users) {
			if (user instanceof RegisteredUser) {
				RegisteredUser reg = (RegisteredUser) user;
				System.out.println("\tInserted: " + user.getUsername() + " with " + reg.getRecommendedActivities().size()
						+ " recommended.");
				kSession.insert(reg);
			}
		}
		
		List<UserActivitiesDTO> dissatisfiedUsers = new ArrayList<UserActivitiesDTO>();
		// globals
		kSession.setGlobal("dissatisfiedUsers", dissatisfiedUsers);
		kSession.getAgenda().getAgendaGroup("dissatisfied").setFocus();
		kSession.fireAllRules();

		// result
		Set<DissatisfiedUsersDTO> dissatisfiedUsersList = new HashSet<DissatisfiedUsersDTO>();
		for (UserActivitiesDTO item : dissatisfiedUsers) {
			DissatisfiedUsersDTO dto = new DissatisfiedUsersDTO();
			dto.setUser(RegistrationDTOConverter.convertToDTO(item.getUser()));
			for (Activity r : item.getActivities()) {
				dto.getActivities().add(ActivityDTOConverter.convertToDTO(r));
			}
			dissatisfiedUsersList.add(dto);
		}
		return dissatisfiedUsersList;
	}

	public Set<DissatisfiedUsersDTO> getSatisfiedUsers() {
		// inserting facts
		KieSession kSession = droolsService.getRulesSession();
		List<User> users = userRepository.findAll();
		for (User user : users) {
			if (user instanceof RegisteredUser) {
				RegisteredUser reg = (RegisteredUser) user;
				System.out.println("\t\tInserted: " + user.getUsername() + " with " + reg.getRecommendedActivities().size()
						+ " recommended.");
				kSession.insert(reg);
			}
		}
		
		List<UserActivitiesDTO> satisfiedUsers = new ArrayList<UserActivitiesDTO>();
		// globals
		kSession.setGlobal("satisfiedUsers", satisfiedUsers);
		kSession.getAgenda().getAgendaGroup("satisfied").setFocus();
		kSession.fireAllRules();

		// result
		Set<DissatisfiedUsersDTO> satisfiedUsersList = new HashSet<DissatisfiedUsersDTO>();
		for (UserActivitiesDTO item : satisfiedUsers) {
			DissatisfiedUsersDTO dto = new DissatisfiedUsersDTO();
			dto.setUser(RegistrationDTOConverter.convertToDTO(item.getUser()));
			for (Activity r : item.getActivities()) {
				dto.getActivities().add(ActivityDTOConverter.convertToDTO(r));
			}
			satisfiedUsersList.add(dto);
		}
		return satisfiedUsersList;
	}

	public List<ActivityDTO> getActivitiesByRatingRange(RatingRange dto) {
		// TODO Auto-generated method stub
		return null;
	}

}
