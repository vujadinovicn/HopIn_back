package com.hopin.HopIn.dtos;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.hopin.HopIn.entities.Passenger;
import com.hopin.HopIn.entities.RejectionNotice;
import com.hopin.HopIn.entities.Ride;
import com.hopin.HopIn.enums.RideStatus;
import com.hopin.HopIn.enums.VehicleType;

public class RejectedRideDTO {
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private double totalCost;
	private UserInRideDTO driver;
	private List<UserInRideDTO> passengers;
	private int estimatedTimeInMinutes;
	private RideStatus status;
	private boolean petTransport;
	private boolean babyTransport;
	private VehicleType vehicleType;
	private RejectionNotice rejection;
	
	public RejectedRideDTO(LocalDateTime startTime, LocalDateTime endTime, double price, UserInRideDTO driver,
			List<UserInRideDTO> passengers, int estimatedTimeInMinutes, RideStatus status, boolean pet,
			boolean baby, VehicleType vehicleType) {
		super();
		this.startTime = startTime;
		this.endTime = endTime;
		this.totalCost = price;
		this.driver = driver;
		this.passengers = passengers;
		this.estimatedTimeInMinutes = estimatedTimeInMinutes;
		this.status = status;
		this.petTransport = pet;
		this.babyTransport = baby;
		this.vehicleType = vehicleType;
	}
	
	public RejectedRideDTO(Ride ride) {
		this.startTime = ride.getStartTime();
		this.endTime = ride.getEndTime();
		this.totalCost = ride.getPrice();
		this.driver = new UserInRideDTO(ride.getDriver());
		this.passengers = new ArrayList<UserInRideDTO>();
		for(Passenger passenger : ride.getPassengers()) {
			this.passengers.add(new UserInRideDTO(passenger));
		}
		this.estimatedTimeInMinutes = ride.getEstimatedTimeInMinutes();
		this.status = ride.getStatus();
		this.petTransport = ride.isPet();
		this.babyTransport = ride.isBaby();
		this.vehicleType = ride.getVehicleType();
		this.rejection = ride.getRejectionNotice();
	}
	
	public LocalDateTime getStartTime() {
		return startTime;
	}
	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}
	public LocalDateTime getEndTime() {
		return endTime;
	}
	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}
	public double getTotalCost() {
		return totalCost;
	}
	public void setTotalCost(double price) {
		this.totalCost = price;
	}
	public UserInRideDTO getDriver() {
		return driver;
	}
	public void setDriver(UserInRideDTO driver) {
		this.driver = driver;
	}
	public List<UserInRideDTO> getPassengers() {
		return passengers;
	}
	public void setPassengers(List<UserInRideDTO> passengers) {
		this.passengers = passengers;
	}
	public int getEstimatedTimeInMinutes() {
		return estimatedTimeInMinutes;
	}
	public void setEstimatedTimeInMinutes(int estimatedTimeInMinutes) {
		this.estimatedTimeInMinutes = estimatedTimeInMinutes;
	}
	public RideStatus getStatus() {
		return status;
	}
	public void setStatus(RideStatus status) {
		this.status = status;
	}
	public boolean isPet() {
		return petTransport;
	}
	public void setPet(boolean pet) {
		this.petTransport = pet;
	}
	public boolean isBaby() {
		return babyTransport;
	}
	public void setBaby(boolean baby) {
		this.babyTransport = baby;
	}
	public VehicleType getVehicleType() {
		return vehicleType;
	}
	public void setVehicleType(VehicleType vehicleType) {
		this.vehicleType = vehicleType;
	}
	

}
