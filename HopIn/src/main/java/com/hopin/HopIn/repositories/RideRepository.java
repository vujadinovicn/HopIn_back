package com.hopin.HopIn.repositories;

import java.awt.print.Pageable;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hopin.HopIn.entities.Ride;

public interface RideRepository extends JpaRepository<Ride, Integer> {
	
	public List<Ride> findAllStartTimeBetween(LocalDateTime from, LocalDateTime to, Pageable pageable);
}
