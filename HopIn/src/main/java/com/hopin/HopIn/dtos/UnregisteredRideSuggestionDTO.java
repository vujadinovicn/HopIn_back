package com.hopin.HopIn.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class UnregisteredRideSuggestionDTO {
	
	@NotNull
	@NotEmpty(message = "is required")
	private String vehicleTypeName;
	private double distance;
	
	public UnregisteredRideSuggestionDTO() {}

	public UnregisteredRideSuggestionDTO(String vehicleTypeName, double distance) {
		super();
		this.vehicleTypeName = vehicleTypeName;
		this.distance = distance;
	}

	public String getVehicleTypeName() {
		return vehicleTypeName;
	}

	public void setVehicleTypeName(String vehicleTypeName) {
		this.vehicleTypeName = vehicleTypeName;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

}
