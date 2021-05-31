package com.sbnz.CityExplorer.dto;

public class RatingRangeDTO {

	private double minRating;
	private double maxRating;

	public RatingRangeDTO() {
	}

	public RatingRangeDTO(double minRating, double maxRating) {
		super();
		this.minRating = minRating;
		this.maxRating = maxRating;
	}

	public double getMinRating() {
		return minRating;
	}

	public void setMinRating(double minRating) {
		this.minRating = minRating;
	}

	public double getMaxRating() {
		return maxRating;
	}

	public void setMaxRating(double maxRating) {
		this.maxRating = maxRating;
	}

}
