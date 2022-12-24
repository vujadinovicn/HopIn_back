package com.hopin.HopIn.dtos;

import com.hopin.HopIn.entities.VehicleType;
import com.hopin.HopIn.enums.VehicleTypeName;

public class UnregisteredRideSuggestionDTO {

	private String vehicleTypeName;
	private double distance;

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
