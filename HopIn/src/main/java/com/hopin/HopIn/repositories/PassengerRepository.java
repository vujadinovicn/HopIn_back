package com.hopin.HopIn.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hopin.HopIn.entities.Passenger;
import com.hopin.HopIn.entities.Route;

public interface PassengerRepository extends JpaRepository<Passenger, Integer>{
	
	@Query("select favouriteRoutes from Passenger p where p.id = ?1")
	public List<Route> findAllRoutesById(int id);
}