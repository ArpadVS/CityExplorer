package com.sbnz.CityExplorer.util;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.sbnz.CityExplorer.model.Activity;
import com.sbnz.CityExplorer.model.ActivityRequirements;
import com.sbnz.CityExplorer.model.Keywords;
import com.sbnz.CityExplorer.model.Location;
import com.sbnz.CityExplorer.model.RegisteredUser;
import com.sbnz.CityExplorer.repository.UserRepository;

@Service
public class ScoreCalculator {
	
	@Autowired
	UserRepository userRepository;
	
	public ScoreCalculator() {
		super();
	}

	public int calculateScore(Activity activity, ActivityRequirements requirements) {
		if((requirements.getPrices()== null) && requirements.getPrices().isEmpty()) {
			return -50;
		}
		System.out.println("Calculating score for " + activity.getName());
		
		int score = 0;
		
		if(activity.getAverage() != null) {
			System.out.println("RATINGS \t");
			score += (int)Math.round(activity.getAverage());
		}
		
		if(requirements.getPrices().contains(activity.getFeatures().getPrice())) {
			System.out.println("PRICE \t");
			score += 4;
		}

		for (Keywords req : requirements.getFeatures().getKeywords()) {
			if (activity.getFeatures().getKeywords().contains(req)) {
				switch (req) {
				case ADRENALINE:
				case SPORT:
				case NATURE:
				case EDUCATIONAL:
					System.out.println("THEME " + req.toString() );
					score+= 4;
					break;
				default:
					System.out.println("KEYWORD " + req.toString() );
					score+= 3;
					break;
				}
			}
		}
		
		if(requirements.getFeatures().isBusNearby() && activity.getFeatures().isBusNearby()) {
			System.out.println("BUS \t");
			score += 1;
		}
		if(requirements.getFeatures().isChildrensProgram() && activity.getFeatures().isChildrensProgram()) {
			System.out.println("CHILDREN \t");
			score += 1;
		}
		if(requirements.getFeatures().isOutdoor() && activity.getFeatures().isOutdoor()) {
			System.out.println("OUTDOOR \t");
			score += 2;
		}
		if(requirements.getFeatures().isParking() && activity.getFeatures().isParking()) {
			System.out.println("PARKING \t");
			score += 1;
		}
		if(requirements.getFeatures().isReservations() && activity.getFeatures().isReservations()) {
			System.out.println("RESERVATIONS \t");
			score += 1;
		}
		if(requirements.getFeatures().isTv() && activity.getFeatures().isTv()) {
			System.out.println("TV \t");
			score += 1;
		}
		if(requirements.getFeatures().isWifi() && activity.getFeatures().isWifi()) {
			System.out.println("WIFI \t");
			score += 1;
		}
		
		if((requirements.getLocation()!= null )
				&& requirements.getLocation().equals(Location.CITY_CENTER)) {
			switch (activity.getLocation()) {
			case CITY_CENTER:
				System.out.println("NoWalk");
				score += 2;
				break;
			case SUBURBS:
				System.out.println("SuburbsWalk \t");
				score += -1;
				break;
			case OUTSIDE_THE_CITY:
				System.out.println("OutsideWalk \t");
				score += -3;
				break;
			}
		}
		
		if((requirements.getFeatures().getSpace() != null )
				&& requirements.getFeatures().getSpace() == activity.getFeatures().getSpace()) {
			score += 2;
		}
		
		RegisteredUser u = getCurrentUser();
		List<Activity> previous = u.getRecommendedActivities();
		if((previous!= null) && previous.contains(activity)) {
			System.out.println("Already recommended");
			score -= 10;
		}

		System.out.println("Score for " + activity.getName() + " is " + score);
		activity.setScore(score);
		return score;
	}
	
	private RegisteredUser getCurrentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken) && authentication != null) {
			String username = authentication.getName();
			return (RegisteredUser) userRepository.findOneByUsername(username);
		}
		return null;
	}
}
