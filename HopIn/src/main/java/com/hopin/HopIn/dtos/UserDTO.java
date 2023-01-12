package com.hopin.HopIn.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public class UserDTO {
	@NotEmpty(message="is required")
	@Pattern(regexp = "^([a-zA-Zčćđžš ]*)$", message="format is not valid")
	private String name;
	
	@NotEmpty(message="is required")
	@Pattern(regexp = "^([a-zA-Zčćđžš ]*)$", message="format is not valid")
	private String surname;
	
	@NotEmpty(message="is required")
	@Email(message="format is not valid")
	private String email;
	
	@NotEmpty(message="is required")
	@Pattern(regexp="^([0-9a-zA-Z]{3,}$)", message="format is not valid")
	private String password;
	
	@NotEmpty(message="is required")
	@Pattern(regexp = "^([a-zA-Z0-9 \\s,'-]*)$", message="format is not valid")
	private String address;
	
	@NotEmpty(message="is required")
	@Pattern(regexp = "^[+]*[(]{0,1}[0-9]{1,4}[)]{0,1}[-\\s\\./0-9]*", message="format is not valid")
	private String telephoneNumber;
	
	@NotEmpty(message="is required")
	private String profilePicture;

	public UserDTO(String name, String surname, String email, String password, String address, String telephoneNumber,
			String profilePicture) {
		super();
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.password = password;
		this.address = address;
		this.telephoneNumber = telephoneNumber;
		this.profilePicture = profilePicture;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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
