package com.hopin.HopIn.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hopin.HopIn.entities.Ride;

public interface RideRepository extends JpaRepository<Ride, Integer> {
	
	@Query(value = "select * from \"rides\" where \"id\" in (select \"ride_id\" from \"rides_passengers\" where \"passengers_id\" = :id) and \"start_time\" between :from and :to order by \"start_time\"", nativeQuery=true)
	public List<Ride> getAllPassengerRidesBetweenDates(int id, LocalDateTime from, LocalDateTime to);
	
	@Query(value = "select * from \"rides\" where \"driver_id\" = :id and \"start_time\" between :from and :to order by \"start_time\"", nativeQuery=true)
	public List<Ride> getAllDriverRidesBetweenDates(int id, LocalDateTime from, LocalDateTime to);

	@Query(value = "select * from \"rides\" where \"driver_id\" = :id and \"status\" = 3", nativeQuery=true)
	public Ride getActiveRideForDriver(int id);
	
	@Query(value = "select * from \"rides\" where \"status\" = 3 and "
			+ "\"id\" in (select \"ride_id\" from \"rides_passengers\" where \"passengers_id\" = :id)", nativeQuery=true)
	public Ride getActiveRideForPassenger(int id);
	
	@Query(value = "select * from \"rides\" where \"driver_id\" = :id and \"status\" = 1 order by \"start_time\" limit 1", nativeQuery=true)
	public Ride getFirstUpcomingRideForDriver(int id);
	
	@Query(value = "select * from \"rides\" where \"driver_id\" = :id and \"status\" = 1 and \"start_time\" < :tomorrow ", nativeQuery=true)
	public List<Ride> getAllScheduledRideForTodayForDriver(int driverId, LocalDateTime tomorrow);
}
