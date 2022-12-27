package com.hopin.HopIn.dtos;


import com.hopin.HopIn.entities.DriverAccountUpdateInfoRequest;
import com.hopin.HopIn.enums.RequestStatus;

public class DriverAccountUpdateInfoRequestDTO {
	
	private int id;
	private String name;
	private String surname;
	private String email;
	private String address;
	private String telephoneNumber;
	private String profilePicture;
	private RequestStatus status;
	private String reason;
	
	public DriverAccountUpdateInfoRequestDTO() {
		
	}
	
	public DriverAccountUpdateInfoRequestDTO(DriverAccountUpdateInfoRequest request) {
		this.id = request.getId();
		this.name = request.getName();
		this.surname = request.getSurname();
		this.email = request.getEmail();
		this.address = request.getAddress();
		this.telephoneNumber = request.getTelephoneNumber();
		this.profilePicture = request.getProfilePicture();
		this.status = request.getStatus();
		this.reason = request.getReason();
	}
	
	public DriverAccountUpdateInfoRequestDTO(int id, String name, String surname, String email, String address, String telephoneNumber, String profilePicture, RequestStatus status) {
		super();
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.address = address;
		this.telephoneNumber = telephoneNumber;
		this.profilePicture = profilePicture;
		this.status = status;
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
	
	public RequestStatus getStatus() {
		return status;
	}
	
	public void setStatus(RequestStatus status) {
		this.status = status;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	
	

}
