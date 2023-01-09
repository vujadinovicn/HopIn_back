package com.hopin.HopIn.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hopin.HopIn.entities.Driver;

public interface DriverRepository extends JpaRepository<Driver, Integer> {

//	@Query(value = "select * from \"drivers\" where \"is_active\" = true")
//	public List<Driver> getActiveDrivers();
	
	public List<Driver> findByIsActive(boolean isActive);
	
}
