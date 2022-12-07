package com.hopin.HopIn.dtos;

import java.util.List;

import com.hopin.HopIn.enums.VehicleType;

public class UnregisteredUserRideDTO {
	List<LocationNoIdDTO> locations;
	VehicleType vehicleType;
	boolean babyTransport;
	boolean petTransport;
	
	public UnregisteredUserRideDTO(List<LocationNoIdDTO> locations, VehicleType vehicleType, boolean babyTransport,
			boolean petTransport) {
		super();
		this.locations = locations;
		this.vehicleType = vehicleType;
		this.babyTransport = babyTransport;
		this.petTransport = petTransport;
	}

	public List<LocationNoIdDTO> getLocations() {
		return locations;
	}

	public void setLocations(List<LocationNoIdDTO> locations) {
		this.locations = locations;
	}

	public VehicleType getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(VehicleType vehicleType) {
		this.vehicleType = vehicleType;
	}

	public boolean isBabyTransport() {
		return babyTransport;
	}

	public void setBabyTransport(boolean babyTransport) {
		this.babyTransport = babyTransport;
	}

	public boolean isPetTransport() {
		return petTransport;
	}

	public void setPetTransport(boolean petTransport) {
		this.petTransport = petTransport;
	}
		
	
}
