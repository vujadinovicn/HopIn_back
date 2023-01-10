package com.hopin.HopIn.dtos;

public class RideInviteDTO {
	private UserReturnedDTO from;
	private RideDTO ride;

	public RideInviteDTO() {
	}

	public RideInviteDTO(UserReturnedDTO from, RideDTO ride) {
		super();
		this.from = from;
		this.ride = ride;
	}

	public UserReturnedDTO getFrom() {
		return from;
	}

	public void setFrom(UserReturnedDTO from) {
		this.from = from;
	}

	public RideDTO getRide() {
		return ride;
	}

	public void setRide(RideDTO ride) {
		this.ride = ride;
	}

}
