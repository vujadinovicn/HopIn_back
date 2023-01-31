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
	
	private Double distance;
	
	private Double duration;
	
	private Double price;
	
	private LocalDateTime scheduledTime;
	
	private String durationFormatted;
	private String distanceFormatted;

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
	
	

	public RideDTO(@Valid @NotNull(message = "is required") List<LocationDTO> locations,
			@Valid @NotNull(message = "is required") List<UserInRideDTO> passengers,
			@NotNull(message = "is required") VehicleTypeName vehicleType,
			@NotNull(message = "is required") Boolean babyTransport,
			@NotNull(message = "is required") Boolean petTransport, Double distance, Double duration, Double price,
			LocalDateTime scheduledTime, String durationFormatted, String distanceFormatted) {
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
		this.durationFormatted = durationFormatted;
		this.distanceFormatted = distanceFormatted;
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

	public String getDurationFormatted() {
		return durationFormatted;
	}

	public void setDurationFormatted(String durationFormatted) {
		this.durationFormatted = durationFormatted;
	}

	public String getDistanceFormatted() {
		return distanceFormatted;
	}

	public void setDistanceFormatted(String distanceFormatted) {
		this.distanceFormatted = distanceFormatted;
	}

	@Override
	public String toString() {
		return "RideDTO [locations=" + locations  + ", vehicleType=" + vehicleType
				+ ", babyTransport=" + babyTransport + ", petTransport=" + petTransport + ", distance=" + distance
				+ ", duration=" + duration + ", price=" + price + ", scheduledTime=" + scheduledTime
				+ ", durationFormatted=" + durationFormatted + ", distanceFormatted=" + distanceFormatted + "]";
	}

	
	
}
