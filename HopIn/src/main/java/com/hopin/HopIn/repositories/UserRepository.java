package com.hopin.HopIn.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hopin.HopIn.entities.User;

public interface UserRepository extends JpaRepository<User, Integer>{
	
	public Optional<User> findByEmail(String email);

}
