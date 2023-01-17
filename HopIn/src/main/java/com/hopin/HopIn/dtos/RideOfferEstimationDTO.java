package com.hopin.HopIn.dtos;

public class RideOfferEstimationDTO {
	
	private double totalCost;
	private double distance;
	private int estimatedTimeInMinutes;
	
	
	public RideOfferEstimationDTO(double totalCost, double distance, int estimatedTimeInMinutes) {
		super();
		this.totalCost = totalCost;
		this.distance = distance;
		this.estimatedTimeInMinutes = estimatedTimeInMinutes;
	}
	
	public double getTotalCost() {
		return totalCost;
	}
	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}
	public double getDistance() {
		return distance;
	}
	public void setDistance(double distance) {
		this.distance = distance;
	}
	public int getEstimatedTimeInMinutes() {
		return estimatedTimeInMinutes;
	}
	public void setEstimatedTimeInMinutes(int estimatedTimeInMinutes) {
		this.estimatedTimeInMinutes = estimatedTimeInMinutes;
	}
	
	
	
}
