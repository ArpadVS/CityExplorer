package com.sbnz.CityExplorer.rules;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.drools.core.time.SessionPseudoClock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.sbnz.CityExplorer.events.BadCredentialsEvent;
import com.sbnz.CityExplorer.model.Activity;
import com.sbnz.CityExplorer.model.RegisteredUser;
import com.sbnz.CityExplorer.repository.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LoginEventRulesUnitTest {

	@MockBean
	UserRepository userRepositoryMocked;

	@Test
	public void testLoginEvents() {

		KieServices kservice = KieServices.Factory.get();
		KieContainer kContainer = kservice
				.newKieContainer(kservice.newReleaseId("com.sbnz", "CityExplorerKJAR", "1.0.0-SNAPSHOT"));
		KieSession kSession = kContainer.newKieSession("eventsSession");
		kSession.getAgenda().getAgendaGroup("login");
		SessionPseudoClock clock = kSession.getSessionClock();

		assertNotNull(kSession != null);
		assertNotNull(clock != null);

		RegisteredUser u1 = new RegisteredUser(100L, "user1", new ArrayList<Activity>());
		u1.setActive(true);
		int ruleCount;

		when(userRepositoryMocked.save(u1)).thenReturn(u1);

		kSession.setGlobal("userRepository", userRepositoryMocked);

		// testing if normal - no warning triggered expected - 1 bad login every 1
		// minute
		for (int i = 0; i < 12; i++) {
			BadCredentialsEvent failedLogin = new BadCredentialsEvent(u1, new Date());
			kSession.insert(failedLogin);
			clock.advanceTime(1, TimeUnit.MINUTES);
			ruleCount = kSession.fireAllRules();
			// As long as there is no bulk failedlogine, no rule will fire
			assertTrue(ruleCount == 0);
		}

		clock.advanceTime(30, TimeUnit.MINUTES); // waiting for events to disappear

		// testing for warning - 10 failed login in 3 minutes
		for (int i = 0; i < 6; i++) {
			BadCredentialsEvent failedLogin = new BadCredentialsEvent(u1, new Date());
			kSession.insert(failedLogin);
		}
		clock.advanceTime(2, TimeUnit.MINUTES);
		for (int i = 0; i < 6; i++) {
			BadCredentialsEvent failedLogin = new BadCredentialsEvent(u1, new Date());
			kSession.insert(failedLogin);
		}
		ruleCount = kSession.fireAllRules();
		// Expecting that rule for warning on console is triggered
		assertTrue(ruleCount == 1);

		clock.advanceTime(30, TimeUnit.MINUTES); // waiting for events to disappar

		// testing for Harmful attach - serious warning is triggered and user
		// deactivated
		for (int i = 0; i < 12; i++) {
			BadCredentialsEvent failedLogin = new BadCredentialsEvent(u1, new Date());
			kSession.insert(failedLogin);
		}

		ruleCount = kSession.fireAllRules();
		// Expecting that rule for warning on console is triggered
		assertTrue(ruleCount == 1);
		assertFalse(u1.isActive());

		kSession.destroy();
	}
}
