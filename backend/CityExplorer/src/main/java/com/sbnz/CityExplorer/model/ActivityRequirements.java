package com.sbnz.CityExplorer.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class ActivityRequirements {

	private Location location;
	// need to have prices here because feature only has 1
	private List<Price> prices;
	private Features features;

	public ActivityRequirements() {
		this.features = new Features();
		this.features.setBusNearby(false);
		this.features.setChildrensProgram(false);
		this.features.setKeywords(new HashSet<Keywords>());
		this.features.setOutdoor(false);
		this.features.setParking(false);
		this.features.setPrice(Price.FREE);
		this.prices = new ArrayList<Price>();

	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public List<Price> getPrices() {
		return prices;
	}

	public void setPrices(List<Price> prices) {
		this.prices = prices;
	}

	public Features getFeatures() {
		return features;
	}

	public void setFeatures(Features features) {
		this.features = features;
	}

	@Override
	public String toString() {
		return "ActivityRequirements [location=" + location + ", prices=" + prices + ", features=" + features + "]";
	}

}