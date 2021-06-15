package com.sbnz.CityExplorer.converter;

import java.sql.Date;
import java.util.stream.Collectors;

import com.sbnz.CityExplorer.dto.ActivityDTO;
import com.sbnz.CityExplorer.dto.FeaturesDTO;
import com.sbnz.CityExplorer.dto.ReportDTO;
import com.sbnz.CityExplorer.model.Activity;
import com.sbnz.CityExplorer.model.Features;

public class ActivityDTOConverter {

	public static ActivityDTO convertToDTO(Activity activity) {
		ActivityDTO dto = new ActivityDTO();
		dto.setId(activity.getId());
		dto.setName(activity.getName());
		dto.setDescription(activity.getDescription());
		if(activity.getLocation()!= null) {
			dto.setLocation(activity.getLocation().toString());
		}
		dto.setAddress(activity.getAddress());
		dto.setImageUrl(activity.getImageUrl());
		if(activity.getAverage() != null) {
			dto.setAverageRating(activity.getAverage());
		}
		if(activity.getAlarm()!= null) {
			dto.setAlarmCreation(Date.valueOf(activity.getAlarm()));
		}
		if (activity.getFeatures() != null) {
			dto.setFeatures(ActivityDTOConverter.convertFeaturesToDTO(activity.getFeatures()));
		}

		return dto;
	}

	public static FeaturesDTO convertFeaturesToDTO(Features f) {
		FeaturesDTO dto = new FeaturesDTO();

		dto.setParking(f.isParking());
		dto.setBusNearby(f.isBusNearby());
		dto.setReservations(f.isReservations());
		dto.setTv(f.isTv());
		dto.setWifi(f.isTv());
		dto.setChildrensProgram(f.isChildrensProgram());
		dto.setOutdoor(f.isOutdoor());
		dto.setSpace(f.getSpace().toString());
		dto.setPrice(f.getPrice().toString());
		dto.setKeywords(f.getKeywords().stream().map(feature -> feature.toString()).collect(Collectors.toList()));
		return dto;
	}

	public static ActivityDTO convertToDTO(Activity activity, ReportDTO reportDTO) {
		ActivityDTO dto = ActivityDTOConverter.convertToDTO(activity);
		dto.setReport(reportDTO);
		return dto;
	}
}
