package com.hopin.HopIn.dtos;

import com.hopin.HopIn.entities.Vehicle;
import com.hopin.HopIn.enums.VehicleTypeName;

public class VehicleDTO {

	private VehicleTypeName vehicleType;
	private String model;
	private String licenseNumber;
	private LocationNoIdDTO currentLocation;
	private int passengerSeats;
	private boolean babyTransport;
	private boolean petTransport;

	public VehicleDTO(VehicleTypeName vehicleType, String model, String licenseNumber, LocationNoIdDTO currentLocation,
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
	
	public VehicleDTO(Vehicle vehicle) {
		this.vehicleType = vehicle.getVehicleType().getName();
		this.model = vehicle.getModel();
		this.licenseNumber = vehicle.getLicenseNumber();
		this.currentLocation = new LocationNoIdDTO(vehicle.getCurrentLocation());
		this.passengerSeats = vehicle.getPassengerSeats();
		this.babyTransport = vehicle.isBabyTransport();
		this.petTransport = vehicle.isPetTransport();
	}

	public VehicleTypeName getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(VehicleTypeName vehicleType) {
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
