package com.hopin.HopIn.entities;

import org.springframework.format.annotation.NumberFormat;

import com.hopin.HopIn.enums.RequestStatus;
import com.hopin.HopIn.enums.RequestType;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "driver_account_update_vehicle_requests")
public class DriverAccountUpdateVehicleRequest extends DriverAccountUpdateRequest {

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
	
//	@NotNull
//	private VehicleType vehicleType;

	public DriverAccountUpdateVehicleRequest() {
	}

	public DriverAccountUpdateVehicleRequest(int id, RequestStatus status, String reason, Driver driver, Administrator admin,
			String model, String licenseNumber, int passengerSeats,
			boolean babyTransport, boolean petTransport, VehicleType vehicleType) {
		super(id, status, reason, driver, admin, RequestType.VEHICLE);
		this.model = model;
		this.licenseNumber = licenseNumber;
		this.passengerSeats = passengerSeats;
		this.babyTransport = babyTransport;
		this.petTransport = petTransport;
		//this.vehicleType = vehicleType;
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

//	public VehicleType getVehicleType() {
//		return vehicleType;
//	}
//
//	public void setVehicleType(VehicleType vehicleType) {
//		this.vehicleType = vehicleType;
//	}
	
	

}
