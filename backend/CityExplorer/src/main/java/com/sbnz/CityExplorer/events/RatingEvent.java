package com.sbnz.CityExplorer.events;

import java.io.Serializable;
import java.util.Date;

import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;
import org.kie.api.definition.type.Timestamp;

import com.sbnz.CityExplorer.model.Rating;

@Role(Role.Type.EVENT)
@Timestamp("executionTime")
@Expires("2h00m")
public class RatingEvent implements Serializable {

	private static final long serialVersionUID = 1L;
	private Date executionTime;
	private Rating rating;

	public RatingEvent() {
	}

	public RatingEvent(Date executionTime, Rating Rating) {
		this.executionTime = executionTime;
		this.rating = Rating;
	}

	public Date getExecutionTime() {
		return executionTime;
	}

	public void setExecutionTime(Date executionTime) {
		this.executionTime = executionTime;
	}

	public Rating getRating() {
		return rating;
	}

	public void setRating(Rating Rating) {
		this.rating = Rating;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
