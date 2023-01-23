package com.hopin.HopIn.dtos;

public class ActiveVehicleDTO {

	private int id;
	private LocationNoIdDTO currentLocation;
	
	
	public ActiveVehicleDTO() {
		
	}
	
	public ActiveVehicleDTO(int id, LocationNoIdDTO currentLocation) {
		super();
		this.id = id;
		this.currentLocation = currentLocation;
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public LocationNoIdDTO getCurrentLocation() {
		return currentLocation;
	}
	
	public void setCurrentLocation(LocationNoIdDTO currentLocation) {
		this.currentLocation = currentLocation;
	}
	
	
}
