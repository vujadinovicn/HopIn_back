package com.hopin.HopIn.dtos;

import java.util.List;

import com.hopin.HopIn.enums.VehicleTypeName;

import jakarta.validation.constraints.NotNull;

public class UnregisteredUserRideDTO {
	List<LocationDTO> locations;
	VehicleTypeName vehicleType;
	
	@NotNull(message = "is required")
	boolean babyTransport;
	
	@NotNull(message = "is required")
	boolean petTransport;
	
	public UnregisteredUserRideDTO(List<LocationDTO> locations, VehicleTypeName vehicleType, boolean babyTransport,
			boolean petTransport) {
		super();
		this.locations = locations;
		this.vehicleType = vehicleType;
		this.babyTransport = babyTransport;
		this.petTransport = petTransport;
	}

	public List<LocationDTO> getLocations() {
		return locations;
	}

	public void setLocations(List<LocationDTO> locations) {
		this.locations = locations;
	}

	public VehicleTypeName getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(VehicleTypeName vehicleType) {
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
