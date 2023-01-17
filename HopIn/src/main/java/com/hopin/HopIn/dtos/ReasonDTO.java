package com.hopin.HopIn.dtos;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotEmpty;

public class ReasonDTO {
	
	@NotEmpty(message = "is required")
	@Length(max=500, message = "can not be longer than 500 char!")
	private String reason;
	
	public ReasonDTO() {}
	

	public ReasonDTO(String reason) {
		super();
		this.reason = reason;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	
	
}
