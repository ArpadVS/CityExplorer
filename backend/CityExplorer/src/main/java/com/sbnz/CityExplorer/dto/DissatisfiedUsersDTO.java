package com.sbnz.CityExplorer.dto;

import java.util.ArrayList;
import java.util.List;

public class DissatisfiedUsersDTO {

	private RegistrationDTO user;
	private List<ActivityDTO> activities;

	public DissatisfiedUsersDTO() {
		this.activities = new ArrayList<ActivityDTO>();
	}

	public RegistrationDTO getUser() {
		return user;
	}

	public void setUser(RegistrationDTO user) {
		this.user = user;
	}

	public List<ActivityDTO> getActivities() {
		return activities;
	}

	public void setActivities(List<ActivityDTO> activities) {
		this.activities = activities;
	}

}
