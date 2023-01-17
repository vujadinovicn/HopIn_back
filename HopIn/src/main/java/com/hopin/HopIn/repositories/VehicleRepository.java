package com.hopin.HopIn.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hopin.HopIn.entities.Vehicle;

public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {

	Optional<Vehicle> findByDriverId(int driverId);

}
