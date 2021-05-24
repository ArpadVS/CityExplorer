package com.sbnz.CityExplorer.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Features {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column
	private boolean parking;
	@Column
	private boolean busNearby;
	@Column
	private boolean reservations;
	@Column
	private boolean wifi;
	@Column
	private boolean tv;
	@Column
	private boolean childrensProgram;
	@Column
	private boolean outdoor;
	@Enumerated(EnumType.STRING)
	private Space space;
	@Enumerated(EnumType.STRING)
	private Price price;
	@Column
	@Enumerated(EnumType.STRING)
	@ElementCollection(targetClass = Keywords.class)
	private Set<Keywords> keywords;

	public Features() {
		super();
		this.keywords = new HashSet<Keywords>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Space getSpace() {
		return space;
	}

	public void setSpace(Space space) {
		this.space = space;
	}

	public Price getPrice() {
		return price;
	}

	public void setPrice(Price price) {
		this.price = price;
	}

	public Set<Keywords> getKeywords() {
		return keywords;
	}

	public void setKeywords(Set<Keywords> keywords) {
		this.keywords = keywords;
	}

	@Override
	public String toString() {
		return "Features [parking=" + parking + ", busNearby=" + busNearby + ", reservations="
				+ reservations + ", wifi=" + wifi + ", tv=" + tv + ", childrensProgram=" + childrensProgram
				+ ", outdoor=" + outdoor + ", space=" + space + ", price=" + price + ", keywords=" + keywords + "]";
	}

}