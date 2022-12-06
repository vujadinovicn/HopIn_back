package com.HopIn.HopIn.dtos;

public class EstimatedRideDetailsDTO {
	int estimatedTimeInMinutes;
	int estimatedCost;
	
	public EstimatedRideDetailsDTO(int estimatedTimeInMinutes, int estimatedCost) {
		super();
		this.estimatedTimeInMinutes = estimatedTimeInMinutes;
		this.estimatedCost = estimatedCost;
	}

	public int getEstimatedTimeInMinutes() {
		return estimatedTimeInMinutes;
	}

	public void setEstimatedTimeInMinutes(int estimatedTimeInMinutes) {
		this.estimatedTimeInMinutes = estimatedTimeInMinutes;
	}

	public int getEstimatedCost() {
		return estimatedCost;
	}

	public void setEstimatedCost(int estimatedCost) {
		this.estimatedCost = estimatedCost;
	}
	
	

}
