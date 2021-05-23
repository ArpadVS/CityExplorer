package com.sbnz.CityExplorer.dto;

import java.util.List;

public class FeaturesDTO {

	private boolean parking;
	private boolean busNearby;
	private boolean reservations;
	private boolean wifi;
	private boolean tv;
	private boolean childrensProgram;
	private boolean outdoor;
	private String space;
	private String price;
	private List<String> keywords;

	public FeaturesDTO() {
		super();
	}

	public boolean isParking() {
		return parking;
	}

	public void setParking(boolean parking) {
		this.parking = parking;
	}

	public boolean isBusNearby() {
		return busNearby;
	}

	public void setBusNearby(boolean busNearby) {
		this.busNearby = busNearby;
	}

	public boolean isReservations() {
		return reservations;
	}

	public void setReservations(boolean reservations) {
		this.reservations = reservations;
	}

	public boolean isWifi() {
		return wifi;
	}

	public void setWifi(boolean wifi) {
		this.wifi = wifi;
	}

	public boolean isTv() {
		return tv;
	}

	public void setTv(boolean tv) {
		this.tv = tv;
	}

	public boolean isChildrensProgram() {
		return childrensProgram;
	}

	public void setChildrensProgram(boolean childrensProgram) {
		this.childrensProgram = childrensProgram;
	}

	public boolean isOutdoor() {
		return outdoor;
	}

	public void setOutdoor(boolean outdoor) {
		this.outdoor = outdoor;
	}

	public String getSpace() {
		return space;
	}

	public void setSpace(String space) {
		this.space = space;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public List<String> getKeywords() {
		return keywords;
	}

	public void setKeywords(List<String> keywords) {
		this.keywords = keywords;
	}

	@Override
	public String toString() {
		return "FeaturesDTO [parking=" + parking + ", busNearby=" + busNearby + ", reservations=" + reservations
				+ ", wifi=" + wifi + ", tv=" + tv + ", childrensProgram=" + childrensProgram + ", outdoor=" + outdoor
				+ ", space=" + space + ", price=" + price + ", keywords=" + keywords + "]";
	}

}
