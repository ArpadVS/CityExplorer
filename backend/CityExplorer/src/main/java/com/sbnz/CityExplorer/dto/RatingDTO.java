package com.sbnz.CityExplorer.dto;

public class RatingDTO {
	private Long activityId;
	private int rating;

	public RatingDTO() {
		super();
	}

	public Long getActivityId() {
		return activityId;
	}

	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

}
