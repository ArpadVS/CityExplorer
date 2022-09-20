package com.sbnz.CityExplorer.service;

import java.util.Date;

import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.sbnz.CityExplorer.converter.RegistrationDTOConverter;
import com.sbnz.CityExplorer.dto.LoginDTO;
import com.sbnz.CityExplorer.dto.RegistrationDTO;
import com.sbnz.CityExplorer.dto.UserDTO;
import com.sbnz.CityExplorer.events.BadCredentialsEvent;
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
			// checking if username exists, so it means pw is wrong
			User user = userRepository.findOneByUsername(dto.getUsername());
			if (user != null && user.isActive()) {
				BadCredentialsEvent failedLogin = new BadCredentialsEvent(user, new Date());
				KieSession kieSession = droolsService.getEventsSession();
				kieSession.getAgenda().getAgendaGroup("login").setFocus();
				kieSession.setGlobal("userRepository", userRepository);
				kieSession.insert(failedLogin);
				kieSession.fireAllRules();
			}
			throw new BadCredentialsException("Bad credentials.");
		}
		User logged = userRepository.findOneByUsername(dto.getUsername());
		return jwtToken.generateToken(dto.getUsername(), logged.getRole().toString());

	}

	public RegisteredUser register(RegistrationDTO dto) {
		if (userRepository.findOneByUsername(dto.getUsername()) != null) {
			System.out.println("Username taken.");
			return null;
		}

		if (userRepository.findOneByEmail(dto.getEmail()) != null) {
			System.out.println("Email already exists.");
			return null;
		}
		RegisteredUser user = RegistrationDTOConverter.convertFromDTO(dto);
		userRepository.save(user);
		return user;
	}

	public UserDTO whoAmI() {
		UserDTO dto = new UserDTO(getCurrentUser());
		return dto;
	}

	private User getCurrentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken) && authentication != null) {
			String username = authentication.getName();
			return userRepository.findOneByUsername(username);
		}
		return null;
	}

}
