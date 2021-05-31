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
import com.sbnz.CityExplorer.dto.DissatisfiedUsersDTO;
import com.sbnz.CityExplorer.dto.PopularityDTO;
import com.sbnz.CityExplorer.dto.RatingRange;
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
	public ResponseEntity<Set<DissatisfiedUsersDTO>> getDissatisfiedUsers() {
		Set<DissatisfiedUsersDTO> dtoList = this.reportService.getDissatisfiedUsers();
		return new ResponseEntity<Set<DissatisfiedUsersDTO>>(dtoList, HttpStatus.OK);
	}

	@GetMapping(value = "/getSatisfiedUsers")
	public ResponseEntity<Set<DissatisfiedUsersDTO>> getSatisfiedUsers() {
		Set<DissatisfiedUsersDTO> dtoList = this.reportService.getSatisfiedUsers();
		return new ResponseEntity<Set<DissatisfiedUsersDTO>>(dtoList, HttpStatus.OK);
	}

	@GetMapping(value = "/getAlarms")
	public ResponseEntity<List<ActivityDTO>> getAlarms() {
		List<ActivityDTO> activities = reportService.getAlarms();
		return new ResponseEntity<List<ActivityDTO>>(activities, HttpStatus.OK);
	}

	@PostMapping(value = "/getByRatingRange")
	public ResponseEntity<List<ActivityDTO>> getactivitiesByRatingRange(@RequestBody RatingRange dto) {
		List<ActivityDTO> activities = reportService.getActivitiesByRatingRange(dto);
		return new ResponseEntity<List<ActivityDTO>>(activities, HttpStatus.OK);
	}
}
