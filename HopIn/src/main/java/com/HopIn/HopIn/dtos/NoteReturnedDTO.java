package com.HopIn.HopIn.dtos;

import java.time.LocalDateTime;

public class NoteReturnedDTO {
	
	int id;
	LocalDateTime date;
	String message;
	
	public NoteReturnedDTO(int id, LocalDateTime date, String message) {
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
