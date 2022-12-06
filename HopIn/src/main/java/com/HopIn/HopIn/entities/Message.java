package com.HopIn.HopIn.entities;

import java.time.LocalDateTime;

import com.HopIn.HopIn.enums.MessageType;

public class Message {

	int id;
	LocalDateTime timeOfSending;
	int senderId;
	int receiverId;
	String message;
	MessageType type;
	int rideId;
	
	public Message(int id, int senderId, int receiverId, LocalDateTime timeOfSending, String message,
			MessageType type, int rideId) {
		super();
		this.id = id;
		this.timeOfSending = timeOfSending;
		this.senderId = senderId;
		this.receiverId = receiverId;
		this.message = message;
		this.type = type;
		this.rideId = rideId;
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
}
