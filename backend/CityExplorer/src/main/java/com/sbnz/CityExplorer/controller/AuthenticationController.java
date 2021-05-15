package com.sbnz.CityExplorer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sbnz.CityExplorer.dto.LoginDTO;
import com.sbnz.CityExplorer.dto.RegistrationDTO;
import com.sbnz.CityExplorer.dto.TokenDTO;
import com.sbnz.CityExplorer.service.AuthenticationService;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthenticationController {
	
	@Autowired
	private AuthenticationService userService;

	@PostMapping(value = "/login")
	public  ResponseEntity<TokenDTO> logIn( @RequestBody LoginDTO dto ) {
		try {
			String token = userService.login(dto);
			return new ResponseEntity<TokenDTO>(new TokenDTO(token), HttpStatus.OK);
		} catch (BadCredentialsException e) {
			System.out.println("Bad credentials with username " + dto.getUsername());
			return new ResponseEntity<TokenDTO>(new TokenDTO(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping(value = "/registration")
	public ResponseEntity<Boolean> register (@RequestBody RegistrationDTO dto){
		if (userService.register(dto) == null) {
			return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Boolean>(true, HttpStatus.OK);
	}
	
}
