package com.hopin.HopIn.dtos;

import java.util.ArrayList;
import java.util.List;

import com.hopin.HopIn.entities.Inbox;
import com.hopin.HopIn.entities.Message;

public class InboxReturnedDTO {

	private int id;
	private int firstUser;
	private int secondUser;
	List<MessageReturnedDTO> messages = new ArrayList<MessageReturnedDTO>();

	public InboxReturnedDTO(int id, int firstUser, int secondUser, List<MessageReturnedDTO> messages) {
		super();
		this.id = id;
		this.firstUser = firstUser;
		this.secondUser = secondUser;
		this.messages = messages;
	}
	
	public InboxReturnedDTO(Inbox inbox) {
		this.id = inbox.getId();
		this.firstUser = inbox.getFirstUser();
		this.secondUser = inbox.getSecondUser();
		
		for(Message message : inbox.getMessages()) {
			this.messages.add(new MessageReturnedDTO(message));
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getFirstUser() {
		return firstUser;
	}

	public void setFirstUser(int firstUser) {
		this.firstUser = firstUser;
	}

	public int getSecondUser() {
		return secondUser;
	}

	public void setSecondUser(int secondUser) {
		this.secondUser = secondUser;
	}

	public List<MessageReturnedDTO> getMessages() {
		return messages;
	}

	public void setMessages(List<MessageReturnedDTO> messages) {
		this.messages = messages;
	}

}
