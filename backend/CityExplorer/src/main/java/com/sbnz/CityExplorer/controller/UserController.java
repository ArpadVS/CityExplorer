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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sbnz.CityExplorer.dto.RegistrationDTO;
import com.sbnz.CityExplorer.service.UserService;

@RestController
@RequestMapping("/api/users")
@CrossOrigin
public class UserController {
	
	@Autowired
	UserService userService;
	
	
	@GetMapping
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<List<RegistrationDTO>> getUsers(Pageable pageable) {
		List<RegistrationDTO> users = userService.getUsers(pageable);
		return new ResponseEntity<List<RegistrationDTO>>(users, HttpStatus.OK);
	}
	
	
	@GetMapping(value = "/deactivateUser/{username}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<Boolean> deactivateUser(@PathVariable("username") String username){
		boolean result = userService.changeUserStatus(username, false);
		return new ResponseEntity<Boolean>(result, HttpStatus.OK);
	}
	

	@GetMapping(value = "/activateUser/{username}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<Boolean> activateUser(@PathVariable("username") String username){
		boolean result = userService.changeUserStatus(username, true);
		return new ResponseEntity<Boolean>(result, HttpStatus.OK);
	}

}
