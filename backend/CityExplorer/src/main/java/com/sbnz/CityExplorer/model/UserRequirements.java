package com.sbnz.CityExplorer.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UserRequirements {

	private List<Price> price = new ArrayList<Price>();
	private Companion companion;
	private int numPeople;
	private Transportation transportation;
	private Theme theme;
	private boolean special;
	private LocalDate date;
	private Features activityFeatures;

	public UserRequirements() {
		this.activityFeatures = new Features();
		this.activityFeatures.setBusNearby(false);
		this.activityFeatures.setChildrensProgram(false);
		this.activityFeatures.setReservations(false);
		this.activityFeatures.setWifi(false);
		this.activityFeatures.setOutdoor(false);
		this.activityFeatures.setParking(false);
		this.date = LocalDate.now();
	}

	public List<Price> getPrice() {
		return price;
	}

	public void setPrice(List<Price> price) {
		this.price = price;
	}

	public Companion getCompanion() {
		return companion;
	}

	public void setCompanion(Companion companion) {
		this.companion = companion;
	}

	public int getNumPeople() {
		return numPeople;
	}

	public void setNumPeople(int numPeople) {
		this.numPeople = numPeople;
	}

	public Transportation getTransportation() {
		return transportation;
	}

	public void setTransportation(Transportation transportation) {
		this.transportation = transportation;
	}

	public Theme getTheme() {
		return theme;
	}

	public void setTheme(Theme theme) {
		this.theme = theme;
	}

	public boolean isSpecial() {
		return special;
	}

	public void setSpecial(boolean special) {
		this.special = special;
	}

	public Features getActivityFeatures() {
		return activityFeatures;
	}

	public void setActivityFeatures(Features activityFeatures) {
		this.activityFeatures = activityFeatures;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "UserRequirements [price=" + price + ", companion=" + companion + ", numPeople=" + numPeople
				+ ", transportation=" + transportation + ", theme=" + theme + ", special=" + special + ", date=" + date
				+ ", activityFeatures=" + activityFeatures + "]";
	}

}