package com.hopin.HopIn.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hopin.HopIn.entities.FavoriteRide;

@Repository
public interface FavoriteRideRepository extends JpaRepository<FavoriteRide, Integer>{

}
