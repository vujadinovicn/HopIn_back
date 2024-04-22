package com.hopin.HopIn.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hopin.HopIn.entities.FavoriteRide;
import com.hopin.HopIn.entities.Passenger;
import com.hopin.HopIn.entities.Route;

@Repository
public interface PassengerRepository extends JpaRepository<Passenger, Integer>{
	
	public Optional<Passenger> findPassengerByEmail(String email);
	
	@Query("select favouriteRoutes from Passenger p where p.id = ?1")
	public List<Route> findAllRoutesById(int id);
	
	@Query("select favouriteRides from Passenger p where p.id = ?1")
	public List<FavoriteRide> findAllFavoriteRidesById(int id);
}
