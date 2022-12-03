package com.hopin.HopIn.dtos;

import com.hopin.HopIn.entities.User;

public class UserDto {
	private int id;
	private String name;
	private String surName;
	private String email;
	private String address;
	private String phone;
	
	public UserDto() {}

	public UserDto(int id, String name, String surName, String email, String address, String phone) {
		super();
		this.id = id;
		this.name = name;
		this.surName = surName;
		this.email = email;
		this.address = address;
		this.phone = phone;
	}
	
	public UserDto(User user) {
		super();
		this.id = user.getId();
		this.name = user.getName();
		this.surName = user.getSurName();
		this.email = user.getEmail();
		this.address = user.getAddress();
		this.phone = user.getPhone();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurName() {
		return surName;
	}

	public void setSurName(String surName) {
		this.surName = surName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	
}
