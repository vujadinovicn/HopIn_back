package com.hopin.HopIn.entities;

import com.hopin.HopIn.dtos.LocationNoIdDTO;
import com.hopin.HopIn.enums.VehicleType;

public class Vehicle {

	private int id;
	private int driverId;
	private VehicleType vehicleType;
	private String model;
	private String licenseNumber;
	private LocationNoIdDTO currentLocation;
	private int passengerSeats;
	private boolean babyTransport;
	private boolean petTransport;

	public Vehicle() {}
	
	public Vehicle(int id, int driverId, VehicleType vehicleType, String model, String licenseNumber,
			LocationNoIdDTO currentLocation, int passengerSeats, boolean babyTransport, boolean petTransport) {
		super();
		this.id = id;
		this.driverId = driverId;
		this.vehicleType = vehicleType;
		this.model = model;
		this.licenseNumber = licenseNumber;
		this.currentLocation = currentLocation;
		this.passengerSeats = passengerSeats;
		this.babyTransport = babyTransport;
		this.petTransport = petTransport;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getDriverId() {
		return driverId;
	}

	public void setDriverId(int driverId) {
		this.driverId = driverId;
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

	public LocationNoIdDTO getCurrentLocation() {
		return currentLocation;
	}

	public void setCurrentLocation(LocationNoIdDTO currentLocation) {
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
