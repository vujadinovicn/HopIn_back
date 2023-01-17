package com.hopin.HopIn.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public class LocationDTO {
	@Valid
	@NotNull(message = "is required")
	private LocationNoIdDTO departure;
	@Valid
	@NotNull(message = "is required")
	private LocationNoIdDTO destination;

	public LocationDTO(LocationNoIdDTO departure, LocationNoIdDTO destinations) {
		super();
		this.departure = departure;
		this.destination = destinations;
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

	public void setDestinations(LocationNoIdDTO destination) {
		this.destination = destination;
	}

}
