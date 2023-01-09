package com.hopin.HopIn.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hopin.HopIn.entities.WorkingHours;

public interface WorkingHoursRepository extends JpaRepository<WorkingHours, Integer> {
	
	@Query(value = "select * from \"working_hours\" where \"driver_id\" = :id and cast(\"start\" as date) = :date", nativeQuery=true)
	public List<WorkingHours> findByDriverAndStart(int id, LocalDate date);
}
