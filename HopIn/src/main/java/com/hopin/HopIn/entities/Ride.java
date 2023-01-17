package com.hopin.HopIn.entities;

import java.time.LocalDateTime;
import java.util.HashSet;
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
	private boolean panic;
	private RideStatus status;
	private double distance;
	private LocalDateTime scheduledTime;

	@ManyToMany(cascade = {})
	private Set<Passenger> passengers = new HashSet<Passenger>();

	@ManyToOne(cascade = CascadeType.REFRESH)
	private Driver driver;

	@OneToMany(cascade = { CascadeType.ALL })
	private Set<Review> reviews = new HashSet<Review>();

	@ManyToOne(cascade = {})
	private VehicleType vehicleType;

	@ManyToOne(cascade = {CascadeType.ALL})
	private Location departureLocation;

	@ManyToOne(cascade = {CascadeType.ALL})
	private Location destinationLocation;

	@OneToOne(cascade = { CascadeType.ALL })
	private RejectionNotice rejectionNotice;

	public Ride() {
	}

	public Ride(int id, LocalDateTime startTime, LocalDateTime endTime, double totalCost, double totalDistance,
			int estimatedTimeInMinutes, boolean petTransport, boolean babyTransport, boolean panic, RideStatus status,
			double distance, Set<Passenger> passengers, Driver driver, Set<Review> reviews, VehicleType vehicleType,
			Location departureLocation, Location destinationLocation, RejectionNotice rejectionNotice) {
		super();
		this.id = id;
		this.startTime = startTime;
		this.endTime = endTime;
		this.totalCost = totalCost;
		this.totalDistance = totalDistance;
		this.estimatedTimeInMinutes = estimatedTimeInMinutes;
		this.petTransport = petTransport;
		this.babyTransport = babyTransport;
		this.panic = panic;
		this.status = status;
		this.distance = distance;
		this.passengers = passengers;
		this.driver = driver;
		this.reviews = reviews;
		this.vehicleType = vehicleType;
		this.departureLocation = departureLocation;
		this.destinationLocation = destinationLocation;
		this.rejectionNotice = rejectionNotice;
		this.scheduledTime = null;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public double getTotalDistance() {
		return totalDistance;
	}

	public void setTotalDistance(double totalDistance) {
		this.totalDistance = totalDistance;
	}

	public int getEstimatedTimeInMinutes() {
		return estimatedTimeInMinutes;
	}

	public void setEstimatedTimeInMinutes(int estimatedTimeInMinutes) {
		this.estimatedTimeInMinutes = estimatedTimeInMinutes;
	}

	public boolean isPetTransport() {
		return petTransport;
	}

	public void setPetTransport(boolean petTransport) {
		this.petTransport = petTransport;
	}

	public boolean isBabyTransport() {
		return babyTransport;
	}

	public void setBabyTransport(boolean babyTransport) {
		this.babyTransport = babyTransport;
	}

	public boolean isPanic() {
		return panic;
	}

	public void setPanic(boolean panic) {
		this.panic = panic;
	}

	public RideStatus getStatus() {
		return status;
	}

	public void setStatus(RideStatus status) {
		this.status = status;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public Set<Passenger> getPassengers() {
		return passengers;
	}

	public void setPassengers(Set<Passenger> passengers) {
		this.passengers = passengers;
	}

	public Driver getDriver() {
		return driver;
	}

	public void setDriver(Driver driver) {
		this.driver = driver;
	}

	public Set<Review> getReviews() {
		return reviews;
	}

	public void setReviews(Set<Review> reviews) {
		this.reviews = reviews;
	}

	public VehicleType getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(VehicleType vehicleType) {
		this.vehicleType = vehicleType;
	}

	public Location getDepartureLocation() {
		return departureLocation;
	}

	public void setDepartureLocation(Location departureLocation) {
		this.departureLocation = departureLocation;
	}

	public Location getDestinationLocation() {
		return destinationLocation;
	}

	public void setDestinationLocation(Location destinationLocation) {
		this.destinationLocation = destinationLocation;
	}

	public RejectionNotice getRejectionNotice() {
		return rejectionNotice;
	}

	public void setRejectionNotice(RejectionNotice rejectionNotice) {
		this.rejectionNotice = rejectionNotice;
	}

	public LocalDateTime getScheduledTime() {
		return scheduledTime;
	}

	public void setScheduledTime(LocalDateTime scheduledTime) {
		this.scheduledTime = scheduledTime;
	}
	
	public void addReview(Review review) {
		this.reviews.add(review);
	}

	@Override
	public String toString() {
		return "Ride [id=" + id + ", startTime=" + startTime + ", endTime=" + endTime + ", totalCost=" + totalCost
				+ ", totalDistance=" + totalDistance + ", estimatedTimeInMinutes=" + estimatedTimeInMinutes
				+ ", petTransport=" + petTransport + ", babyTransport=" + babyTransport + ", panic=" + panic
				+ ", status=" + status + ", distance=" + distance + ", scheduledTime=" + scheduledTime + ", passengers="
				+ passengers + ", driver=" + driver + ", reviews=" + reviews + ", vehicleType=" + vehicleType
				+ ", departureLocation=" + departureLocation + ", destinationLocation=" + destinationLocation
				+ ", rejectionNotice=" + rejectionNotice + "]";
	}
	
	

}
