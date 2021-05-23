package com.sbnz.CityExplorer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sbnz.CityExplorer.model.Rating;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {

}
