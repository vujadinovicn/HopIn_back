package com.hopin.HopIn.entities;

import java.util.ArrayList;
import java.util.List;

import com.hopin.HopIn.enums.MessageType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "inboxes")
public class Inbox {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private MessageType type;
	
	private int rideId;
	
	@ManyToOne(cascade = { CascadeType.ALL })
	private User firstUser;
	
	@ManyToOne(cascade = { CascadeType.ALL })
	private User secondUser;

	@OneToMany(cascade = { CascadeType.ALL })
	private List<Message> messages = new ArrayList<Message>();

	public Inbox() {
	}

	public Inbox(int id, User firstUser, User secondUser, List<Message> messages) {
		super();
		this.id = id;
		this.firstUser = firstUser;
		this.secondUser = secondUser;
		this.messages = messages;
	}
	
	
	
	public Inbox(int id, MessageType type, User firstUser, User secondUser, List<Message> messages) {
		super();
		this.id = id;
		this.type = type;
		this.firstUser = firstUser;
		this.secondUser = secondUser;
		this.messages = messages;
	}

	public Inbox(User firstUser, User secondUser, MessageType type, int rideId) {
		this.firstUser = firstUser;
		this.secondUser = secondUser;
		this.type = type;
		this.rideId = rideId;
		this.messages = new ArrayList<Message>();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getFirstUser() {
		return firstUser;
	}

	public void setFirstUser(User firstUser) {
		this.firstUser = firstUser;
	}

	public User getSecondUser() {
		return secondUser;
	}

	public void setSecondUser(User secondUser) {
		this.secondUser = secondUser;
	}

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
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
