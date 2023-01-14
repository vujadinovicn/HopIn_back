package com.hopin.HopIn.dtos;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.hopin.HopIn.entities.Ride;

public class AllPassengerRidesDTO {
	private int totalCount;
	private Set<PassengerRideDTO> results;
	
	public AllPassengerRidesDTO() {
		this.results = new HashSet<PassengerRideDTO>();
	}

	public AllPassengerRidesDTO(int totalCount, Set<PassengerRideDTO> result) {
		super();
		this.totalCount = totalCount;
		this.results = result;
	}
	
	public AllPassengerRidesDTO(List<Ride> rides) {
		super();
		this.totalCount = rides.size();
		this.results = new HashSet<PassengerRideDTO>();
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
	public Set<PassengerRideDTO> getResults() {
		return results;
	}
	public void setResult(Set<PassengerRideDTO> result) {
		this.results = result;
	}
	
	
}
