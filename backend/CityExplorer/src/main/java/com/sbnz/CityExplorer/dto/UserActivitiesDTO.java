package com.sbnz.CityExplorer.dto;

import java.util.List;

import com.sbnz.CityExplorer.model.Activity;
import com.sbnz.CityExplorer.model.RegisteredUser;

public class UserActivitiesDTO {
	private RegisteredUser user;
	private List<Activity> activities;

	public UserActivitiesDTO() {
	}

	public UserActivitiesDTO(RegisteredUser user, List<Activity> activities) {
		super();
		this.user = user;
		this.activities = activities;
	}

	public RegisteredUser getUser() {
		return user;
	}

	public void setUser(RegisteredUser user) {
		this.user = user;
	}

	public List<Activity> getActivities() {
		return activities;
	}

	public void setActivities(List<Activity> activities) {
		this.activities = activities;
	}

}
