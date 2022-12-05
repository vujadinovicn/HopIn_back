package com.hopin.HopIn.dtos;

import java.time.LocalDateTime;
import java.util.List;

import com.hopin.HopIn.entities.Location;
import com.hopin.HopIn.entities.Ride;
import com.hopin.HopIn.enums.RideStatus;
import com.hopin.HopIn.enums.VehicleType;

public class RideReturnedDTO {
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private double totalCost;
	private UserInRideDTO driver;
	private List<UserInRideDTO> passengers;
	private int estimatedTimeInMinutes;
	private VehicleType vehicleType;
	private boolean petTransport;
	private boolean babyTransport;
	private List<Location> locations;
	private RideStatus status;

	
	public RideReturnedDTO(LocalDateTime startTime, LocalDateTime endTime, double totalCost, UserInRideDTO driver,
			List<UserInRideDTO> passengers, int estimatedTimeInMinutes, VehicleType vehicleType, boolean petTransport,
			boolean babyTransport, List<Location> locations, RideStatus status) {
		super();
		this.startTime = startTime;
		this.endTime = endTime;
		this.totalCost = totalCost;
		this.driver = driver;
		this.passengers = passengers;
		this.estimatedTimeInMinutes = estimatedTimeInMinutes;
		this.vehicleType = vehicleType;
		this.petTransport = petTransport;
		this.babyTransport = babyTransport;
		this.setLocations(locations);
		this.status = status;
	}

	public RideReturnedDTO(Ride ride) {
		this.startTime = ride.getStartTime();
		this.endTime = ride.getEndTime();
		this.totalCost = ride.getTotalCost();
		this.driver = ride.getDriver();
		this.passengers = ride.getPassengers();
		this.estimatedTimeInMinutes = ride.getEstimatedTimeInMinutes();
		this.status = ride.getStatus();
		this.petTransport = ride.isPet();
		this.babyTransport = ride.isBaby();
		this.vehicleType = ride.getVehicleType();
		this.setLocations(ride.getLocations());

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
	public boolean isPetTransport() {
		return petTransport;
	}
	public void setPetTransport(boolean pet) {
		this.petTransport = pet;
	}
	public boolean isBabyTransport() {
		return babyTransport;
	}
	public void setBabyTransport(boolean baby) {
		this.babyTransport = baby;
	}
	public VehicleType getVehicleType() {
		return vehicleType;
	}
	public void setVehicleType(VehicleType vehicleType) {
		this.vehicleType = vehicleType;
	}

	public List<Location> getLocations() {
		return locations;
	}

	public void setLocations(List<Location> locations) {
		this.locations = locations;
	}
	
	
	
}
