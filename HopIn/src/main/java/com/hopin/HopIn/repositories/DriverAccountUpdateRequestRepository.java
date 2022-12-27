package com.hopin.HopIn.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hopin.HopIn.dtos.DriverAccountUpdateRequestDTO;
import com.hopin.HopIn.entities.DriverAccountUpdateRequest;

public interface DriverAccountUpdateRequestRepository extends JpaRepository<DriverAccountUpdateRequest, Integer>{
	
	@Query("select r from DriverAccountUpdateRequest r where r.status = 1")
	public List<DriverAccountUpdateRequest> findAllPending();
	
	@Query("select r from DriverAccountUpdateRequest r where r.status != 1")
	public List<DriverAccountUpdateRequest> findAllProcessed();

}
