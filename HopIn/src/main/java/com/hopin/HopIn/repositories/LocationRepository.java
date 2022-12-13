package com.hopin.HopIn.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hopin.HopIn.entities.Location;

public interface LocationRepository extends JpaRepository<Location, Integer> {

}
