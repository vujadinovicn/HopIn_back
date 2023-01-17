package com.hopin.HopIn.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hopin.HopIn.entities.Location;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import jakarta.validation.constraints.Pattern;



public class LocationNoIdDTO {
	@NotNull
	@NotEmpty(message="is required")
	@Pattern(regexp = "^([a-zA-Z0-9 \\s,'-]*)$", message="format is not valid")
	private String address;
	

	@NotNull(message="is required")
	@Max(value=90, message="value is max 90")
	@Min(value=-90, message="value is min -90")
	private Double latitude;
	
	@NotNull(message="is required")
	@Max(value=180, message="value is max 180")
	@Min(value=-180, message="value is min -180")
	private Double longitude;
	
	public LocationNoIdDTO() {}

	public LocationNoIdDTO(String address, Double latitude, Double longitude) {
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

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	
	@JsonIgnore
	public String getLatitudeString() {
		return Double.toString(latitude);
	}
	@JsonIgnore
	public String getLongitudeString() {
		return Double.toString(longitude);
	}

	@Override
	public String toString() {
		return "LocationNoIdDTO [address=" + address + ", latitude=" + latitude + ", longitude=" + longitude + "]";
	}
	
	
	
}
