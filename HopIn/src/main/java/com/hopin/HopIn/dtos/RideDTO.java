package com.hopin.HopIn.dtos;

import java.util.List;

import com.hopin.HopIn.enums.VehicleType;

public class RideDTO {
	private List<LocationsDTO> locations;
	private List<UserInRideDTO> passengers;
	private VehicleType vehicleType;
	private boolean babyTransport;
	private boolean petTransport;
	
	public RideDTO(List<LocationsDTO> locations, List<UserInRideDTO> passengers, VehicleType vehicleType,
			boolean babyTransport, boolean petTransport) {
		super();
		this.locations = locations;
		this.passengers = passengers;
		this.vehicleType = vehicleType;
		this.babyTransport = babyTransport;
		this.petTransport = petTransport;
	}

	public List<LocationsDTO> getLocations() {
		return locations;
	}

	public void setLocations(List<LocationsDTO> locations) {
		this.locations = locations;
	}

	public List<UserInRideDTO> getPassengers() {
		return passengers;
	}

	public void setPassengers(List<UserInRideDTO> passengers) {
		this.passengers = passengers;
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
