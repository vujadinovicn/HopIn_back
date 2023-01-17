package com.hopin.HopIn.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public class ChangePasswordDTO {
	@NotEmpty(message = "is required")
	@Pattern(regexp="^(?=.*\\d)(?=.*[A-Z])(?!.*[^a-zA-Z0-9@#$^+=])(.{8,15})$", message="invalid format")
	private String newPassword;

	@NotEmpty(message = "is required")
	@Pattern(regexp="^(?=.*\\d)(?=.*[A-Z])(?!.*[^a-zA-Z0-9@#$^+=])(.{8,15})$", message="invalid format")
	private String oldPassword;

	public ChangePasswordDTO(@NotEmpty(message = "is required") String newPassword,
			@NotEmpty(message = "is required") String oldPassword) {
		super();
		this.newPassword = newPassword;
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

}
