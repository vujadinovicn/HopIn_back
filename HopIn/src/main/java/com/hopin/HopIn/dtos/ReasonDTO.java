package com.hopin.HopIn.dtos;

import jakarta.validation.constraints.NotEmpty;

public class ReasonDTO {
	
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
