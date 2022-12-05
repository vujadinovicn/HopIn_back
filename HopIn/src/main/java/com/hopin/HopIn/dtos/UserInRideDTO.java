package com.hopin.HopIn.dtos;

import com.hopin.HopIn.entities.User;
import com.hopin.HopIn.enums.UserType;

public class UserInRideDTO {
	private int id;
	private String email;
	private UserType type;
	
	public UserInRideDTO(User user) {
		this.id = user.getId();
		this.email = user.getEmail();
		this.type = user.getType();
	}
	
	

	public UserInRideDTO(int id, String email, UserType type) {
		super();
		this.id = id;
		this.email = email;
		this.type = type;
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

	public UserType getType() {
		return type;
	}

	public void setType(UserType type) {
		this.type = type;
	}
	
	
}
