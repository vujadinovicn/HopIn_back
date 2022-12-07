package com.hopin.HopIn.dtos;

import com.hopin.HopIn.entities.Location;

public class LocationsDTO {
	private Location departure;
	private Location destination;
	public LocationsDTO(Location departure, Location destinations) {
		super();
		this.departure = departure;
		this.destination = destinations;
	}
	public Location getDeparture() {
		return departure;
	}
	public void setDeparture(Location departure) {
		this.departure = departure;
	}
	public Location getDestinations() {
		return destination;
	}
	public void setDestinations(Location destination) {
		this.destination = destination;
	}
	
	
}
