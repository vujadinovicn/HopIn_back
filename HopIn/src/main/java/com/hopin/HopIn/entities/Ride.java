package com.hopin.HopIn.entities;

import java.time.LocalDateTime;
import java.util.Set;

import com.hopin.HopIn.enums.RideStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "rides")
public class Ride {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private double totalCost;
	private double totalDistance;
	private int estimatedTimeInMinutes;
	private boolean petTransport;
	private boolean babyTransport;
	private RideStatus status;

	@ManyToMany(cascade = {})
	private Set<Passenger> passengers;

	@ManyToOne(cascade = {})
	private Driver driver;

	@OneToMany(cascade = { CascadeType.ALL })
	private Set<Review> reviews;

	@ManyToOne(cascade = {})
	private VehicleType vehicleType;

	@ManyToMany(cascade = {})
	private Set<Route> routes;
	
	@OneToOne(cascade = {CascadeType.ALL})
	private RejectionNotice rejectionNotice;


	public Ride() {
	}

	public Ride(int id, LocalDateTime startTime, LocalDateTime endTime, double totalCost, double totalDistance,
			int estimatedTimeInMinutes, boolean petTransport, boolean babyTransport, RejectionNotice rejectionNotice,
			RideStatus status, Set<Passenger> passengers, Driver driver, Set<Review> reviews, VehicleType vehicleType,
			Set<Route> routes) {
		super();
		this.id = id;
		this.startTime = startTime;
		this.endTime = endTime;
		this.totalCost = totalCost;
		this.totalDistance = totalDistance;
		this.estimatedTimeInMinutes = estimatedTimeInMinutes;
		this.petTransport = petTransport;
		this.babyTransport = babyTransport;
		this.rejectionNotice = rejectionNotice;
		this.status = status;
		this.passengers = passengers;
		this.driver = driver;
		this.reviews = reviews;
		this.vehicleType = vehicleType;
		this.routes = routes;
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

	public Driver getDriver() {
		return driver;
	}

	public void setDriver(Driver driver) {
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

	public Set<Review> getReviews() {
		return reviews;
	}

	public void setReviews(Set<Review> reviews) {
		this.reviews = reviews;
	}

	public RejectionNotice getRejectionNotice() {
		return rejectionNotice;
	}

	public void setRejectionNotice(RejectionNotice rejectionNotice) {
		this.rejectionNotice = rejectionNotice;
	}

	public Set<Passenger> getPassengers() {
		return passengers;
	}

	public void setPassengers(Set<Passenger> passengers) {
		this.passengers = passengers;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Set<Route> getRoutes() {
		return routes;
	}

	public void setRoutes(Set<Route> routes) {
		this.routes = routes;
	}

	public double getTotalDistance() {
		return totalDistance;
	}

	public void setTotalDistance(double totalDistance) {
		this.totalDistance = totalDistance;
	}
}
