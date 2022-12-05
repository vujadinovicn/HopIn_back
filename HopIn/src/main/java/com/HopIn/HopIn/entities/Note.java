package com.HopIn.HopIn.entities;

import java.time.LocalDateTime;

public class Note {
	
	int id;
	LocalDateTime date;
	String message;
	
	public Note(int id, LocalDateTime date, String message) {
		super();
		this.id = id;
		this.date = date;
		this.message = message;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
