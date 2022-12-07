package com.HopIn.HopIn.dtos;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.HopIn.HopIn.entities.Ride;
import com.HopIn.HopIn.enums.VehicleType;

public class AllUserRidesReturnedDTO {
	
	private int totalCount;
	private Set<UserRidesReturnedDTO> results;
	
	public AllUserRidesReturnedDTO() {}

	public AllUserRidesReturnedDTO(Map<Integer, Ride> allRides) {
		super();
		this.totalCount = allRides.size();
		
		this.results = new HashSet<UserRidesReturnedDTO>();
		for(Ride ride : allRides.values()) {
			this.results.add(new UserRidesReturnedDTO(ride));
		}
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public Set<UserRidesReturnedDTO> getResults() {
		return results;
	}

	public void setResults(Set<UserRidesReturnedDTO> results) {
		this.results = results;
	}

}