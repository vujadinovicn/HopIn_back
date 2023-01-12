package com.hopin.HopIn.dtos;

import com.hopin.HopIn.entities.Location;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public class LocationNoIdDTO {
	
	@NotEmpty(message="is required")
	@Pattern(regexp = "^([a-zA-Z0-9 \\s,'-]*)$", message="format is not valid")
	private String address;
	
	private double latitude;
	
	private double longitude;
	
	public LocationNoIdDTO() {}

	public LocationNoIdDTO(String address, double latitude, double longitude) {
		super();
		this.address = address;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	public LocationNoIdDTO(Location location) {
		super();
		this.address = location.getAddress();
		this.latitude = location.getLatitude();
		this.longitude = location.getLongitude();
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
}
