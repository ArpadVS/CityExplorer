package com.sbnz.CityExplorer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sbnz.CityExplorer.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	public User findOneByUsername(String username);
	public User findOneByEmail(String email);
}
