package com.hopin.HopIn.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "driver_account_update_info_requests")
public class DriverAccountUpdateInfoRequest extends DriverAccountUpdateRequest {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@NotEmpty
	@Pattern(regexp = "^([a-zA-Zčćđžš ]*)$")
	private String name;

	@NotEmpty
	@Pattern(regexp = "^([a-zA-Zčćđžš ]*)$")
	private String surname;

	@NotEmpty
	@Pattern(regexp = "^([a-zA-Z0-9 \\s,'-]*)$")
	private String address;

	@NotEmpty
	@Pattern(regexp = "^[+]*[(]{0,1}[0-9]{1,4}[)]{0,1}[-\\s\\./0-9]*")
	private String telephoneNumber;

	@Lob
	private byte[] profilePicture;

	public DriverAccountUpdateInfoRequest() {
	}

	public DriverAccountUpdateInfoRequest(int id, @NotEmpty @Pattern(regexp = "^([a-zA-Zčćđžš ]*)$") String name,
			@NotEmpty @Pattern(regexp = "^([a-zA-Zčćđžš ]*)$") String surname,
			@NotEmpty @Pattern(regexp = "^([a-zA-Z0-9 \\s,'-]*)$") String address,
			@NotEmpty @Pattern(regexp = "^[+]*[(]{0,1}[0-9]{1,4}[)]{0,1}[-\\s\\./0-9]*") String telephoneNumber,
			byte[] profilePicture) {
		super();
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.address = address;
		this.telephoneNumber = telephoneNumber;
		this.profilePicture = profilePicture;
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

	public byte[] getProfilePicture() {
		return profilePicture;
	}

	public void setProfilePicture(byte[] profilePicture) {
		this.profilePicture = profilePicture;
	}

}
