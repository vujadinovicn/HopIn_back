package com.hopin.HopIn.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.hopin.HopIn.entities.Driver;
import com.hopin.HopIn.entities.Note;
import com.hopin.HopIn.entities.Ride;

public interface DriverRepository extends JpaRepository<Driver, Integer>, PagingAndSortingRepository<Driver, Integer> {

//	@Query(value = "select * from \"drivers\" where \"is_active\" = true")
//	public List<Driver> getActiveDrivers();
	
	public List<Driver> findByIsActive(boolean isActive);

	@Query(value = "select * from \"drivers\"", nativeQuery=true)
	public List<Driver> getAll(Pageable pageable);
	
}
