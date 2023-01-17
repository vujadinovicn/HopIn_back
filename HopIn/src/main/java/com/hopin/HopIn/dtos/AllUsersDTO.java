package com.hopin.HopIn.dtos;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.hopin.HopIn.entities.Driver;
import com.hopin.HopIn.entities.Passenger;
import com.hopin.HopIn.entities.User;

public class AllUsersDTO {
	private int totalCount;
	private Set<UserReturnedDTO> results;
	
	public AllUsersDTO() {}
	

	public AllUsersDTO(List<Passenger> passengers) {
		super();
		this.totalCount = passengers.size();
		this.results = new HashSet<UserReturnedDTO>();
		for(User user : passengers) {
			this.results.add(new UserReturnedDTO(user));
		}
	}
	
	public AllUsersDTO(Collection<Driver> allDrivers) {
		super();
		this.totalCount = allDrivers.size();
		
		this.results = new HashSet<UserReturnedDTO>();
		for(User user : allDrivers) {
			this.results.add(new UserReturnedDTO(user));
		}
	}
	
	

	public AllUsersDTO(int totalCount, Set<UserReturnedDTO> results) {
		super();
		this.totalCount = totalCount;
		this.results = results;
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
