package com.hopin.HopIn.dtos;

import org.hibernate.validator.constraints.Length;

import com.hopin.HopIn.enums.MessageType;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class MessageDTO {
	@NotNull
	@NotEmpty(message="is required")
	@Length(min=1, max=500)
	String message;
	
	@NotNull
	@NotEmpty(message="is required")
	MessageType type;
	
	@NotNull
	@Min(value=1)
	Integer rideId;

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
