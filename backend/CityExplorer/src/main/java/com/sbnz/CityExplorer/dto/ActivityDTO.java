package com.sbnz.CityExplorer.dto;

import java.util.Date;

public class ActivityDTO {

	private Long id;
	private String name;
	private String description;
	private String location;
	private String address;
	private String imageUrl;
	private double averageRating;
	private FeaturesDTO features;
	private Date alarmCreation;

	private ReportDTO report;

	public ActivityDTO() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public FeaturesDTO getFeatures() {
		return features;
	}

	public void setFeatures(FeaturesDTO features) {
		this.features = features;
	}

	public ReportDTO getReport() {
		return report;
	}

	public void setReport(ReportDTO report) {
		this.report = report;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public double getAverageRating() {
		return averageRating;
	}

	public void setAverageRating(double averageRating) {
		this.averageRating = averageRating;
	}

	public Date getAlarmCreation() {
		return alarmCreation;
	}

	public void setAlarmCreation(Date alarmCreation) {
		this.alarmCreation = alarmCreation;
	}

	@Override
	public String toString() {
		return "ActivityDTO [id=" + id + ", name=" + name + ", description=" + description + ", location=" + location
				+ ", address=" + address + ", features=" + features.toString() + "]";
	}

}
