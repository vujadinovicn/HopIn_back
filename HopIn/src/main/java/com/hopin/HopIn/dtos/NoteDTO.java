package com.hopin.HopIn.dtos;

import jakarta.validation.constraints.NotEmpty;

public class NoteDTO {
	
	@NotEmpty(message = "is required")
	//@Length max 200
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
