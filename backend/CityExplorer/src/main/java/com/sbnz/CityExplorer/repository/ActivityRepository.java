package com.sbnz.CityExplorer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sbnz.CityExplorer.model.Activity;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {
	

	@Query("SELECT a FROM Activity a WHERE  a.alarm != null")
	List<Activity> findAlarmedActivities();

}
