package com.hopin.HopIn.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hopin.HopIn.entities.Review;
import com.hopin.HopIn.entities.Route;

public interface ReviewRepository extends JpaRepository<Review, Integer>{
	@Query("select favouriteRoutes from Passenger p where p.id = ?1")
	public List<Route> findAllRoutesById(int id);
	
	@Query("select r from Review r where r.ride.driver.vehicle.id = ?1 and r.type = 0")
	public List<Review> findAllReviewsByVehicleId(int id);
}
