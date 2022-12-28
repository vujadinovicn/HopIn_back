package com.hopin.HopIn.entities;

import com.hopin.HopIn.enums.RequestStatus;
import com.hopin.HopIn.enums.RequestType;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "driver_account_update_password_requests")
public class DriverAccountUpdatePasswordRequest extends DriverAccountUpdateRequest {
	
	@NotEmpty 
	@Pattern(regexp = "^([0-9a-zA-Z]{6,}$)")
	private String oldPassword;

	@NotEmpty
	@Pattern(regexp = "^([0-9a-zA-Z]{6,}$)")
	private String newPassword;

	public DriverAccountUpdatePasswordRequest() {
	}

//	public DriverAccountUpdatePasswordRequest(int id, String oldPassword, 
//			@NotEmpty @Pattern(regexp = "^([0-9a-zA-Z]{6,}$)") String newPassword) {
//		super();
//		this.id = id;
//		this.oldPassword = oldPassword;
//		this.newPassword = newPassword;
//	}
	
	public DriverAccountUpdatePasswordRequest(int id, RequestStatus status, String reason, Driver driver, Administrator admin,
			String oldPassword, 
			@NotEmpty @Pattern(regexp = "^([0-9a-zA-Z]{6,}$)") String newPassword) {
		super(id, status, reason, driver, admin, RequestType.PASSWORD);
		this.oldPassword = oldPassword;
		this.newPassword = newPassword;
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

}
