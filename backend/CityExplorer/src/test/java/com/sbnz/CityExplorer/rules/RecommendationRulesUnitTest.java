package com.sbnz.CityExplorer.rules;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import com.sbnz.CityExplorer.dto.UserRequirementsDTO;
import com.sbnz.CityExplorer.model.ActivityRequirements;
import com.sbnz.CityExplorer.model.Keywords;
import com.sbnz.CityExplorer.model.Location;
import com.sbnz.CityExplorer.model.Price;
import com.sbnz.CityExplorer.model.Space;

public class RecommendationRulesUnitTest {

	KieSession kSession;
	KieContainer kContainer;

	@Before
	public void createdDb() {

		KieServices kservice = KieServices.Factory.get();
		kContainer = kservice.newKieContainer(kservice.newReleaseId("com.sbnz", "CityExplorerKJAR", "1.0.0-SNAPSHOT"));

	}

	// testing both rules because they one after another
	@Test
	public void testGoingWithFamilyandFamilyFriendly() {
		kSession = kContainer.newKieSession("rulesSession");
		assertNotNull(kSession != null);
		kSession.getAgenda().getAgendaGroup("recommend");
		UserRequirementsDTO dto = new UserRequirementsDTO();
		dto.setCompanion("FAMILY");
		dto.setDate(LocalDate.of(2021, 2, 20)); // setting date so outdoor rule does not activate
		ActivityRequirements req = new ActivityRequirements();
		kSession.insert(dto);
		kSession.insert(req);
		int ruleCount = kSession.fireAllRules();

		// 4 rules fired, Start, End , rule for goingwithfamily, and rule for Family
		// friendly
		assertTrue(ruleCount == 4);
		assertTrue(req.getFeatures().getKeywords().contains(Keywords.FAMILY_FRIENDLY));
		assertTrue(req.getFeatures().isChildrensProgram());
		kSession.destroy();
	}

	@Test
	public void testSpeciaWithPartner() {
		kSession = kContainer.newKieSession("rulesSession");
		assertNotNull(kSession != null);
		kSession.getAgenda().getAgendaGroup("recommend");
		UserRequirementsDTO dto = new UserRequirementsDTO();
		dto.setCompanion("PARTNER");
		dto.setSpecial(true);
		dto.setDate(LocalDate.of(2021, 2, 20)); // setting date so outdoor rule does not activate
		ActivityRequirements req = new ActivityRequirements();
		kSession.insert(dto);
		kSession.insert(req);
		int ruleCount = kSession.fireAllRules();

		// 3 rules fired, Start, End , rule which is tested
		assertTrue(ruleCount == 3);
		assertTrue(req.getFeatures().getKeywords().contains(Keywords.ROMANTIC));
		assertTrue(req.getFeatures().getKeywords().contains(Keywords.LUXURY));
		assertTrue(req.getFeatures().getSpace() == Space.SMALL);
		assertTrue(req.getFeatures().isChildrensProgram());
		kSession.destroy();
	}

	// testing both rules because they one after another
	@Test
	public void testSpeciaWithColleaguesAndTeambuilding() {
		kSession = kContainer.newKieSession("rulesSession");
		assertNotNull(kSession != null);
		kSession.getAgenda().getAgendaGroup("recommend");
		UserRequirementsDTO dto = new UserRequirementsDTO();
		dto.setCompanion("COLLEAGUES");
		dto.setSpecial(true);
		dto.setDate(LocalDate.of(2021, 2, 20)); // setting date so outdoor rule does not activate
		ActivityRequirements req = new ActivityRequirements();
		kSession.insert(dto);
		kSession.insert(req);
		int ruleCount = kSession.fireAllRules();

		// 4 rules fired, Start, End , rule for specialwithcolleagues, rule for
		// teambuilding

		assertTrue(ruleCount == 4);
		assertTrue(req.getFeatures().getKeywords().contains(Keywords.TEAMBUILDING));
		assertTrue(req.getFeatures().isReservations());
		kSession.destroy();

	}

	@Test
	public void testNormalWithColleagues() {
		kSession = kContainer.newKieSession("rulesSession");
		assertNotNull(kSession != null);
		kSession.getAgenda().getAgendaGroup("recommend");
		UserRequirementsDTO dto = new UserRequirementsDTO();
		dto.setCompanion("COLLEAGUES");
		dto.setSpecial(false);
		dto.setDate(LocalDate.of(2021, 2, 20)); // setting date so outdoor rule does not activate
		ActivityRequirements req = new ActivityRequirements();
		kSession.insert(dto);
		kSession.insert(req);
		int ruleCount = kSession.fireAllRules();

		// 3 rules fired, Start, End , rule which is tested
		assertTrue(ruleCount == 3);
		assertTrue(req.getFeatures().getKeywords().contains(Keywords.LUXURY));
		kSession.destroy();
	}

	@Test
	public void testNormalWithFriendsAndChill() {
		kSession = kContainer.newKieSession("rulesSession");
		assertNotNull(kSession != null);
		kSession.getAgenda().getAgendaGroup("recommend");
		UserRequirementsDTO dto = new UserRequirementsDTO();
		dto.setCompanion("FRIENDS");
		dto.setSpecial(false);
		dto.setDate(LocalDate.of(2021, 2, 20)); // setting date so outdoor rule does not activate
		ActivityRequirements req = new ActivityRequirements();
		kSession.insert(dto);
		kSession.insert(req);
		int ruleCount = kSession.fireAllRules();

		// 4 rules fired, Start, End , rule for friends and for chill keyword
		assertTrue(ruleCount == 4);
		assertTrue(req.getFeatures().getKeywords().contains(Keywords.CHILL));
		assertTrue(req.getFeatures().isWifi());
		assertTrue(req.getFeatures().isTv());
		kSession.destroy();
	}

