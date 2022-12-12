package com.hopin.HopIn.dtos;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.hopin.HopIn.entities.Passenger;
import com.hopin.HopIn.entities.RejectionNotice;
import com.hopin.HopIn.entities.Ride;
import com.hopin.HopIn.enums.RideStatus;
import com.hopin.HopIn.enums.VehicleTypeName;

public class PassengerRideDTO {
	private int id;
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private double totalCost;
	private UserInRideDTO driver;
	private List<UserInRideDTO> passengers;
	private int estimatedTimeInMinutes;
	private VehicleTypeName vehicleType;
	private boolean babyTransport;
	private boolean petTransport;
	private RejectionNotice rejection;
	private List<LocationDTO> locations;

	
	
	public PassengerRideDTO(int id, List<LocationDTO> locations, LocalDateTime startTime, LocalDateTime endTime,
			double totalCost, UserInRideDTO driver, List<UserInRideDTO> passengers, int estimatedTimeInMinutes,
			VehicleTypeName vehicleType, boolean babyTransport, boolean petTransport, RejectionNotice rejection) {
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
	
//	public PassengerRideDTO(Ride ride) {
//		this.id = ride.getId();
//		this.locations = new ArrayList<LocationDTO>();
//		for(int i = 0; i < ride.getLocations().size(); i =+ 2) {
//			this.locations.add(new LocationDTO(ride.getLocations().get(i), ride.getLocations().get(i+1)));
//		}
//		this.startTime = ride.getStartTime();
//		this.endTime = ride.getEndTime();
//		this.totalCost = ride.getTotalCost();
//		this.driver = ride.getDriver();
//		this.passengers = ride.getPassengers();
//		this.estimatedTimeInMinutes = ride.getEstimatedTimeInMinutes();
//		this.vehicleType = ride.getVehicleType();
//		this.babyTransport = ride.isBaby();
//		this.petTransport = ride.isPet();
//		this.rejection = ride.getRejectionNotice();
//	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public List<LocationDTO> getLocations() {
		return locations;
	}
	public void setLocations(List<LocationDTO> locations) {
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
	public double getTotalCost() {
		return totalCost;
	}
	public void setTotalCost(double totalCost) {
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
	public VehicleTypeName getVehicleType() {
		return vehicleType;
	}
	public void setVehicleType(VehicleTypeName vehicleType) {
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
	public RejectionNotice getRejection() {
		return rejection;
	}
	public void setRejection(RejectionNotice rejection) {
		this.rejection = rejection;
	}
	
	
}
