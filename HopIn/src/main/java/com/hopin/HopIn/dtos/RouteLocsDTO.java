package com.hopin.HopIn.dtos;

public class RouteLocsDTO {

	LocationNoIdDTO departure;
	LocationNoIdDTO destination;

	public RouteLocsDTO() {
	}

	public RouteLocsDTO(LocationNoIdDTO departure, LocationNoIdDTO destination) {
		super();
		this.departure = departure;
		this.destination = destination;
	}

	public LocationNoIdDTO getDeparture() {
		return departure;
	}

	public void setDeparture(LocationNoIdDTO departure) {
		this.departure = departure;
	}

	public LocationNoIdDTO getDestination() {
		return destination;
	}

	public void setDestination(LocationNoIdDTO destination) {
		this.destination = destination;
	}

}
