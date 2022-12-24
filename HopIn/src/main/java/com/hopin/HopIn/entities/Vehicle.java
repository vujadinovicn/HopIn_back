package com.hopin.HopIn.entities;

import org.springframework.format.annotation.NumberFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "vehicles")
public class Vehicle {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotNull
	private int driverId;
	
	@Pattern(regexp = "^([a-zA-Z0-9- ]*)$")
	private String model;
	
	@Pattern(regexp = "^([A-Z]{2}-[0-9]{3}-[A-Z]{2})$")
	private String licenseNumber;
	
	@NumberFormat
	private int passengerSeats;
	
	@NotNull
	private boolean babyTransport;
	
	@NotNull
	private boolean petTransport;

	@ManyToOne(cascade = CascadeType.REFRESH)
	private VehicleType vehicleType;

	@OneToOne(cascade = CascadeType.REFRESH)
	private Location currentLocation;

	public Vehicle() {
	}

	public Vehicle(int id, int driverId, String model, String licenseNumber, int passengerSeats, boolean babyTransport,
			boolean petTransport, VehicleType vehicleType, Location currentLocation) {
		super();
		this.id = id;
		this.driverId = driverId;
		this.model = model;
		this.licenseNumber = licenseNumber;
		this.passengerSeats = passengerSeats;
		this.babyTransport = babyTransport;
		this.petTransport = petTransport;
		this.vehicleType = vehicleType;
		this.currentLocation = currentLocation;
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

	public VehicleType getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(VehicleType vehicleType) {
		this.vehicleType = vehicleType;
	}

	public Location getCurrentLocation() {
		return currentLocation;
	}

	public void setCurrentLocation(Location currentLocation) {
		this.currentLocation = currentLocation;
	}

}
