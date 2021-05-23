package com.sbnz.CityExplorer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sbnz.CityExplorer.model.Activity;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {
	

}
