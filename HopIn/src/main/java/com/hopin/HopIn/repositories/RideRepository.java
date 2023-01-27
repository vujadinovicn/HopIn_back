package com.hopin.HopIn.repositories;

import java.time.LocalDateTime;
import org.springframework.data.domain.Pageable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.hopin.HopIn.entities.Ride;

public interface RideRepository extends JpaRepository<Ride, Integer>, PagingAndSortingRepository<Ride, Integer> {
	
	@Query(value = "select * from \"rides\" where \"start_time\" between :from and :to order by \"start_time\"", nativeQuery=true)
	public List<Ride> getAllRidesBetweenDates(LocalDateTime from, LocalDateTime to);
	
	@Query(value = "select * from \"rides\" where \"id\" in (select \"ride_id\" from \"rides_passengers\" where \"passengers_id\" = :id) and \"start_time\" between :from and :to order by \"start_time\"", nativeQuery=true)
	public List<Ride> getAllPassengerRidesBetweenDates(int id, LocalDateTime from, LocalDateTime to);
	
	@Query(value = "select * from \"rides\" where \"driver_id\" = :id and \"start_time\" between :from and :to order by \"start_time\"", nativeQuery=true)
	public List<Ride> getAllDriverRidesBetweenDates(int id, LocalDateTime from, LocalDateTime to);
	
	@Query(value = "select * from \"rides\" where \"id\" in (select \"ride_id\" from \"rides_passengers\" where \"passengers_id\" = :id ) order by \"start_time\"", nativeQuery=true)
	public List<Ride> getAllPassengerRidesPaginated(int id, Pageable pageable);
	
	@Query(value = "select * from \"rides\" where \"id\" in (select \"ride_id\" from \"rides_passengers\" where \"passengers_id\" = :id) order by \"start_time\" desc", nativeQuery=true)
	public List<Ride> getAllPassengerRides(int id);
	
	public List<Ride> findAllByDriverId(int id, Pageable pageable);
	
	public List<Ride> findAllByDriverId(int id);
	
	@Query(value = "select * from \"rides\" where \"driver_id\" = :id and \"status\" = 6", nativeQuery=true)
	public Ride getActiveRideForDriver(int id);
	
	@Query(value = "select * from \"rides\" where \"driver_id\" = :id and \"status\" = 0", nativeQuery=true)
	public Ride getPendingRideForDriver(int id);
	
	@Query(value = "select * from \"rides\" where \"status\" = 6 and "
			+ "\"id\" in (select \"ride_id\" from \"rides_passengers\" where \"passengers_id\" = :id)", nativeQuery=true)
	public Ride getActiveRideForPassenger(int id);
	
	@Query(value = "select * from \"rides\" where \"status\" = 0 and "
			+ "\"id\" in (select \"ride_id\" from \"rides_passengers\" where \"passengers_id\" = :id)", nativeQuery=true)
	public Ride getPendingRideForPassenger(int id);
	
	@Query(value = "select * from \"rides\" where \"driver_id\" = :id and \"status\" = 1 order by \"start_time\" limit 1", nativeQuery=true)
	public Ride getFirstUpcomingRideForDriver(int id);
	
	@Query(value = "select * from \"rides\" where \"driver_id\" = :driverId and \"status\" = 1 and \"start_time\" < :tomorrow ", nativeQuery=true)
	public List<Ride> getAllScheduledRideForTodayForDriver(int driverId, LocalDateTime tomorrow);
	

	@Query(value = "select * from \"rides\" where \"driver_id\" = :userId or \"id\" in (select \"ride_id\" from \"rides_passengers\" where \"passengers_id\" = :userId order by \"start_time\")", nativeQuery=true)
	public List<Ride> getAllUserRides(int userId, Pageable pageable);

	@Query(value = "select * from \"rides\" where \"status\" = 1 and \"start_time\" < :tomorrow and "
			+ "\"id\" in (select \"ride_id\" from \"rides_passengers\" where \"passengers_id\" = :id)", nativeQuery=true)
	public List<Ride> getAllScheduledRideForTodayForPassenger(int id, LocalDateTime tomorrow);

}
