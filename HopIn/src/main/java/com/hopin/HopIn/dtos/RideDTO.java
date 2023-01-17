package com.hopin.HopIn.dtos;

import java.time.LocalDateTime;
import java.util.List;

import com.hopin.HopIn.enums.VehicleTypeName;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public class RideDTO {
	
	@Valid
	@NotNull(message="is required")
	private List<LocationDTO> locations;
	
	@Valid
	@NotNull(message="is required")
	private List<UserInRideDTO> passengers;
	
	@NotNull(message="is required")
	private VehicleTypeName vehicleType;
	
	@NotNull(message="is required")
	private Boolean babyTransport;
	
	@NotNull(message="is required")
	private Boolean petTransport;
	
	@NotNull(message="is required")
	private Double distance;
	
	@NotNull(message="is required")
	private Double duration;
	
	@NotNull(message="is required")
	private Double price;
	
	@Valid
	@NotNull(message="is required")
	private LocalDateTime scheduledTime;

	public RideDTO() {
	}

	public RideDTO(List<LocationDTO> locations, List<UserInRideDTO> passengers, VehicleTypeName vehicleType,
			Boolean babyTransport, Boolean petTransport) {
		super();
		this.locations = locations;
		this.passengers = passengers;
		this.vehicleType = vehicleType;
		this.babyTransport = babyTransport;
		this.petTransport = petTransport;
	}

	public RideDTO(List<LocationDTO> locations, List<UserInRideDTO> passengers, VehicleTypeName vehicleType,
			Boolean babyTransport, Boolean petTransport, Double distance, Double duration, Double price,
			LocalDateTime scheduledTime) {
		super();
		this.locations = locations;
		this.passengers = passengers;
		this.vehicleType = vehicleType;
		this.babyTransport = babyTransport;
		this.petTransport = petTransport;
		this.distance = distance;
		this.duration = duration;
		this.price = price;
		this.scheduledTime = scheduledTime;
	}

	public List<LocationDTO> getLocations() {
		return locations;
	}

	public void setLocations(List<LocationDTO> locations) {
		this.locations = locations;
	}

	public List<UserInRideDTO> getPassengers() {
		return passengers;
	}

	public void setPassengers(List<UserInRideDTO> passengers) {
		this.passengers = passengers;
	}

	public VehicleTypeName getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(VehicleTypeName vehicleType) {
		this.vehicleType = vehicleType;
	}

	public Boolean isBabyTransport() {
		return babyTransport;
	}

	public void setBabyTransport(Boolean babyTransport) {
		this.babyTransport = babyTransport;
	}

	public Boolean isPetTransport() {
		return petTransport;
	}

	public void setPetTransport(Boolean petTransport) {
		this.petTransport = petTransport;
	}
	
	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}

	public Double getDuration() {
		return duration;
	}

	public void setDuration(Double duration) {
		this.duration = duration;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public LocalDateTime getScheduledTime() {
		return scheduledTime;
	}

	public void setScheduledTime(LocalDateTime scheduledTime) {
		this.scheduledTime = scheduledTime;
	}
	
	public LocationNoIdDTO getDepartureLocation() {
		return this.getLocations().get(0).getDeparture();
	}
	
	public LocationNoIdDTO getDestinationLocation() {
		return this.getLocations().get(0).getDestination();
	}
	

	public String getDepartureLocationLat() {
		return Double.toString(this.getDepartureLocation().getLatitude());
	}
	
	public String getDepartureLocationLng() {
		return Double.toString(this.getDepartureLocation().getLongitude());
	}
	
	public String getDestinationLocationLat() {
		return Double.toString(this.getDestinationLocation().getLatitude());
	}
	
	public String getDestinationLocationLng() {
		return Double.toString(this.getDestinationLocation().getLongitude());
	}
}
