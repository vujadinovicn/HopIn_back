package com.hopin.HopIn.dtos;

public class DriverSentPasswordRequestDTO {
	private int driverId;
	private PasswordRequestDTO request;

	public DriverSentPasswordRequestDTO() {
	}

	public DriverSentPasswordRequestDTO(int driverId, PasswordRequestDTO request) {
		super();
		this.driverId = driverId;
		this.request = request;
	}
	
	public int getDriverId() {
		return driverId;
	}

	public void setDriverId(int driverId) {
		this.driverId = driverId;
	}

	public PasswordRequestDTO getRequest() {
		return request;
	}

	public void setRequest(PasswordRequestDTO request) {
		this.request = request;
	}

}
