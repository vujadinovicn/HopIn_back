package com.hopin.HopIn.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hopin.HopIn.entities.Message;

public interface MessageRepository extends JpaRepository<Message, Integer>{
	
	@Query("select m from Message m where m.receiverId = ?1 or m.senderId = ?1")
	public List<Message> findAllMessagesById(int id);
}
