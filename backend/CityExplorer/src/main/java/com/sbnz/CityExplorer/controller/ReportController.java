package com.sbnz.CityExplorer.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sbnz.CityExplorer.dto.ActivityDTO;
import com.sbnz.CityExplorer.dto.UserSatisfactionDTO;
import com.sbnz.CityExplorer.dto.PopularityDTO;
import com.sbnz.CityExplorer.dto.RatingRangeDTO;
import com.sbnz.CityExplorer.service.ReportService;

@RestController
@RequestMapping("/api/reports")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
@CrossOrigin
public class ReportController {

	@Autowired
	private ReportService reportService;

	@GetMapping(value = "/popularityReport")
	public ResponseEntity<PopularityDTO> getPopularityReport() {
		PopularityDTO dto = this.reportService.getPopularityReport();
		return new ResponseEntity<PopularityDTO>(dto, HttpStatus.OK);
	}

	@GetMapping(value = "/getDissatisfiedUsers")
	public ResponseEntity<Set<UserSatisfactionDTO>> getDissatisfiedUsers() {
		Set<UserSatisfactionDTO> dtoList = this.reportService.getDissatisfiedUsers();
		return new ResponseEntity<Set<UserSatisfactionDTO>>(dtoList, HttpStatus.OK);
	}

	@GetMapping(value = "/getSatisfiedUsers")
	public ResponseEntity<Set<UserSatisfactionDTO>> getSatisfiedUsers() {
		Set<UserSatisfactionDTO> dtoList = this.reportService.getSatisfiedUsers();
		return new ResponseEntity<Set<UserSatisfactionDTO>>(dtoList, HttpStatus.OK);
	}

	@GetMapping(value = "/getAlarms")
	public ResponseEntity<List<ActivityDTO>> getAlarms() {
		List<ActivityDTO> activities = reportService.getAlarmedActivities();
		return new ResponseEntity<List<ActivityDTO>>(activities, HttpStatus.OK);
	}

	@PostMapping(value = "/getByRatingRange")
	public ResponseEntity<List<ActivityDTO>> getactivitiesByRatingRange(@RequestBody RatingRangeDTO dto) {
		List<ActivityDTO> activities = reportService.getActivitiesByRatingRange(dto);
		return new ResponseEntity<List<ActivityDTO>>(activities, HttpStatus.OK);
	}
}
