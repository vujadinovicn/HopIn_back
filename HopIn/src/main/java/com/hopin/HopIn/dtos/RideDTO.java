package com.hopin.HopIn.dtos;

import java.util.List;
import java.util.Map;

import com.hopin.HopIn.entities.Location;
import com.hopin.HopIn.enums.VehicleType;

public class RideDTO {
	private Map<String, Location> locations;
	private List<UserInRideDTO> passengers;
	private VehicleType vehicleType;
	private boolean babyTransport;
	private boolean petTransport;
	
	public RideDTO(Map<String, Location> locations, List<UserInRideDTO> passengers, VehicleType vehicleType,
			boolean babyTransport, boolean petTransport) {
		super();
		this.locations = locations;
		this.passengers = passengers;
		this.vehicleType = vehicleType;
		this.babyTransport = babyTransport;
		this.petTransport = petTransport;
	}

	public Map<String, Location> getLocations() {
		return locations;
	}

	public void setLocations(Map<String, Location> locations) {
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
