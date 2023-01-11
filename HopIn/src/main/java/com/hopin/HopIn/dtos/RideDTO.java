package com.hopin.HopIn.dtos;

import java.util.List;

import com.hopin.HopIn.enums.VehicleTypeName;

public class RideDTO {
	private List<LocationDTO> locations;
	private List<UserInRideDTO> passengers;
	private VehicleTypeName vehicleType;
	private boolean babyTransport;
	private boolean petTransport;
	
	public RideDTO(List<LocationDTO> locations, List<UserInRideDTO> passengers, VehicleTypeName vehicleType,
			boolean babyTransport, boolean petTransport) {
		super();
		this.locations = locations;
		this.passengers = passengers;
		this.vehicleType = vehicleType;
		this.babyTransport = babyTransport;
		this.petTransport = petTransport;
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
