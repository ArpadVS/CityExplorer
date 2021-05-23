package com.sbnz.CityExplorer.dto;

public class ReportDTO {

	private double average;
	private int numOfRatings;
	private int ones;
	private int twos;
	private int threes;
	private int fours;
	private int fives;
	private int myRating;

	public ReportDTO() {
		// TODO Auto-generated constructor stub
	}

	public ReportDTO(double average, int numOfRatings, int ones, int twos, int threes, int fours, int fives,
			int myRating) {
		super();
		this.average = average;
		this.numOfRatings = numOfRatings;
		this.ones = ones;
		this.twos = twos;
		this.threes = threes;
		this.fours = fours;
		this.fives = fives;
		this.myRating = myRating;
	}

	public ReportDTO(double average, int numOfRatings) {
		super();
		this.average = average;
		this.numOfRatings = numOfRatings;
		this.ones = 0;
		this.twos = 0;
		this.threes = 0;
		this.fours = 0;
		this.fives = 0;
	}

	public double getAverage() {
		return average;
	}

	public void setAverage(double average) {
		this.average = average;
	}

	public int getOnes() {
		return ones;
	}

	public void setOnes(int ones) {
		this.ones = ones;
	}

	public int getTwos() {
		return twos;
	}

	public void setTwos(int twos) {
		this.twos = twos;
	}

	public int getThrees() {
		return threes;
	}

	public void setThrees(int threes) {
		this.threes = threes;
	}

	public int getFours() {
		return fours;
	}

	public void setFours(int fours) {
		this.fours = fours;
	}

	public int getFives() {
		return fives;
	}

	public void setFives(int fives) {
		this.fives = fives;
	}

	public int getNumOfRatings() {
		return numOfRatings;
	}

	public void setNumOfRatings(int numOfRatings) {
		this.numOfRatings = numOfRatings;
	}

	public int getMyReview() {
		return myRating;
	}

	public void setMyReview(int myReview) {
		this.myRating = myReview;
	}

}
