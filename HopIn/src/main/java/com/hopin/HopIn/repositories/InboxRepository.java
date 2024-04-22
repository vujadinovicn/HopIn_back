package com.hopin.HopIn.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hopin.HopIn.entities.Inbox;
import com.hopin.HopIn.entities.Message;
import com.hopin.HopIn.enums.MessageType;

public interface InboxRepository extends JpaRepository<Inbox, Integer> {
	
	@Query("select i from Inbox i where (i.firstUser.id = ?1 and i.secondUser.id = ?2) or (i.firstUser.id = ?2 and i.secondUser.id = ?1)")
	public List<Inbox> findAllInboxesByIds(int firstId, int secondId);
	
	@Query("select i from Inbox i where i.firstUser.id = ?1 or i.secondUser.id = ?1")
	public List<Inbox> findAllInboxesByUserId(int id);

	@Query("select i from Inbox i where i.type = 1 and (i.firstUser.id = ?1 or i.secondUser.id = ?1)")
	public Inbox getSupportInbox(int id);

	public List<Inbox> findAllInboxesByType(MessageType support);
	
	
}
