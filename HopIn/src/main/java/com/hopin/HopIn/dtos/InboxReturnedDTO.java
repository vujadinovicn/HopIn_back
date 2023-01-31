package com.hopin.HopIn.dtos;

import java.util.ArrayList;
import java.util.List;

import com.hopin.HopIn.entities.Inbox;
import com.hopin.HopIn.entities.Message;

public class InboxReturnedDTO {

	private int id;
	private UserReturnedDTO firstUser;
	private UserReturnedDTO secondUser;
	List<MessageReturnedDTO> messages = new ArrayList<MessageReturnedDTO>();

	public InboxReturnedDTO(int id, UserReturnedDTO firstUser, UserReturnedDTO secondUser, List<MessageReturnedDTO> messages) {
		super();
		this.id = id;
		this.firstUser = firstUser;
		this.secondUser = secondUser;
		this.messages = messages;
	}
	
	public InboxReturnedDTO(Inbox inbox) {
		this.id = inbox.getId();
		this.firstUser = new UserReturnedDTO(inbox.getFirstUser());
		this.secondUser = new UserReturnedDTO(inbox.getSecondUser());
		
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

	public UserReturnedDTO getFirstUser() {
		return firstUser;
	}

	public void setFirstUser(UserReturnedDTO firstUser) {
		this.firstUser = firstUser;
	}

	public UserReturnedDTO getSecondUser() {
		return secondUser;
	}

	public void setSecondUser(UserReturnedDTO secondUser) {
		this.secondUser = secondUser;
	}

	public List<MessageReturnedDTO> getMessages() {
		return messages;
	}

	public void setMessages(List<MessageReturnedDTO> messages) {
		this.messages = messages;
	}

}
