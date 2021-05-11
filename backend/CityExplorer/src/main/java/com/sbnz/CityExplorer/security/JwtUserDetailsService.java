package com.sbnz.CityExplorer.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.sbnz.CityExplorer.repository.UserRepository;
import com.sbnz.CityExplorer.model.User;


@Service
public class JwtUserDetailsService implements UserDetailsService {

	private UserRepository repository;
	
	@Autowired
	public JwtUserDetailsService(UserRepository repository){
		this.repository = repository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = repository.findOneByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
		}
		if (!user.isActive()) {
			throw new IllegalArgumentException("This account is blocked");
		}
		return user;
	}
}
