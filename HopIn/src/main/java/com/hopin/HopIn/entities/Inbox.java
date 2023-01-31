package com.hopin.HopIn.entities;

import java.util.HashSet;
import java.util.Set;

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
	
	@ManyToOne(cascade = { CascadeType.ALL })
	private User firstUser;
	
	@ManyToOne(cascade = { CascadeType.ALL })
	private User secondUser;

	@OneToMany(cascade = { CascadeType.ALL })
	private Set<Message> messages = new HashSet<Message>();

	public Inbox() {
	}

	public Inbox(int id, User firstUser, User secondUser, Set<Message> messages) {
		super();
		this.id = id;
		this.firstUser = firstUser;
		this.secondUser = secondUser;
		this.messages = messages;
	}
	
	public Inbox(User firstUser, User secondUser) {
		this.firstUser = firstUser;
		this.secondUser = secondUser;
		this.messages = new HashSet<Message>();
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

	public Set<Message> getMessages() {
		return messages;
	}

	public void setMessages(Set<Message> messages) {
		this.messages = messages;
	}

}
