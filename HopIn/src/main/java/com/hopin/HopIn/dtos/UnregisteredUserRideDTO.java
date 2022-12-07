package com.HopIn.HopIn.dtos;

import java.util.List;

import com.HopIn.HopIn.entities.Location;
import com.HopIn.HopIn.enums.VehicleType;

public class UnregisteredUserRideDTO {
	List<Location> locations;
	VehicleType vehicleType;
	boolean babyTransport;
	boolean petTransport;
	
	public UnregisteredUserRideDTO(List<Location> locations, VehicleType vehicleType, boolean babyTransport,
			boolean petTransport) {
		super();
		this.locations = locations;
		this.vehicleType = vehicleType;
		this.babyTransport = babyTransport;
		this.petTransport = petTransport;
	}

	public List<Location> getLocations() {
		return locations;
	}

	public void setLocations(List<Location> locations) {
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
