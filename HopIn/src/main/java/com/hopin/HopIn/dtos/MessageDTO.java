package com.hopin.HopIn.dtos;

import com.hopin.HopIn.enums.MessageType;

import jakarta.validation.constraints.NotEmpty;

public class MessageDTO {
	@NotEmpty(message="is required")
	String message;
	
	@NotEmpty(message="is required")
	MessageType type;
	
	int rideId;

	public MessageDTO(String message, MessageType type, int rideId) {
		super();
		this.message = message;
		this.type = type;
		this.rideId = rideId;
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
