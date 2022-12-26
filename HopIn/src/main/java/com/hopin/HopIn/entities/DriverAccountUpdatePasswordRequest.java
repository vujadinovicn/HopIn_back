package com.hopin.HopIn.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "driver_account_update_password_requests")
public class DriverAccountUpdatePasswordRequest extends DriverAccountUpdateRequest {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@NotEmpty
	@Pattern(regexp = "^([0-9a-zA-Z]{6,}$)")
	private String newPassword;

	public DriverAccountUpdatePasswordRequest() {
	}

	public DriverAccountUpdatePasswordRequest(int id,
			@NotEmpty @Pattern(regexp = "^([0-9a-zA-Z]{6,}$)") String newPassword) {
		super();
		this.id = id;
		this.newPassword = newPassword;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

}
