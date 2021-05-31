package com.sbnz.CityExplorer.dto;


public class PopularityDTO {

	private ActivityDTO mostRecommended;
	private ActivityDTO leastRecommended;
	
	public PopularityDTO() {
	}
	

	public PopularityDTO(ActivityDTO mostRecommended, ActivityDTO leastRecommended) {
		super();
		this.mostRecommended = mostRecommended;
		this.leastRecommended = leastRecommended;
	}


	public ActivityDTO getMostRecommended() {
		return mostRecommended;
	}

	public void setMostRecommended(ActivityDTO mostRecommended) {
		this.mostRecommended = mostRecommended;
	}

	public ActivityDTO getLeastRecommended() {
		return leastRecommended;
	}

	public void setLeastRecommended(ActivityDTO leastRecommended) {
		this.leastRecommended = leastRecommended;
	}
	
}
