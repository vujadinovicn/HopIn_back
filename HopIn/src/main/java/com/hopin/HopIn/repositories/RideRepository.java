package com.hopin.HopIn.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hopin.HopIn.entities.Ride;

public interface RideRepository extends JpaRepository<Ride, Integer> {
	
	@Query(value = "select * from \"rides\" where \"id\" in (select \"ride_id\" from \"rides_passengers\" where \"passengers_id\" = 1) and \"start_time\" between '2022-12-19' and '2022-12-22';", nativeQuery=true)
	public List<Ride> getAllPassengerRidesBetweenDates(int id, LocalDateTime from, LocalDateTime to);
}
