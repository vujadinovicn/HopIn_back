package com.hopin.HopIn.dtos;

import com.hopin.HopIn.entities.User;
import com.hopin.HopIn.entities.Vehicle;

public class UserReturnedDTO {
	private int id;
	private String name;
	private String surname;
	private String profilePicture;
	private String telephoneNumber;
	private String email;
	private String address;
	private String password;
	private boolean isBlocked;
	
	public UserReturnedDTO() {}

	public UserReturnedDTO(int id, String name, String surname, String profilePicture, String telephoneNumber,
			String email, String address, String password, boolean isBlocked) {
		super();
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.profilePicture = profilePicture;
		this.telephoneNumber = telephoneNumber;
		this.email = email;
		this.address = address;
		this.password = password;
		this.setBlocked(isBlocked);
	}

	public UserReturnedDTO(User user) {
		super();
		this.id = user.getId();
		this.name = user.getName();
		this.surname = user.getSurname();
		this.email = user.getEmail();
		this.address = user.getAddress();
		this.telephoneNumber = user.getTelephoneNumber();
		this.profilePicture = user.getProfilePicture();
		this.password = user.getPassword();
		this.isBlocked = user.isBlocked();
	}

	public UserReturnedDTO(User user, Vehicle vehicle) {
		super();
		this.id = user.getId();
		this.name = user.getName();
		this.surname = user.getSurname();
		this.email = user.getEmail();
		this.address = user.getAddress();
		this.telephoneNumber = user.getTelephoneNumber();
		this.profilePicture = user.getProfilePicture();
		this.password = null;
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

	public String getTelephoneNumber() {
		return telephoneNumber;
	}

	public void setTelephoneNumber(String telephoneNumber) {
		this.telephoneNumber = telephoneNumber;
	}

	public String getProfilePicture() {
		return profilePicture;
	}

	public void setProfilePicture(String profilePicture) {
		this.profilePicture = profilePicture;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isBlocked() {
		return isBlocked;
	}

	public void setBlocked(boolean isBlocked) {
		this.isBlocked = isBlocked;
	}
}
