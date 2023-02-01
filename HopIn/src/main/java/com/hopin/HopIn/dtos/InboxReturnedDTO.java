package com.hopin.HopIn.dtos;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.hopin.HopIn.entities.Inbox;
import com.hopin.HopIn.entities.Message;
import com.hopin.HopIn.enums.MessageType;

public class InboxReturnedDTO {

	private int id;
	private UserReturnedDTO firstUser;
	private UserReturnedDTO secondUser;
	private LocalDateTime lastMessage;
	private MessageType type;
	List<MessageReturnedDTO> messages = new ArrayList<MessageReturnedDTO>();

	public InboxReturnedDTO(int id, UserReturnedDTO firstUser, UserReturnedDTO secondUser, LocalDateTime lastMessage,
			List<MessageReturnedDTO> messages) {
		super();
		this.id = id;
		this.firstUser = firstUser;
		this.secondUser = secondUser;
		this.lastMessage = lastMessage;
		this.messages = messages;
	}

	public InboxReturnedDTO(int id, UserReturnedDTO firstUser, UserReturnedDTO secondUser, LocalDateTime lastMessage,
			MessageType type, List<MessageReturnedDTO> messages) {
		super();
		this.id = id;
		this.firstUser = firstUser;
		this.secondUser = secondUser;
		this.lastMessage = lastMessage;
		this.type = type;
		this.messages = messages;
	}

	public InboxReturnedDTO(Inbox inbox) {
		this.id = inbox.getId();
		this.firstUser = new UserReturnedDTO(inbox.getFirstUser());
		this.secondUser = new UserReturnedDTO(inbox.getSecondUser());
		for (Message message : inbox.getMessages()) {
			this.messages.add(new MessageReturnedDTO(message, inbox.getId()));
		}

		Collections.sort(this.messages, new Comparator<MessageReturnedDTO>() {

			public int compare(MessageReturnedDTO i1, MessageReturnedDTO i2) {
				return i2.getTimeOfSending().compareTo(i1.getTimeOfSending());
			}
		});
		if (this.messages.size() > 0)
			this.lastMessage = this.messages.get(this.messages.size() - 1).getTimeOfSending();
		this.lastMessage = null;
		this.type = inbox.getType();
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

	public LocalDateTime getLastMessage() {
		return lastMessage;
	}

	public void setLastMessage(LocalDateTime lastMessage) {
		this.lastMessage = lastMessage;
	}

	public MessageType getType() {
		return type;
	}

	public void setType(MessageType type) {
		this.type = type;
	}

}
