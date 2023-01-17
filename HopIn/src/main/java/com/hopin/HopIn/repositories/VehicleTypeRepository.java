package com.hopin.HopIn.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hopin.HopIn.entities.VehicleType;
import com.hopin.HopIn.enums.VehicleTypeName;

public interface VehicleTypeRepository extends JpaRepository<VehicleType, Integer> {
	public VehicleType getByName(VehicleTypeName name);
}
