package com.hopin.HopIn.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hopin.HopIn.entities.Inbox;
import com.hopin.HopIn.entities.Message;

public interface InboxRepository extends JpaRepository<Inbox, Integer> {
	
	@Query("select i from Inbox i where i.firstUser = ?1 and i.secondUser = ?2")
	public Inbox findAllInboxesByIds(int firstId, int secondId);
	
	@Query("select i from Inbox i where i.firstUser = ?1 or i.secondUser = ?1")
	public List<Inbox> findAllInboxesByUserId(int id);
	
	
}
