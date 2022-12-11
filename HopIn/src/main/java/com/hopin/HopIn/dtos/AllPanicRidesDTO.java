package com.hopin.HopIn.dtos;

import java.util.Set;

import com.hopin.HopIn.entities.PanicRide;

public class AllPanicRidesDTO {
	private int totalCount;
	private Set<PanicRide> results;
	
	public AllPanicRidesDTO(int totalCount, Set<PanicRide> panicRides) {
		super();
		this.totalCount = totalCount;
		this.setResults(panicRides);
	}
	
	public AllPanicRidesDTO(Set<PanicRide> rides) {
		super();
		this.totalCount = rides.size();
		this.setResults(rides);
	}
	
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public Set<PanicRide> getResults() {
		return results;
	}

	public void setResults(Set<PanicRide> results) {
		this.results = results;
	}
	
	
	
}
