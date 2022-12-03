package com.hopin.HopIn.dtos;

import java.util.HashSet;
import java.util.Set;

import com.hopin.HopIn.entities.User;

public class AllPassengersDto {
	private Set<UserDto> results;
	private int totalCount;
	
	public AllPassengersDto() {}

	public AllPassengersDto(Set<User> users) {
		super();
		this.totalCount = users.size();
		
		this.results = new HashSet<UserDto>();
		for(User user : users) {
			this.results.add(new UserDto(user));
		}
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public Set<UserDto> getResults() {
		return results;
	}

	public void setResults(Set<UserDto> results) {
		this.results = results;
	}
	
	
}
