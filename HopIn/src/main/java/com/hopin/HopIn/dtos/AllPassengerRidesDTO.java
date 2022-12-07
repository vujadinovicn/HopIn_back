package com.hopin.HopIn.dtos;

import java.util.HashSet;
import java.util.Set;

public class AllPassengerRidesDTO {
	private int totalCount;
	private Set<PassengerRideDTO> result;
	
	public AllPassengerRidesDTO() {
		this.result = new HashSet<PassengerRideDTO>();
	}

	public AllPassengerRidesDTO(int totalCount, Set<PassengerRideDTO> result) {
		super();
		this.totalCount = totalCount;
		this.result = result;
	}
	
	public void add(PassengerRideDTO ride) {
		this.result.add(ride);
		this.totalCount++;
	}
	
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public Set<PassengerRideDTO> getResult() {
		return result;
	}
	public void setResult(Set<PassengerRideDTO> result) {
		this.result = result;
	}
	
	
}
