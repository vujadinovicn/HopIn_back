package com.hopin.HopIn.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hopin.HopIn.entities.Message;

public interface MessageRepository extends JpaRepository<Message, Integer>{

}
