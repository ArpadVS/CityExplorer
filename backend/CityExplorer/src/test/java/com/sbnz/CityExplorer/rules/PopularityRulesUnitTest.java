package com.sbnz.CityExplorer.rules;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import com.sbnz.CityExplorer.model.Activity;
import com.sbnz.CityExplorer.model.RegisteredUser;

public class PopularityRulesUnitTest {

	@Test
	public void testPopularityRules() {

		KieServices kservice = KieServices.Factory.get();
		KieContainer kContainer = kservice
				.newKieContainer(kservice.newReleaseId("com.sbnz", "CityExplorerKJAR", "1.0.0-SNAPSHOT"));
		KieSession kSession = kContainer.newKieSession("rulesSession");

		assertNotNull(kSession != null);

		Activity mostRecommendedActivity = null;
		Activity leastRecommendedActivity = null;
		RegisteredUser u1 = new RegisteredUser(100L, "user1", new ArrayList<Activity>());
		RegisteredUser u2 = new RegisteredUser(101L, "user2", new ArrayList<Activity>());
		RegisteredUser u3 = new RegisteredUser(102L, "user3", new ArrayList<Activity>());

		Activity a1 = new Activity(100L, "Activity1"); // 3 times recommended
		Activity a2 = new Activity(101L, "Activity2"); // 2 times
		Activity a3 = new Activity(102L, "Activity3"); // 2 times
		Activity a4 = new Activity(103L, "Activity4"); // 1 times

		u1.getRecommendedActivities().add(a1);

		u2.getRecommendedActivities().add(a1);
		u2.getRecommendedActivities().add(a2);
		u2.getRecommendedActivities().add(a3);
		u2.getRecommendedActivities().add(a4);

		u3.getRecommendedActivities().add(a1);
		u3.getRecommendedActivities().add(a2);
		u3.getRecommendedActivities().add(a3);

		kSession.insert(a1);
		kSession.insert(a2);
		kSession.insert(a3);
		kSession.insert(a4);
		kSession.insert(u1);
		kSession.insert(u2);
		kSession.insert(u3);

		kSession.setGlobal("mostRecommendedActivity", mostRecommendedActivity);
		kSession.setGlobal("leastRecommendedActivity", leastRecommendedActivity);
		kSession.getAgenda().getAgendaGroup("popularity").setFocus();
		int times = kSession.fireAllRules();

		mostRecommendedActivity = (Activity) kSession.getGlobal("mostRecommendedActivity");
		leastRecommendedActivity = (Activity) kSession.getGlobal("leastRecommendedActivity");

		assertTrue(times == 2);
		assertTrue(mostRecommendedActivity.getId() == 100L);
		assertEquals("Activity1", mostRecommendedActivity.getName());

		assertTrue(leastRecommendedActivity.getId() == 103L);
		assertEquals("Activity4", leastRecommendedActivity.getName());

		kSession.destroy();
	}

}
