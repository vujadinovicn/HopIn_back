package com.hopin.HopIn.dtos;

import com.hopin.HopIn.entities.Location;
import com.hopin.HopIn.enums.VehicleType;

public class VehicleDTO {

	private VehicleType vehicleType;
	private String model;
	private String licenseNumber;
	private Location currentLocation;
	private int passengerSeats;
	private boolean babyTransport;
	private boolean petTransport;

	public VehicleDTO(VehicleType vehicleType, String model, String licenseNumber, Location currentLocation,
			int passengerSeats, boolean babyTransport, boolean petTransport) {
		super();
		this.vehicleType = vehicleType;
		this.model = model;
		this.licenseNumber = licenseNumber;
		this.currentLocation = currentLocation;
		this.passengerSeats = passengerSeats;
		this.babyTransport = babyTransport;
		this.petTransport = petTransport;
	}

	public VehicleType getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(VehicleType vehicleType) {
		this.vehicleType = vehicleType;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getLicenseNumber() {
		return licenseNumber;
	}

	public void setLicenseNumber(String licenseNumber) {
		this.licenseNumber = licenseNumber;
	}

	public Location getCurrentLocation() {
		return currentLocation;
	}

	public void setCurrentLocation(Location currentLocation) {
		this.currentLocation = currentLocation;
	}

	public int getPassengerSeats() {
		return passengerSeats;
	}

	public void setPassengerSeats(int passengerSeats) {
		this.passengerSeats = passengerSeats;
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
