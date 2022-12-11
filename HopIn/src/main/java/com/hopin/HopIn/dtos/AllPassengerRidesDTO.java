package com.hopin.HopIn.dtos;

import java.util.HashSet;
import java.util.Set;

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
