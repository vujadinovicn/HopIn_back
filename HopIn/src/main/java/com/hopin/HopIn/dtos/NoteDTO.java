package com.HopIn.HopIn.dtos;

public class NoteDTO {
	
	String message;
	
	public NoteDTO() {}

	public NoteDTO(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
