package com.hopin.HopIn.dtos;

import com.hopin.HopIn.entities.User;

public class UserInPanicRideDTO {
	private String name;
	private String surname;
	private String profilePicture;
	private String telephoneNumber;
	private String email;
	private String address;
	
	public UserInPanicRideDTO(String name, String surname, String email, String address, String telephoneNumber,
			String profilePicture) {
		super();
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.address = address;
		this.telephoneNumber = telephoneNumber;
		this.profilePicture = profilePicture;
	}

	public UserInPanicRideDTO(User user) {
		super();
		this.name = user.getName();
		this.surname = user.getSurname();
		this.email = user.getEmail();
		this.address = user.getAddress();
		this.telephoneNumber = user.getTelephoneNumber();
		this.profilePicture = user.getProfilePicture();
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
	
}
