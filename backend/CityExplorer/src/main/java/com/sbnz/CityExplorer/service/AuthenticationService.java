package com.sbnz.CityExplorer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.sbnz.CityExplorer.dto.LoginDTO;
import com.sbnz.CityExplorer.dto.RegistrationDTO;
import com.sbnz.CityExplorer.model.RegisteredUser;
import com.sbnz.CityExplorer.model.User;
import com.sbnz.CityExplorer.repository.UserRepository;
import com.sbnz.CityExplorer.security.JwtToken;

@Service
public class AuthenticationService {

	@Autowired
	UserRepository userRepository;
	@Autowired
	DroolsService droolsService;
	@Autowired
	JwtToken jwtToken;
	@Autowired 
	AuthenticationManager authenticationManager;
	

	public String login(LoginDTO dto) {
		try {

			authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()));

		} catch (BadCredentialsException | InternalAuthenticationServiceException e) {
			//TO do - login event for bad uname or pw
			throw new BadCredentialsException("Bad credentials.");
		}
		User logged = userRepository.findOneByUsername(dto.getUsername());
		return jwtToken.generateToken(dto.getUsername(), logged.getRole().toString());
		
	}
	
	public RegisteredUser register (RegistrationDTO dto) {
		return new RegisteredUser();
	}
	
	
}
