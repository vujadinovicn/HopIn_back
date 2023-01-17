package com.hopin.HopIn.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public class RouteLocsDTO {
	
	@Valid
	@NotNull
	LocationNoIdDTO departure;
	
	@Valid
	@NotNull
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
