package com.hopin.HopIn.dtos;

import java.util.List;

import com.hopin.HopIn.enums.VehicleTypeName;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public class UnregisteredUserRideDTO {
	@Valid
	@NotNull
	List<LocationDTO> locations;
	
	@Valid
	@NotNull(message = "is required")
	VehicleTypeName vehicleType;
	
	@NotNull(message = "is required")
	Boolean babyTransport;
	
	@NotNull(message = "is required")
	Boolean petTransport;
	
	public UnregisteredUserRideDTO(List<LocationDTO> locations, VehicleTypeName vehicleType, Boolean babyTransport,
			Boolean petTransport) {
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

	public Boolean isBabyTransport() {
		return babyTransport;
	}

	public void setBabyTransport(Boolean babyTransport) {
		this.babyTransport = babyTransport;
	}

	public Boolean isPetTransport() {
		return petTransport;
	}

	public void setPetTransport(Boolean petTransport) {
		this.petTransport = petTransport;
	}
		
	
}
