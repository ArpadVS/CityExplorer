package com.sbnz.CityExplorer.rules;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.drools.core.time.SessionPseudoClock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.sbnz.CityExplorer.events.RatingEvent;
import com.sbnz.CityExplorer.model.Activity;
import com.sbnz.CityExplorer.model.Rating;
import com.sbnz.CityExplorer.model.RegisteredUser;
import com.sbnz.CityExplorer.service.DroolsService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RatingEventRulesUnitTest {

	@Autowired 
	DroolsService droolsService;
	
	@Test
	public void testRatingEventAverageUpdate() {
		KieSession kSession = droolsService.getEventsSession();
		kSession.getAgenda().getAgendaGroup("rating");

		assertNotNull(kSession != null);
		
		//setup
		RegisteredUser u1 = new RegisteredUser();
		RegisteredUser u2 = new RegisteredUser();
		RegisteredUser u3 = new RegisteredUser();
		RegisteredUser u4 = new RegisteredUser();
		u4.setId(100L);
		Activity a = new Activity(100L, "TestActivity");
		Set<Rating> ratings = new HashSet<Rating>();
		ratings.add(new Rating(5, a, u1));
		ratings.add(new Rating(5, a, u2));
		ratings.add(new Rating(5, a, u3));
		a.setRatings(ratings);
		a.setAverage(5.0);
		
		//new rating appears
		Rating newRating = new Rating(5,a,u4);
		a.getRatings().add(newRating);

		RatingEvent ratingEvent = new RatingEvent(new Date(), newRating, u4.getId());
		
		kSession.insert(ratingEvent);
		int ruleCount = kSession.fireAllRules();

		assertEquals(1, ruleCount);
		assertEquals(4.5, a.getAverage());;
	}
	
	@Test
	public void testRatingBelow3() {
		KieSession kSession = droolsService.getEventsSession();
		kSession.getAgenda().getAgendaGroup("rating");

		assertNotNull(kSession != null);
		
		//setup
		RegisteredUser u1 = new RegisteredUser();
		RegisteredUser u2 = new RegisteredUser();
		RegisteredUser u3 = new RegisteredUser();
		RegisteredUser u4 = new RegisteredUser();
		RegisteredUser u5 = new RegisteredUser();
		u5.setId(100L);
		Activity a = new Activity(100L, "TestActivity");
		Set<Rating> ratings = new HashSet<Rating>();
		ratings.add(new Rating(3, a, u1));
		ratings.add(new Rating(3, a, u2));
		ratings.add(new Rating(3, a, u3));
		ratings.add(new Rating(3, a, u4));
		a.setRatings(ratings);
		a.setAverage(3.0);
		a.setAlarm(null);

		//new rating appears
		Rating newRating = new Rating(1,a,u5);
		a.getRatings().add(newRating);

		RatingEvent ratingEvent = new RatingEvent(new Date(), newRating, u5.getId());

		kSession.insert(ratingEvent);
		int ruleCount = kSession.fireAllRules();

		assertEquals(2, ruleCount); //average and alarm
		assertEquals(2.6, a.getAverage());;
		assertNotNull(a.getAlarm());
	}

	@Test
	public void testRatingGoesAbove3() {
		KieSession kSession = droolsService.getEventsSession();
		kSession.getAgenda().getAgendaGroup("rating");

		assertNotNull(kSession != null);
		
		//setup
		RegisteredUser u1 = new RegisteredUser();
		RegisteredUser u2 = new RegisteredUser();
		RegisteredUser u3 = new RegisteredUser();
		RegisteredUser u4 = new RegisteredUser();
		RegisteredUser u5 = new RegisteredUser();
		u5.setId(100L);
		Activity a = new Activity(100L, "TestActivity");
		Set<Rating> ratings = new HashSet<Rating>();
		ratings.add(new Rating(3, a, u1));
		ratings.add(new Rating(3, a, u2));
		ratings.add(new Rating(3, a, u3));
		ratings.add(new Rating(2, a, u4));
		a.setRatings(ratings);
		a.setAverage(2.75);
		a.setAlarm(LocalDate.now());

		//new rating appears
		Rating newRating = new Rating(5,a,u5);
		a.getRatings().add(newRating);

		RatingEvent ratingEvent = new RatingEvent(new Date(), newRating, u5.getId());

		kSession.insert(ratingEvent);
		int ruleCount = kSession.fireAllRules();

		assertEquals(2, ruleCount); //average and alarm
		assertEquals(3.2, a.getAverage());
		assertNull(a.getAlarm());
	}
	

	@Test
	public void PossibleReviewBomb() {
		KieSession kSession = droolsService.getEventsSession();
		kSession.getAgenda().getAgendaGroup("rating");
		SessionPseudoClock clock = kSession.getSessionClock();

		assertNotNull(kSession != null);
		assertNotNull(clock != null);
		
		//setup
		RegisteredUser u1 = new RegisteredUser();
		u1.setId(100L);
		Activity a = new Activity(100L, "TestActivity");
		a.setRatings(new HashSet<Rating>());
		Activity a2 = new Activity(101L, "TestActivity2");
		a.setRatings(new HashSet<Rating>());
		Activity a3 = new Activity(102L, "TestActivity3");
		a.setRatings(new HashSet<Rating>());
		Activity a4 = new Activity(103L, "TestActivity4");
		a.setRatings(new HashSet<Rating>());
		Activity a5 = new Activity(104L, "TestActivity5");
		a.setRatings(new HashSet<Rating>());
		
		List<Activity> activities = new ArrayList<Activity>();
		activities.add(a);
		activities.add(a2);
		activities.add(a3);
		activities.add(a4);

		//giving 4 rating in 1 minute
		for (int i = 0; i < 4; i++) {
			Rating newRating = new Rating(1,activities.get(i),u1);
			activities.get(i).getRatings().add(newRating);

			RatingEvent ratingEvent = new RatingEvent(new Date(), newRating, u1.getId());
			kSession.insert(ratingEvent);
			int ruleCount = kSession.fireAllRules();
			
			//only average fires
			assertEquals(1, ruleCount);
		}
		//5th 1-star rating in a minute from same user in 1 minute
		Rating newRating = new Rating(1,a5,u1);
		a5.getRatings().add(newRating);

		RatingEvent ratingEvent = new RatingEvent(new Date(), newRating, u1.getId());
		kSession.insert(ratingEvent);
		int ruleCount = kSession.fireAllRules();
		//2 rules fired, average and reviewBomb test 
		assertEquals(2, ruleCount);
	}
	
}
