package com.hopin.HopIn.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.hopin.HopIn.enums.RideStatus;
import com.hopin.HopIn.dtos.LocationDTO;
import com.hopin.HopIn.dtos.LocationNoIdDTO;
import com.hopin.HopIn.dtos.RideDTO;
import com.hopin.HopIn.dtos.UserInRideDTO;
import com.hopin.HopIn.enums.UserType;
import com.hopin.HopIn.enums.VehicleType;


public class Ride {
	private int id;
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private double totalCost;
	private UserInRideDTO driver;
	private List<UserInRideDTO> passengers;
	private int estimatedTimeInMinutes;
	private VehicleType vehicleType;
	private boolean petTransport;
	private boolean babyTransport;
	private List<Review> reviews;
	private RejectionNotice rejectionNotice;
	private List<LocationNoIdDTO> locations;
	private RideStatus status;

	
	
	public Ride() {}

	public Ride(LocalDateTime startTime, LocalDateTime endTime, double price, int estimatedDuration,
			RideStatus status, boolean pet, boolean baby, VehicleType vehicleType, List<Review> reviews,
			RejectionNotice rejectionNotice, List<UserInRideDTO> passengers, List<LocationNoIdDTO> locations) {
		super();
		this.startTime = startTime;
		this.endTime = endTime;
		this.totalCost = price;
		this.estimatedTimeInMinutes = estimatedDuration;
		this.status = status;
		this.petTransport = pet;
		this.babyTransport = baby;
		this.vehicleType = vehicleType;
		this.reviews = reviews;
		this.rejectionNotice = rejectionNotice;
		this.passengers = passengers;
		this.locations = locations;
	}
	
	
	
	public Ride(int id, LocalDateTime startTime, LocalDateTime endTime, double price, int estimatedTimeInMinutes,
			RideStatus status, boolean pet, boolean baby, VehicleType vehicleType, List<Review> reviews,
			RejectionNotice rejectionNotice, List<UserInRideDTO> passengers, List<LocationNoIdDTO> locations, UserInRideDTO driver) {
		super();
		this.setId(id);
		this.startTime = startTime;
		this.endTime = endTime;
		this.totalCost = price;
		this.estimatedTimeInMinutes = estimatedTimeInMinutes;
		this.status = status;
		this.petTransport = pet;
		this.babyTransport = baby;
		this.vehicleType = vehicleType;
		this.reviews = reviews;
		this.rejectionNotice = rejectionNotice;
		this.passengers = passengers;
		this.locations = locations;
		this.driver = driver;
	}

	
	public Ride(RideDTO dto, int id) {
		this.setId(id);
		this.locations = new ArrayList<LocationNoIdDTO>();
		for(LocationDTO locDto : dto.getLocations()) {
			this.locations.add(locDto.getDeparture());
			this.locations.add(locDto.getDestinations());
		}
		this.passengers = dto.getPassengers();
		this.vehicleType = dto.getVehicleType();
		this.babyTransport = dto.isBabyTransport();
		this.petTransport = dto.isPetTransport();
		
		// dummy data
		this.startTime = LocalDateTime.now().minusHours(1);
		this.endTime = LocalDateTime.now().plusHours(1);
		this.totalCost = 2000;
		this.estimatedTimeInMinutes = 5;
		this.status = RideStatus.PENDING;
		this.driver = new UserInRideDTO(1, "driver@gmail.com", UserType.DRIVER);
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

	public UserInRideDTO getDriver() {
		return driver;
	}

	public void setDriver(UserInRideDTO driver) {
		this.driver = driver;
	}

	public void setEstimatedTimeInMinutes(int estimatedTimeInMinutes) {
		this.estimatedTimeInMinutes = estimatedTimeInMinutes;
	}

	public void setTotalCost(double price) {
		this.totalCost = price;
	}

	public int getEstimatedTimeInMinutes() {
		return estimatedTimeInMinutes;
	}

	public void setEstimatedTimeInMinutesDuration(int estimatedDuration) {
		this.estimatedTimeInMinutes = estimatedDuration;
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

	public List<Review> getReviews() {
		return reviews;
	}

	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}

	public RejectionNotice getRejectionNotice() {
		return rejectionNotice;
	}

	public void setRejectionNotice(RejectionNotice rejectionNotice) {
		this.rejectionNotice = rejectionNotice;
	}

	public List<UserInRideDTO> getPassengers() {
		return passengers;
	}

	public void setPassengers(List<UserInRideDTO> passengers) {
		this.passengers = passengers;
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
}
