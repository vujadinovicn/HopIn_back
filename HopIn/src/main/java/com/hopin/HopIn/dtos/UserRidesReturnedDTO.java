package com.hopin.HopIn.dtos;

import java.time.LocalDateTime;
import java.util.List;

import com.hopin.HopIn.entities.Passenger;
import com.hopin.HopIn.entities.Ride;
import com.hopin.HopIn.enums.VehicleType;

public class UserRidesReturnedDTO {
	
	int id;
	LocalDateTime startTime;
	LocalDateTime endTime;
	int totalCost;
	UserInRideDTO driver;
	List<UserInRideDTO> passengers;
	int estimatedTimeInMinutes;
	VehicleType vehicleType;
	boolean babyTransport;
	boolean petTransport;
	RejectionNoticeDTO rejection;
	List<LocationNoIdDTO> locations;
	
	public UserRidesReturnedDTO(int id, List<LocationNoIdDTO> locations, LocalDateTime startTime, LocalDateTime endTime,
			int totalCost, UserInRideDTO driver, List<UserInRideDTO> passengers, int estimatedTimeInMinutes,
			VehicleType vehicleType, boolean babyTransport, boolean petTransport, RejectionNoticeDTO rejection) {
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
		this.rejection = rejection;
	}
	
	public UserRidesReturnedDTO(Ride ride) {
		super();
		this.id = ride.getId();
		this.startTime = ride.getStartTime();
		this.endTime = ride.getEndTime();
		this.totalCost = (int) ride.getTotalCost();
		this.driver = ride.getDriver();
		if (ride.getPassengers() == null)
			this.passengers = null;
		else {
			for (UserInRideDTO p : ride.getPassengers())
				this.passengers.add(p);
		}
		this.estimatedTimeInMinutes = ride.getEstimatedTimeInMinutes();
		this.petTransport = ride.isPet();
		this.babyTransport = ride.isBaby();
		this.vehicleType = ride.getVehicleType();
		this.rejection = new RejectionNoticeDTO(ride.getRejectionNotice());
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<LocationNoIdDTO> getLocations() {
		return locations;
	}

	public void setLocations(List<LocationNoIdDTO> locations) {
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

	public RejectionNoticeDTO getRejection() {
		return rejection;
	}

	public void setRejection(RejectionNoticeDTO rejection) {
		this.rejection = rejection;
	}

}
