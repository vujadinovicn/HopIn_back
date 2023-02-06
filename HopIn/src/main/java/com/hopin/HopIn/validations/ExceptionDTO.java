package com.hopin.HopIn.validations;

public class ExceptionDTO {
	
	private String message;
	
	public ExceptionDTO() {
		
	}

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
