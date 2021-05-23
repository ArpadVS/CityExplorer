package com.sbnz.CityExplorer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sbnz.CityExplorer.dto.ActivityDTO;
import com.sbnz.CityExplorer.service.ActivityService;

@RestController
@RequestMapping("/api/activities")
@CrossOrigin
public class ActivityController {

	@Autowired
	private ActivityService activityService;

	@GetMapping
	public ResponseEntity<List<ActivityDTO>> getActivities() {
		List<ActivityDTO> restaurants = activityService.getAll();
		return new ResponseEntity<List<ActivityDTO>>(restaurants, HttpStatus.OK);
	}

	@GetMapping(value = "details/{id}")
	public ResponseEntity<ActivityDTO> getRestaurant(@PathVariable("id") Long id) {
		ActivityDTO activity = activityService.getActivity(id);
		return new ResponseEntity<ActivityDTO>(activity, HttpStatus.OK);
	}

}
