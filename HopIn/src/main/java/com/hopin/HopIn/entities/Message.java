package com.hopin.HopIn.entities;

import java.time.LocalDateTime;

import com.hopin.HopIn.dtos.MessageDTO;
import com.hopin.HopIn.enums.MessageType;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="messages")
public class Message {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private LocalDateTime timeOfSending;
	private int senderId;
	private int receiverId;
	private String message;
	private MessageType type;
	private int rideId;
	
	public Message() {}
	
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
	
	public Message(int senderId, int receiverId, MessageDTO dto) {
		this.senderId = senderId;
		this.receiverId = receiverId;
		this.message = dto.getMessage();
		this.type = dto.getType();
		this.rideId = dto.getRideId();
		this.timeOfSending = LocalDateTime.now();
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
