package com.hopin.HopIn.dtos;

import java.time.LocalDateTime;

import com.hopin.HopIn.entities.Panic;

public class PanicRideDTO {
	private int id;
	private UserInPanicRideDTO user;
	private RideReturnedDTO ride;
	private LocalDateTime time;
	private String reason;
	
	public PanicRideDTO(int id, UserInPanicRideDTO user, RideReturnedDTO ride, LocalDateTime time,
			String reason) {
		super();
		this.id = id;
		this.user = user;
		this.ride = ride;
		this.time = time;
		this.reason = reason;
	}
	
	public PanicRideDTO(RideReturnedDTO ride, String reason) {
//		same id
		this.id = 1;
		this.ride = ride;
		this.time = LocalDateTime.now();
		this.reason = reason;
	}
	
	public PanicRideDTO(Panic panic) {
		this.id = panic.getId();
		this.ride = new RideReturnedDTO(panic.getRide());
		this.time = panic.getTime();
		this.reason = panic.getReason();
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public UserInPanicRideDTO getUser() {
		return user;
	}
	public void setUser(UserInPanicRideDTO user) {
		this.user = user;
	}
	public RideReturnedDTO getRide() {
		return ride;
	}
	public void setRide(RideReturnedDTO ride) {
		this.ride = ride;
	}
	public LocalDateTime getTime() {
		return time;
	}
	public void setTime(LocalDateTime time) {
		this.time = time;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	
	
}
