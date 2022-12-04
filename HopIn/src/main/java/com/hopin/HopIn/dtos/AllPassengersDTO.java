package com.hopin.HopIn.dtos;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.hopin.HopIn.entities.Passenger;
import com.hopin.HopIn.entities.User;

public class AllPassengersDTO {
	private int totalCount;
	private Set<UserReturnedDTO> results;
	
	public AllPassengersDTO() {}

	public AllPassengersDTO(Map<Integer, Passenger> allPassengers) {
		super();
		this.totalCount = allPassengers.size();
		
		this.results = new HashSet<UserReturnedDTO>();
		for(Passenger passenger : allPassengers.values()) {
			this.results.add(new UserReturnedDTO(passenger));
		}
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public Set<UserReturnedDTO> getResults() {
		return results;
	}

	public void setResults(Set<UserReturnedDTO> results) {
		this.results = results;
	}
	
	
}
