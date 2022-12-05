package com.HopIn.HopIn.dtos;

import java.time.LocalDateTime;
import java.util.List;

import com.HopIn.HopIn.enums.RideStatus;
import com.HopIn.HopIn.enums.VehicleType;

public class RideReturnedDTO {
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private double totalCost;
	private UserInRideDTO driver;
	private List<UserInRideDTO> passengers;
	private int estimatedTimeInMinutes;
	private RideStatus status;
	private boolean panic;
	private boolean pet;
	private boolean baby;
	private VehicleType vehicleType;
	
	public RideReturnedDTO(LocalDateTime startTime, LocalDateTime endTime, double price, UserInRideDTO driver,
			List<UserInRideDTO> passengers, int estimatedTimeInMinutes, RideStatus status, boolean panic, boolean pet,
			boolean baby, VehicleType vehicleType) {
		super();
		this.startTime = startTime;
		this.endTime = endTime;
		this.totalCost = price;
		this.driver = driver;
		this.passengers = passengers;
		this.estimatedTimeInMinutes = estimatedTimeInMinutes;
		this.status = status;
		this.panic = panic;
		this.pet = pet;
		this.baby = baby;
		this.vehicleType = vehicleType;
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
	public boolean isPanic() {
		return panic;
	}
	public void setPanic(boolean panic) {
		this.panic = panic;
	}
	public boolean isPet() {
		return pet;
	}
	public void setPet(boolean pet) {
		this.pet = pet;
	}
	public boolean isBaby() {
		return baby;
	}
	public void setBaby(boolean baby) {
		this.baby = baby;
	}
	public VehicleType getVehicleType() {
		return vehicleType;
	}
	public void setVehicleType(VehicleType vehicleType) {
		this.vehicleType = vehicleType;
	}
	
	
	
}
