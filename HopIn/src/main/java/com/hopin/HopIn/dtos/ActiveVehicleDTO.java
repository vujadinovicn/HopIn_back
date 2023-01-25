package com.hopin.HopIn.dtos;

public class ActiveVehicleDTO {

	private int vehicleId;
	private int driverId;
	private LocationNoIdDTO currentLocation;
	
	
	public ActiveVehicleDTO() {
		
	}
	
	public ActiveVehicleDTO(int vehicleId, int driverId, LocationNoIdDTO currentLocation) {
		super();
		this.vehicleId = vehicleId;
		this.driverId = driverId;
		this.currentLocation = currentLocation;
	}
	
	public int getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(int vehicleId) {
		this.vehicleId = vehicleId;
	}

	public int getDriverId() {
		return driverId;
	}

	public void setDriverId(int driverId) {
		this.driverId = driverId;
	}

	public LocationNoIdDTO getCurrentLocation() {
		return currentLocation;
	}
	
	public void setCurrentLocation(LocationNoIdDTO currentLocation) {
		this.currentLocation = currentLocation;
	}
	
	
}
