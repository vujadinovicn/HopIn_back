package com.hopin.HopIn.entities;

import java.time.LocalDateTime;

import com.hopin.HopIn.dtos.RideReturnedDTO;
import com.hopin.HopIn.dtos.UserInPanicRideDTO;

public class PanicRide {
	private int id;
	private UserInPanicRideDTO user;
	private RideReturnedDTO ride;
	private LocalDateTime time;
	private String reason;
	
	public PanicRide(int id, UserInPanicRideDTO user, RideReturnedDTO ride, LocalDateTime time,
			String reason) {
		super();
		this.id = id;
		this.user = user;
		this.ride = ride;
		this.time = time;
		this.reason = reason;
	}
	
	public PanicRide(RideReturnedDTO ride, String reason) {
//		same id
		this.id = 1;
		this.ride = ride;
		this.time = LocalDateTime.now();
		this.reason = reason;
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
