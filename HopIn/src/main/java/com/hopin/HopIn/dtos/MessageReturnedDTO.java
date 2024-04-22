package com.hopin.HopIn.dtos;

import java.time.LocalDateTime;

import com.hopin.HopIn.entities.Message;
import com.hopin.HopIn.enums.MessageType;

public class MessageReturnedDTO {

	int id;
	int inboxId;
	LocalDateTime timeOfSending;
	int senderId;
	int receiverId;
	String message;
	MessageType type;
	int rideId;

	public MessageReturnedDTO(int id, int senderId, int receiverId, LocalDateTime timeOfSending, String message,
			MessageType type, int rideId, int inboxId) {
		super();
		this.id = id;
		this.timeOfSending = timeOfSending;
		this.senderId = senderId;
		this.receiverId = receiverId;
		this.message = message;
		this.type = type;
		this.rideId = rideId;
		this.inboxId = inboxId;
	}

	public MessageReturnedDTO(Message message, int inboxId) {
		super();
		this.id = message.getId();
		this.inboxId = inboxId;
		this.timeOfSending = message.getTimeOfSending();
		this.senderId = message.getSenderId();
		this.receiverId = message.getReceiverId();
		this.message = message.getMessage();
		this.type = message.getType();
		this.rideId = message.getRideId();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDateTime getTimeOfSending() {
		return timeOfSending;
	}

	public void setTimeOfSending(LocalDateTime timeOfSending) {
		this.timeOfSending = timeOfSending;
	}

	public int getSenderId() {
		return senderId;
	}

	public void setSenderId(int senderId) {
		this.senderId = senderId;
	}

	public int getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(int receiverId) {
		this.receiverId = receiverId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public MessageType getType() {
		return type;
	}

	public void setType(MessageType type) {
		this.type = type;
	}

	public int getRideId() {
		return rideId;
	}

	public void setRideId(int rideId) {
		this.rideId = rideId;
	}

	public int getInboxId() {
		return inboxId;
	}

	public void setInboxId(int inboxId) {
		this.inboxId = inboxId;
	}

	@Override
	public String toString() {
		return "MessageReturnedDTO [id=" + id + ", timeOfSending=" + timeOfSending + ", senderId=" + senderId
				+ ", receiverId=" + receiverId + ", message=" + message + ", type=" + type + ", rideId=" + rideId + "]";
	}

}
