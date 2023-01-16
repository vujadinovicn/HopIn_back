package com.hopin.HopIn.dtos;

import org.hibernate.validator.constraints.Length;

import com.hopin.HopIn.entities.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class UserInRideDTO {
	
	@NotNull
	private Integer id;
	
	@NotNull
	@NotEmpty(message="is required")
	@Email(message="format is not valid")
	@Length(max=100)
	private String email;
	
	public UserInRideDTO() {}
	
	public UserInRideDTO(User user) {
		this.id = user.getId();
		this.email = user.getEmail();
	}

	public UserInRideDTO(int id, String email) {
		super();
		this.id = id;
		this.email = email;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}
