package com.HopIn.HopIn.dtos;

import java.time.LocalDateTime;
import java.util.List;

import com.HopIn.HopIn.entities.Location;
import com.HopIn.HopIn.entities.Passenger;
import com.HopIn.HopIn.entities.Ride;
import com.HopIn.HopIn.enums.VehicleType;

public class UserRidesReturnedDTO {
	
	int id;
	List<Location> locations;
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private int totalCost;
	private UserInRideDTO driver;
	private List<UserInRideDTO> passengers;
	private int estimatedTimeInMinutes;
	private VehicleType vehicleType;
	private boolean babyTransport;
	private boolean petTransport;
	
	public UserRidesReturnedDTO(int id, List<Location> locations, LocalDateTime startTime, LocalDateTime endTime,
			int totalCost, UserInRideDTO driver, List<UserInRideDTO> passengers, int estimatedTimeInMinutes,
			VehicleType vehicleType, boolean babyTransport, boolean petTransport) {
		super();
		this.id = id;
		this.locations = locations;
		this.startTime = startTime;
		this.endTime = endTime;
		this.totalCost = totalCost;
		this.driver = driver;
		this.passengers = passengers;
		this.estimatedTimeInMinutes = estimatedTimeInMinutes;
		this.vehicleType = vehicleType;
		this.babyTransport = babyTransport;
		this.petTransport = petTransport;
	}
	
	public UserRidesReturnedDTO(Ride ride) {
		super();
		this.id = ride.getId();
		this.startTime = ride.getStartTime();
		this.endTime = ride.getEndTime();
		this.totalCost = (int) ride.getPrice();
		this.driver = new UserInRideDTO(ride.getDriver());
		for (Passenger p : ride.getPassengers())
			this.passengers.add(new UserInRideDTO(p));
		this.estimatedTimeInMinutes = ride.getEstimatedTimeInMinutes();
		this.petTransport = ride.isPet();
		this.babyTransport = ride.isBaby();
		this.vehicleType = ride.getVehicleType();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<Location> getLocations() {
		return locations;
	}

	public void setLocations(List<Location> locations) {
		this.locations = locations;
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

	public int getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(int totalCost) {
		this.totalCost = totalCost;
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
