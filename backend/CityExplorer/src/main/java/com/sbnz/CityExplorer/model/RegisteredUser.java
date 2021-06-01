package com.sbnz.CityExplorer.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;

@Entity
public class RegisteredUser extends User {
	private static final long serialVersionUID = 1L;

	@ManyToMany
	private List<Activity> recommendedActivities;

	public RegisteredUser() {
		super();
	}
	
	public RegisteredUser(Long id, String uname, ArrayList<Activity> recommended) {
		this.setId(id);
		this.setUsername(uname);
		this.setRecommendedActivities(recommended);
	}

	public RegisteredUser(List<Activity> recommendedActivities) {
		super();
		this.recommendedActivities = recommendedActivities;
	}

	public List<Activity> getRecommendedActivities() {
		return recommendedActivities;
	}

	public void setRecommendedActivities(List<Activity> recommendedActivities) {
		this.recommendedActivities = recommendedActivities;
	}

	@Override
	public String toString() {
		return "RegisteredUser [recommendedActivities=" + recommendedActivities + "]";
	}

}