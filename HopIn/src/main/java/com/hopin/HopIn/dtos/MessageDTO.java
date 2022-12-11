package com.hopin.HopIn.dtos;

import com.hopin.HopIn.enums.MessageType;

public class MessageDTO {
	
	int receiverId;
	String message;
	MessageType type;
	int rideId;
	
	public MessageDTO(int receiverId, String message, MessageType type, int rideId) {
		super();
		this.receiverId = receiverId;
		this.message = message;
		this.type = type;
		this.rideId = rideId;
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
