package com.hopin.HopIn.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class CredentialsDTO {
	
	@NotNull
	@NotEmpty(message="is required")	
	String email;
	@NotNull
	@NotEmpty(message="is required")
	String password;
	
	public CredentialsDTO(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
