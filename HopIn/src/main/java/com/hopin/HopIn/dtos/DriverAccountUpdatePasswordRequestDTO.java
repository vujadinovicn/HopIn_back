package com.hopin.HopIn.dtos;

import com.hopin.HopIn.entities.DriverAccountUpdatePasswordRequest;
import com.hopin.HopIn.enums.RequestStatus;

public class DriverAccountUpdatePasswordRequestDTO {
	

	private int id;
	private String oldPassword;
	private String newPassword;
	private RequestStatus status;
	private String reason;
	
	public DriverAccountUpdatePasswordRequestDTO() {
		
	}
	
	public DriverAccountUpdatePasswordRequestDTO(DriverAccountUpdatePasswordRequest request) {
		this.id = request.getId();
		this.oldPassword = request.getOldPassword();
		this.newPassword = request.getNewPassword();
		this.status = request.getStatus();
		this.reason = request.getReason();
	}
	
	public DriverAccountUpdatePasswordRequestDTO(int id, String oldPassword, String newPassword, RequestStatus status) {
		super();
		this.id = id;
		this.oldPassword = oldPassword;
		this.newPassword = newPassword;
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
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
