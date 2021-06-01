package com.sbnz.CityExplorer.rules;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import com.sbnz.CityExplorer.dto.UserActivitiesDTO;
import com.sbnz.CityExplorer.model.Activity;
import com.sbnz.CityExplorer.model.Rating;
import com.sbnz.CityExplorer.model.RegisteredUser;

public class UserSatisfactionRulesUnitTest {
	RegisteredUser satisfiedUser;
	RegisteredUser dissatisfiedUser;
	KieSession kSession;

	@Before
	public void createdDb() {
		dissatisfiedUser = new RegisteredUser(100L,"dissatisfied",new ArrayList<Activity>());
		satisfiedUser = new RegisteredUser(101L,"satisfied",new ArrayList<Activity>());

		// creating activities with positive and negative ratings
		for (int i = 0; i < 8; i++) {
			Activity a = new Activity();
			a.setId(Long.parseLong(i + ""));
			a.setName("Activity" + i);
			HashSet<Rating> ratings = new HashSet<Rating>();
			Rating r1 = new Rating(1, a, dissatisfiedUser);
			r1.setCreation(LocalDate.now().minusDays(5));
			Rating r2 = new Rating(5, a, satisfiedUser);
			r2.setCreation(LocalDate.now().minusDays(5));
			ratings.add(r1);
			ratings.add(r2);
			a.setRatings(ratings);

			dissatisfiedUser.getRecommendedActivities().add(a);
			satisfiedUser.getRecommendedActivities().add(a);
		}

		KieServices kservice = KieServices.Factory.get();
		KieContainer kContainer = kservice
				.newKieContainer(kservice.newReleaseId("com.sbnz", "CityExplorerKJAR", "1.0.0-SNAPSHOT"));
		kSession = kContainer.newKieSession("rulesSession");

	}

	@Test
	public void testDissatisfiedUsers() {
		assertNotNull(kSession);
		assertTrue(dissatisfiedUser.getRecommendedActivities().size() > 6);
		assertTrue(dissatisfiedUser.getRecommendedActivities().get(0).getRatings().size() == 2);

		List<UserActivitiesDTO> dissatisfiedUsers = new ArrayList<UserActivitiesDTO>();

		kSession.getAgenda().getAgendaGroup("dissatisfied");
		kSession.setGlobal("dissatisfiedUsers", dissatisfiedUsers);

		kSession.insert(satisfiedUser);
		int ruleCount = kSession.fireAllRules();
		assertTrue(ruleCount == 0);

		kSession.insert(dissatisfiedUser);
		ruleCount = kSession.fireAllRules();
		assertTrue(ruleCount == 1);
		assertTrue(dissatisfiedUsers.size() == 1);
		assertEquals("dissatisfied", dissatisfiedUsers.get(0).getUser().getUsername().toLowerCase());

	}

	public void testSatisfiedUsers() {
		assertNotNull(kSession);

		kSession.getAgenda().getAgendaGroup("satisfied");

		List<UserActivitiesDTO> satisfiedUsers = new ArrayList<UserActivitiesDTO>();

		kSession.getAgenda().getAgendaGroup("satisfied");
		kSession.setGlobal("satisfiedUsers", satisfiedUsers);
		kSession.insert(dissatisfiedUser);
		kSession.insert(satisfiedUser);
		int ruleCount = kSession.fireAllRules();

		assertTrue(ruleCount == 1);
		assertTrue(satisfiedUsers.size() == 1);
		assertEquals("satisfied", satisfiedUsers.get(0).getUser().getUsername().toLowerCase());

	}
}
