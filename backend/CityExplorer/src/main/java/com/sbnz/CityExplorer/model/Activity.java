package com.sbnz.CityExplorer.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Activity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column
	private String name;
	@Column
	private String description;
	@Enumerated(EnumType.STRING)
	private Location location;
	@Column
	private String address;
	@OneToOne
	private Features features;
	@OneToMany
	private Set<Rating> ratings;

	public Activity() {
		super();
		this.ratings = new HashSet<Rating>();
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

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Features getFeatures() {
		return features;
	}

	public void setFeatures(Features features) {
		this.features = features;
	}

	public Set<Rating> getRatings() {
		return ratings;
	}

	public void setRatings(Set<Rating> ratings) {
		this.ratings = ratings;
	}

	@Override
	public String toString() {
		return "Activity [id=" + id + ", name=" + name + ", description=" + description + ", location=" + location
				+ ", address=" + address + ", features=" + features + "]";
	}

}