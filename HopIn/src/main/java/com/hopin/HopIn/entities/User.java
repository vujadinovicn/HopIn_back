package com.HopIn.HopIn.entities;

import com.HopIn.HopIn.dtos.UserDTO;
import com.HopIn.HopIn.enums.UserType;

public class User {
	
	private int id;
	private String name;
	private String surname;
	private String email;
	private String password;
	private String address;
	private String telephoneNumber;
	private String profilePicture;
	private boolean isActivated;
	private UserType type;
	
	public User() {}

	public User(int id, String name, String surname, String email, String password, String address, String telephoneNumber,
			String profilePicture) {
		super();
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.password = password;
		this.address = address;
		this.telephoneNumber = telephoneNumber;
		this.profilePicture = profilePicture;
		this.isActivated = false;
	}
	
	public User(UserDTO dto) {
		super();
		this.name = dto.getName();
		this.surname = dto.getSurname();
		this.email = dto.getEmail();
		this.password = dto.getPassword();
		this.address = dto.getAddress();
		this.telephoneNumber = dto.getTelephoneNumber();
		this.profilePicture = dto.getProfilePicture();
		this.isActivated = false;
	}
	
	public void copy(UserDTO dto) {
		this.name = dto.getName();
		this.surname = dto.getSurname();
		this.email = dto.getEmail();
		this.password = dto.getPassword();
		this.address = dto.getAddress();
		this.telephoneNumber = dto.getTelephoneNumber();
		this.profilePicture = dto.getProfilePicture();
	}


	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
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

	public String getProfilePicture() {
		return profilePicture;
	}

	public void setProfilePicture(String profilePicture) {
		this.profilePicture = profilePicture;
	}

	public String getTelephoneNumber() {
		return telephoneNumber;
	}

	public void setTelephoneNumber(String telephoneNumber) {
		this.telephoneNumber = telephoneNumber;
	}

	public boolean isActivated() {
		return isActivated;
	}

	public void setActivated(boolean isActivated) {
		this.isActivated = isActivated;
	}

	public UserType getType() {
		return type;
	}

	public void setType(UserType type) {
		this.type = type;
	}
	
}
