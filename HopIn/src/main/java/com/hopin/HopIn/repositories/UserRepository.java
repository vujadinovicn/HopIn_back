package com.hopin.HopIn.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.hopin.HopIn.entities.Ride;
import com.hopin.HopIn.entities.User;

public interface UserRepository extends JpaRepository<User, Integer>, PagingAndSortingRepository<User, Integer>{
	
	public Optional<User> findByEmail(String email);

	public Optional<User> getUserByEmail(String email);

	@Query("select a from User a where a.role = 2")
	public List<User> getAnyAdmin();

}
