package com.hopin.HopIn.dtos;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.hopin.HopIn.entities.Driver;
import com.hopin.HopIn.entities.Passenger;
import com.hopin.HopIn.entities.User;

public class AllUsersDTO {
	private int totalCount;
	private Set<UserReturnedDTO> results;
	
	public AllUsersDTO() {}

	public AllUsersDTO(Map<Integer, User> allUsers) {
		super();
		this.totalCount = allUsers.size();
		
		this.results = new HashSet<UserReturnedDTO>();
		for(User user : allUsers.values()) {
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
