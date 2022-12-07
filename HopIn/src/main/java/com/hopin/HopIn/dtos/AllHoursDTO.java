package com.hopin.HopIn.dtos;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.hopin.HopIn.entities.Driver;
import com.hopin.HopIn.entities.User;
import com.hopin.HopIn.entities.WorkingHours;

public class AllHoursDTO {
	private int totalCount;
	private Set<WorkingHoursReturnedDTO> results;

	public AllHoursDTO(int totalCount, Set<WorkingHoursReturnedDTO> results) {
		super();
		this.totalCount = totalCount;
		this.results = results;
	}
	
	public AllHoursDTO(int totalCount, Collection<WorkingHours> allHours) {
		super();
		this.totalCount = allHours.size();
		
		this.results = new HashSet<WorkingHoursReturnedDTO>();
		for(WorkingHours hour : allHours) {
			this.results.add(new WorkingHoursReturnedDTO(hour));
		}
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public Set<WorkingHoursReturnedDTO> getResults() {
		return results;
	}

	public void setResults(Set<WorkingHoursReturnedDTO> results) {
		this.results = results;
	}

}
