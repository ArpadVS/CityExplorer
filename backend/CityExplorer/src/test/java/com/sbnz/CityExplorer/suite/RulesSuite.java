package com.sbnz.CityExplorer.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.sbnz.CityExplorer.rules.LoginEventRulesUnitTest;
import com.sbnz.CityExplorer.rules.PopularityRulesUnitTest;
import com.sbnz.CityExplorer.rules.RatingEventRulesUnitTest;
import com.sbnz.CityExplorer.rules.RecommendationRulesUnitTest;
import com.sbnz.CityExplorer.rules.UserSatisfactionRulesUnitTest;

@RunWith(Suite.class)
@SuiteClasses({
	UserSatisfactionRulesUnitTest.class,
	RecommendationRulesUnitTest.class,
	PopularityRulesUnitTest.class,
	LoginEventRulesUnitTest.class,
	RatingEventRulesUnitTest.class
})
public class RulesSuite {

}
