package com.hopin.HopIn.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hopin.HopIn.entities.Vehicle;

public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {

}
