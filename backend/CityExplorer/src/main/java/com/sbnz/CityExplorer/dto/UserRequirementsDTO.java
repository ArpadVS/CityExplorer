package com.sbnz.CityExplorer.dto;

import java.time.LocalDate;
import java.util.List;

import com.sbnz.CityExplorer.model.Features;

public class UserRequirementsDTO {
	private List<String> price;
	private String companion;
	private int numPeople;
	private String transportation;
	private String theme;
	private boolean special;
	private LocalDate date;

	public UserRequirementsDTO() {
		super();
	}

	public UserRequirementsDTO(List<String> price, String companion, int numPeople, String transportation, String theme,
			boolean special, LocalDate date, Features activityFeatures) {
		super();
		this.price = price;
		this.companion = companion;
		this.numPeople = numPeople;
		this.transportation = transportation;
		this.theme = theme;
		this.special = special;
		this.date = date;
	}

	public List<String> getPrice() {
		return price;
	}

	public void setPrice(List<String> price) {
		this.price = price;
	}

	public String getCompanion() {
		return companion;
	}

	public void setCompanion(String companion) {
		this.companion = companion;
	}

	public int getNumPeople() {
		return numPeople;
	}

	public void setNumPeople(int numPeople) {
		this.numPeople = numPeople;
	}

	public String getTransportation() {
		return transportation;
	}

	public void setTransportation(String transportation) {
		this.transportation = transportation;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public boolean isSpecial() {
		return special;
	}

	public void setSpecial(boolean special) {
		this.special = special;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}
	
	public int getMonth() {
		return this.date.getMonthValue();
	}

	@Override
	public String toString() {
		return "UserRequirementsDTO [price=" + price + ", companion=" + companion + ", numPeople=" + numPeople
				+ ", transportation=" + transportation + ", theme=" + theme + ", special=" + special + ", date=" + date;
	}

}
