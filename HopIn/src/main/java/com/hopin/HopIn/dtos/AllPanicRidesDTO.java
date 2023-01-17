package com.hopin.HopIn.dtos;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.hopin.HopIn.entities.Panic;

public class AllPanicRidesDTO {
	private int totalCount;
	private Set<PanicRideDTO> results;
	
	public AllPanicRidesDTO(int totalCount, Set<PanicRideDTO> panicRides) {
		super();
		this.totalCount = totalCount;
		this.setResults(panicRides);
	}
	
	public AllPanicRidesDTO(Set<PanicRideDTO> rides) {
		super();
		this.totalCount = rides.size();
		this.setResults(rides);
	}
	
	public AllPanicRidesDTO(List<Panic> panics) {
		this.results = new HashSet<PanicRideDTO>();
		this.totalCount = 0;
		for (Panic panic : panics) {
			this.results.add(new PanicRideDTO(panic));
			this.totalCount++;
		}
	}
	
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public Set<PanicRideDTO> getResults() {
		return results;
	}

	public void setResults(Set<PanicRideDTO> results) {
		this.results = results;
	}
	
	
	
}
