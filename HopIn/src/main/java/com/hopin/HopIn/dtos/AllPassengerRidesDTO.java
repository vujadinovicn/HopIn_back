package com.hopin.HopIn.dtos;

import java.util.ArrayList;
import java.util.List;

import com.hopin.HopIn.entities.Ride;

public class AllPassengerRidesDTO {
	private int totalCount;
	private List<PassengerRideDTO> results;
	
	public AllPassengerRidesDTO() {
		this.results = new ArrayList<PassengerRideDTO>();
	}

	public AllPassengerRidesDTO(int totalCount, List<PassengerRideDTO> result) {
		super();
		this.totalCount = totalCount;
		this.results = result;
	}
	
	public AllPassengerRidesDTO(List<Ride> rides) {
		super();
		this.totalCount = rides.size();
		this.results = new ArrayList<PassengerRideDTO>();
		for (Ride ride : rides)
			this.results.add(new PassengerRideDTO(ride));
	}
	
	public void add(PassengerRideDTO ride) {
		this.results.add(ride);
		this.totalCount++;
	}
	
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public List<PassengerRideDTO> getResults() {
		return results;
	}
	public void setResult(List<PassengerRideDTO> result) {
		this.results = result;
	}
	
	
}
