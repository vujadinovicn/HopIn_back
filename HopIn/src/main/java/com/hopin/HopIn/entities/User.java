package com.hopin.HopIn.entities;

public class User {
	
	private int id;
	private String name;
	private String surName;
	private String email;
	private String password;
	private String address;
	private String phone;
	
	public User() {}

	public User(int id, String name, String surName, String email, String password, String address, String phone) {
		super();
		this.id = id;
		this.name = name;
		this.surName = surName;
		this.email = email;
		this.password = password;
		this.address = address;
		this.phone = phone;
	}
	

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
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
	
	
	

}
