package com.hopin.HopIn.dtos;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.hopin.HopIn.entities.Passenger;
import com.hopin.HopIn.entities.RejectionNotice;
import com.hopin.HopIn.entities.Ride;
import com.hopin.HopIn.entities.User;
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
	private double distance;
	private LocalDateTime scheduledTime;

	public PassengerRideDTO(int id, List<LocationDTO> locations, LocalDateTime startTime, LocalDateTime endTime,
			double totalCost, UserInRideDTO driver, List<UserInRideDTO> passengers, int estimatedTimeInMinutes,
			VehicleTypeName vehicleType, boolean babyTransport, boolean petTransport, RejectionNotice rejection,
			double distance) {
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
		this.distance = distance;
	}

	public PassengerRideDTO(Ride ride) {
		this.id = ride.getId();
		this.driver = new UserInRideDTO(ride.getDriver());
		this.passengers = new ArrayList<UserInRideDTO>();
		for (User passenger : ride.getPassengers()) {
			this.passengers.add(new UserInRideDTO(passenger));
		}
		this.babyTransport = ride.isBabyTransport();
		this.petTransport = ride.isPetTransport();
		this.endTime = ride.getEndTime();
		this.startTime = ride.getStartTime();
		this.estimatedTimeInMinutes = ride.getEstimatedTimeInMinutes();
		this.locations = new ArrayList<LocationDTO>();
		this.locations.add(new LocationDTO(new LocationNoIdDTO(ride.getDepartureLocation()),
				new LocationNoIdDTO(ride.getDestinationLocation())));
		this.totalCost = ride.getTotalCost();
		this.rejection = ride.getRejectionNotice();
		this.distance = ride.getDistance();
		this.vehicleType = ride.getVehicleType().getName();
		this.scheduledTime = ride.getScheduledTime();
	}

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

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	@Override
	public String toString() {
		return "PassengerRideDTO [id=" + id + ", startTime=" + startTime + ", endTime=" + endTime + ", totalCost="
				+ totalCost + ", driver=" + driver + ", passengers=" + passengers + ", estimatedTimeInMinutes="
				+ estimatedTimeInMinutes + ", vehicleType=" + vehicleType + ", babyTransport=" + babyTransport
				+ ", petTransport=" + petTransport + ", rejection=" + rejection + ", locations=" + locations
				+ ", distance=" + distance + "]";
	}

	public LocalDateTime getScheduledTime() {
		return scheduledTime;
	}

	public void setScheduledTime(LocalDateTime scheduledTime) {
		this.scheduledTime = scheduledTime;
	}

}
