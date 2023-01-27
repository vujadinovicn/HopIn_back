package com.hopin.HopIn.dtos;

public class PanicDTO {
	private int userId;
	private int rideId;
	private String reason;

	public PanicDTO(int userId, int rideId, String reason) {
		super();
		this.userId = userId;
		this.rideId = rideId;
		this.reason = reason;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getRideId() {
		return rideId;
	}

	public void setRideId(int rideId) {
		this.rideId = rideId;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

}
