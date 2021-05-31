package com.sbnz.CityExplorer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.sbnz.CityExplorer.dto.RatingDTO;
import com.sbnz.CityExplorer.dto.SearchDTO;
import com.sbnz.CityExplorer.dto.UserRequirementsDTO;
import com.sbnz.CityExplorer.service.ActivityService;

@RestController
@RequestMapping("/api/activities")
@CrossOrigin
public class ActivityController {

	@Autowired
	private ActivityService activityService;

	@GetMapping
	public ResponseEntity<List<ActivityDTO>> getActivities() {
		List<ActivityDTO> activities = activityService.getAll();
		return new ResponseEntity<List<ActivityDTO>>(activities, HttpStatus.OK);
	}

	@GetMapping(value = "details/{id}")
	public ResponseEntity<ActivityDTO> getActivity(@PathVariable("id") Long id) {
		ActivityDTO activity = activityService.getActivity(id);
		return new ResponseEntity<ActivityDTO>(activity, HttpStatus.OK);
	}

	@PostMapping(value = "/search")
	public ResponseEntity<List<ActivityDTO>> search(@RequestBody SearchDTO dto) {
		List<ActivityDTO> activities = activityService.search(dto);
		return new ResponseEntity<List<ActivityDTO>>(activities, HttpStatus.OK);
	}

	@PostMapping(value = "/getRecommendation")
	@PreAuthorize("hasAuthority('ROLE_REGISTERED_USER')")
	public ResponseEntity<ActivityDTO> recommendation(@RequestBody UserRequirementsDTO dto) {
		ActivityDTO activity = activityService.getRecommendation(dto);
		return new ResponseEntity<ActivityDTO>(activity, HttpStatus.OK);
	}

	@PostMapping(value = "/rate")
	@PreAuthorize("hasAuthority('ROLE_REGISTERED_USER')")
	public ResponseEntity<Boolean> rateActivity(@RequestBody RatingDTO dto) {
		boolean result = activityService.rateActivity(dto);
		return new ResponseEntity<Boolean>(result, HttpStatus.OK);
	}

}
