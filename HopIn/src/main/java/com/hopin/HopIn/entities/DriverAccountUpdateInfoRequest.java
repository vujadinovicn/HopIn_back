package com.hopin.HopIn.entities;

import java.io.UnsupportedEncodingException;

import com.hopin.HopIn.enums.RequestStatus;
import com.hopin.HopIn.enums.RequestType;

import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "driver_account_update_info_requests")
public class DriverAccountUpdateInfoRequest extends DriverAccountUpdateRequest {

	@NotEmpty
	@Pattern(regexp = "^([a-zA-Zčćđžš ]*)$")
	private String name;

	@NotEmpty
	@Pattern(regexp = "^([a-zA-Zčćđžš ]*)$")
	private String surname;
	
	@NotEmpty
	@Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")
	private String email;

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

	public DriverAccountUpdateInfoRequest(int id, RequestStatus status, String reason, Driver driver, Administrator admin, @NotEmpty @Pattern(regexp = "^([a-zA-Zčćđžš ]*)$") String name,
			@NotEmpty @Pattern(regexp = "^([a-zA-Zčćđžš ]*)$") String surname,
			String email,
			@NotEmpty @Pattern(regexp = "^([a-zA-Z0-9 \\s,'-]*)$") String address,
			@NotEmpty @Pattern(regexp = "^[+]*[(]{0,1}[0-9]{1,4}[)]{0,1}[-\\s\\./0-9]*") String telephoneNumber,
			byte[] profilePicture) {
		super(id, status, reason, driver, admin,RequestType.INFO);
		this.name = name;
		this.surname = surname;
		this.email = email;
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
		String s;
		try {
			s = "data:image/jpeg;base64, ";
			s = s + new String(this.profilePicture, "UTF-8");
			return s;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public void setProfilePicture(String profilePicture) {
		String[] picture = profilePicture.split(",");
		if (picture.length >= 2) {
			byte[] decoded;
			try {
				decoded = picture[1].getBytes("UTF-8");
				this.profilePicture = decoded;
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
	}

}
