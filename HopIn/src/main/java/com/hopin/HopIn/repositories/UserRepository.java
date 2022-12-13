package com.hopin.HopIn.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hopin.HopIn.entities.User;

public interface UserRepository extends JpaRepository<User, Integer>{

}