	@Test
	public void testWeatherForOutdoor() {
		kSession = kContainer.newKieSession("rulesSession");
		assertNotNull(kSession != null);
		kSession.getAgenda().getAgendaGroup("recommend");
		UserRequirementsDTO dto = new UserRequirementsDTO();
		dto.setDate(LocalDate.of(2021, 8, 20)); // setting date from summer
		ActivityRequirements req = new ActivityRequirements();
		kSession.insert(dto);
		kSession.insert(req);
		int ruleCount = kSession.fireAllRules();

		// 3 rules fired, Start, End , rule for friends and for chill keyword
		assertTrue(ruleCount == 3);
		assertTrue(req.getFeatures().isOutdoor());
		kSession.destroy();
	}

	@Test
	public void testTransporationOptions() {
		kSession = kContainer.newKieSession("rulesSession");
		assertNotNull(kSession != null);
		kSession.getAgenda().getAgendaGroup("recommend");
		UserRequirementsDTO dto = new UserRequirementsDTO();
		dto.setDate(LocalDate.of(2021, 2, 20)); // setting date so outdoor rule does not activate
		dto.setTransportation("BY_FOOT");
		ActivityRequirements req = new ActivityRequirements();
		kSession.insert(dto);
		kSession.insert(req);
		int ruleCount = kSession.fireAllRules();

		assertTrue(ruleCount == 3);
		assertTrue(req.getLocation() == Location.CITY_CENTER);

		dto.setTransportation("BUS");
		ruleCount = kSession.fireAllRules();
		assertTrue(ruleCount == 3);
		assertTrue(req.getFeatures().isBusNearby());

		dto.setTransportation("AUTO");
		ruleCount = kSession.fireAllRules();
		assertTrue(ruleCount == 3);
		assertTrue(req.getFeatures().isParking());

		kSession.destroy();
	}

	@Test
	public void testSpaceForMorePeople() {
		kSession = kContainer.newKieSession("rulesSession");
		assertNotNull(kSession != null);
		kSession.getAgenda().getAgendaGroup("recommend");
		UserRequirementsDTO dto = new UserRequirementsDTO();
		dto.setDate(LocalDate.of(2021, 2, 20)); // setting date so outdoor rule does not activate
		dto.setNumPeople(8);
		ActivityRequirements req = new ActivityRequirements();
		kSession.insert(dto);
		kSession.insert(req);
		int ruleCount = kSession.fireAllRules();

		assertTrue(ruleCount == 3);
		assertTrue(req.getFeatures().getSpace() == Space.MEDIUM);

		dto.setNumPeople(20);
		ruleCount = kSession.fireAllRules();
		assertTrue(ruleCount == 3);
		assertTrue(req.getFeatures().getSpace() == Space.LARGE);

		kSession.destroy();
	}

	@Test
	public void testThemeOptions() {
		kSession = kContainer.newKieSession("rulesSession");
		assertNotNull(kSession != null);
		kSession.getAgenda().getAgendaGroup("recommend");
		UserRequirementsDTO dto = new UserRequirementsDTO();
		dto.setDate(LocalDate.of(2021, 2, 20)); // setting date so outdoor rule does not activate
		dto.setTheme("NATURE");
		ActivityRequirements req = new ActivityRequirements();
		kSession.insert(dto);
		kSession.insert(req);
		int ruleCount = kSession.fireAllRules();

		assertTrue(ruleCount == 3);
		assertTrue(req.getFeatures().getKeywords().contains(Keywords.NATURE));

		dto.setTheme("ADRENALINE");
		ruleCount = kSession.fireAllRules();
		assertTrue(ruleCount == 3);
		assertTrue(req.getFeatures().getKeywords().contains(Keywords.ADRENALINE));

		dto.setTheme("HISTORY");
		ruleCount = kSession.fireAllRules();
		assertTrue(ruleCount == 3);
		assertTrue(req.getFeatures().getKeywords().contains(Keywords.HISTORY));

		dto.setTheme("SPORT");
		ruleCount = kSession.fireAllRules();
		assertTrue(ruleCount == 3);
		assertTrue(req.getFeatures().getKeywords().contains(Keywords.SPORT));

		kSession.destroy();
	}

	@Test
	public void testPricesOptions() {
		kSession = kContainer.newKieSession("rulesSession");
		assertNotNull(kSession != null);
		kSession.getAgenda().getAgendaGroup("recommend");
		UserRequirementsDTO dto = new UserRequirementsDTO();
		dto.setDate(LocalDate.of(2021, 2, 20)); // setting date so outdoor rule does not activate
		dto.getPrice().add("FREE");
		dto.getPrice().add("CHEAP");
		dto.getPrice().add("MODERATE");
		dto.getPrice().add("EXPENSIVE");
		ActivityRequirements req = new ActivityRequirements();
		kSession.insert(dto);
		kSession.insert(req);
		int ruleCount = kSession.fireAllRules();

		assertTrue(ruleCount == 6);
		assertTrue(req.getPrices().contains(Price.FREE));
		assertTrue(req.getPrices().contains(Price.CHEAP));
		assertTrue(req.getPrices().contains(Price.MODERATE));
		assertTrue(req.getPrices().contains(Price.EXPENSIVE));

	}
}
