package com.hopin.HopIn.dtos;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.hopin.HopIn.entities.Location;
import com.hopin.HopIn.entities.Passenger;
import com.hopin.HopIn.entities.RejectionNotice;
import com.hopin.HopIn.entities.Ride;
import com.hopin.HopIn.entities.User;
import com.hopin.HopIn.enums.RideStatus;
import com.hopin.HopIn.enums.VehicleTypeName;

public class RideReturnedDTO {
	int id;
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private double totalCost;
	private double distance;
	private UserInRideDTO driver;
	private List<UserInRideDTO> passengers;
	private int estimatedTimeInMinutes;
	private VehicleTypeName vehicleType; 
	private boolean petTransport;
	private boolean babyTransport;
	private RejectionNotice rejection;
	private List<LocationDTO> locations;
	private RideStatus status;
	private LocalDateTime scheduledTime;
	private String distanceFormatted;
	private String durationFormatted;
	private String scheduledTimeFormatted;
	
	public RideReturnedDTO() {
		
	}

	
	public RideReturnedDTO(int id, LocalDateTime startTime, LocalDateTime endTime, double totalCost,
			UserInRideDTO driver, List<UserInRideDTO> passengers, int estimatedTimeInMinutes,
			VehicleTypeName vehicleType, boolean petTransport, boolean babyTransport, RejectionNotice rejection,
			List<LocationDTO> locations, RideStatus status, LocalDateTime scheduledTime, double distance) {
		super();
		this.id = id;
		this.startTime = startTime;
		this.endTime = endTime;
		this.totalCost = totalCost;
		this.driver = driver;
		this.passengers = passengers;
		this.estimatedTimeInMinutes = estimatedTimeInMinutes;
		this.vehicleType = vehicleType;
		this.petTransport = petTransport;
		this.babyTransport = babyTransport;
		this.rejection = rejection;
		this.locations = locations;
		this.status = status;
		this.scheduledTime = scheduledTime;
		this.distance = distance;
	}


	public RideReturnedDTO(RideDTO ride) {
		this.startTime = LocalDateTime.now();
		this.endTime = LocalDateTime.now();
		this.totalCost = 200;
		this.driver = null;
		this.passengers = ride.getPassengers();
		this.estimatedTimeInMinutes = 15;
		this.status = RideStatus.PENDING;
		this.petTransport = ride.isPetTransport();
		this.babyTransport = ride.isBabyTransport();
		this.vehicleType = ride.getVehicleType();
		this.locations = ride.getLocations();
		this.distance = ride.getDistance();
		this.distanceFormatted = ride.getDistanceFormatted();
		this.durationFormatted = ride.getDurationFormatted();
		
		this.rejection = null;
	}

//	public RideReturnedDTO(Ride ride) {
//		this.startTime = ride.getStartTime();
//		this.endTime = ride.getEndTime();
//		this.totalCost = ride.getTotalCost();
//		this.driver = ride.getDriver();
//		this.passengers = ride.getPassengers();
//		this.estimatedTimeInMinutes = ride.getEstimatedTimeInMinutes();
//		this.status = ride.getStatus();
//		this.petTransport = ride.isPet();
//		this.babyTransport = ride.isBaby();
//		this.vehicleType = ride.getVehicleType();
//		this.locations = new ArrayList<LocationDTO>();
//		for(int i = 0; i < ride.getLocations().size(); i =+ 2) {
//			this.locations.add(new LocationDTO(ride.getLocations().get(i), ride.getLocations().get(i+1)));
//		}
//		this.rejection = ride.getRejectionNotice();
//	}


	public RideReturnedDTO(Ride ride) {
		System.out.println(ride);
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
		this.status = ride.getStatus();
		this.scheduledTime = ride.getScheduledTime();
		this.rejection = ride.getRejectionNotice();
		this.distance = ride.getTotalDistance();
		this.vehicleType = ride.getVehicleType().getName();
	}
	
	
	

	public String getScheduledTimeFormatted() {
		return scheduledTimeFormatted;
	}


	public void setScheduledTimeFormatted(String scheduledTimeFormatted) {
		this.scheduledTimeFormatted = scheduledTimeFormatted;
	}


	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
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

	public VehicleTypeName getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(VehicleTypeName vehicleType) {
		this.vehicleType = vehicleType;
	}

	public List<LocationDTO> getLocations() {
		return locations;
	}

	public void setLocations(List<LocationDTO> locations) {
		this.locations = locations;
	}

	public RejectionNotice getRejection() {
		return rejection;
	}

	public void setRejection(RejectionNotice rejection) {
		this.rejection = rejection;
	}

	public LocalDateTime getScheduledTime() {
		return scheduledTime;
	}

	public void setScheduledTime(LocalDateTime scheduledTime) {
		this.scheduledTime = scheduledTime;
	}

	

	public String getDistanceFormatted() {
		return distanceFormatted;
	}


	public void setDistanceFormatted(String distanceFormatted) {
		this.distanceFormatted = distanceFormatted;
	}


	public String getDurationFormatted() {
		return durationFormatted;
	}


	public void setDurationFormatted(String durationFormatted) {
		this.durationFormatted = durationFormatted;
	}


	@Override
	public String toString() {
		return "RideReturnedDTO [id=" + id + ", startTime=" + startTime + ", endTime=" + endTime + ", totalCost="
				+ totalCost + ", distance=" + distance + ", driver=" + driver + ", passengers=" + passengers
				+ ", estimatedTimeInMinutes=" + estimatedTimeInMinutes + ", vehicleType=" + vehicleType
				+ ", petTransport=" + petTransport + ", babyTransport=" + babyTransport + ", rejection=" + rejection
				+ ", locations=" + locations + ", status=" + status + ", scheduledTime=" + scheduledTime + "]";
	}

	
	
}
