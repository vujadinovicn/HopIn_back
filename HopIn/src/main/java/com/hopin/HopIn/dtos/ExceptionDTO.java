package com.hopin.HopIn.dtos;

public class ExceptionDTO {
	
	private String message;

	public ExceptionDTO(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	

}
