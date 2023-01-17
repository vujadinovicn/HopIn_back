package com.hopin.HopIn.repositories;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hopin.HopIn.entities.WorkingHours;

public interface WorkingHoursRepository extends JpaRepository<WorkingHours, Integer> {
	
	@Query(value = "select * from \"working_hours\" where \"driver_id\" = :id and \"end\" between :start and :end ", nativeQuery=true)
	public List<WorkingHours> findByDriverAndDates(int id, LocalDateTime start, LocalDate end);
}
