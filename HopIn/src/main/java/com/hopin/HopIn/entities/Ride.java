package com.hopin.HopIn.entities;

import java.security.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

enum RideStatus {
	PENDING,
	ACCEPTED,
	REJECTED,
	ACTIVE,
	FINISHED
}

enum VehicleType {
	STANDARD,
	LUXURY,
	VAN
}

public class Ride {
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private double price;
	private Timestamp estimatedDuration;
	private RideStatus status;
	private boolean panic;
	private boolean pet;
	private boolean baby;
	private VehicleType vehicleType;
	private List<Review> reviews;
	private RejectionNotice rejectionNotice;
	private List<Passenger> passengers;
	private List<Location> locations;
	
	public Ride() {}

	public Ride(LocalDateTime startTime, LocalDateTime endTime, double price, Timestamp estimatedDuration,
			RideStatus status, boolean panic, boolean pet, boolean baby, VehicleType vehicleType, List<Review> reviews,
			RejectionNotice rejectionNotice, List<Passenger> passengers, List<Location> locations) {
		super();
		this.startTime = startTime;
		this.endTime = endTime;
		this.price = price;
		this.estimatedDuration = estimatedDuration;
		this.status = status;
		this.panic = panic;
		this.pet = pet;
		this.baby = baby;
		this.vehicleType = vehicleType;
		this.reviews = reviews;
		this.rejectionNotice = rejectionNotice;
		this.passengers = passengers;
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

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Timestamp getEstimatedDuration() {
		return estimatedDuration;
	}

	public void setEstimatedDuration(Timestamp estimatedDuration) {
		this.estimatedDuration = estimatedDuration;
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

	public List<Passenger> getPassengers() {
		return passengers;
	}

	public void setPassengers(List<Passenger> passengers) {
		this.passengers = passengers;
	}

	public List<Location> getLocations() {
		return locations;
	}

	public void setLocations(List<Location> locations) {
		this.locations = locations;
	}
}
