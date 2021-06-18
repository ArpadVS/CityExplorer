package com.sbnz.CityExplorer.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sbnz.CityExplorer.converter.RegistrationDTOConverter;
import com.sbnz.CityExplorer.dto.RegistrationDTO;
import com.sbnz.CityExplorer.model.RegisteredUser;
import com.sbnz.CityExplorer.model.User;
import com.sbnz.CityExplorer.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;

	public List<RegistrationDTO> getUsers(Pageable pageable) {
		Page<User> users = userRepository.findAll(pageable);

		List<RegistrationDTO> dtos = new ArrayList<RegistrationDTO>();

		for (User u : users) {
			try {
				RegistrationDTO dto = RegistrationDTOConverter.convertToDTO((RegisteredUser) u);
				dto.setSize(users.getTotalElements());
				dtos.add(dto);
			} catch (Exception e) {
				continue;
			}
		}
		return dtos;
	}

	public boolean changeUserStatus(String username, boolean setActive) {
		User user = userRepository.findOneByUsername(username);
		if (user.isActive() != setActive) {
			user.setActive(setActive);
			userRepository.save(user);
		}
		return true;
	}

}
