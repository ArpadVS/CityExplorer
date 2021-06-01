package com.sbnz.CityExplorer.rules;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;

import org.drools.core.time.SessionPseudoClock;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import com.sbnz.CityExplorer.model.Activity;
import com.sbnz.CityExplorer.model.RegisteredUser;

public class RatingEventRulesUnitTest {

	@Test
	public void testPossibleReviewBombEvent() {

		KieServices kservice = KieServices.Factory.get();
		KieContainer kContainer = kservice
				.newKieContainer(kservice.newReleaseId("com.sbnz", "CityExplorerKJAR", "1.0.0-SNAPSHOT"));
		KieSession kSession = kContainer.newKieSession("eventsSession");
		kSession.getAgenda().getAgendaGroup("rating");
		SessionPseudoClock clock = kSession.getSessionClock();

		assertNotNull(kSession != null);
		assertNotNull(clock != null);
		

		RegisteredUser u1 = new RegisteredUser(100L, "user1", new ArrayList<Activity>());
		u1.setActive(true);
		int ruleCount;
		
		
		
	}
}
