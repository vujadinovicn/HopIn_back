package com.hopin.HopIn.dtos;

import java.time.LocalDateTime;

import com.hopin.HopIn.entities.Ride;

public class RideForReportDTO {
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private double distance;
	private int estimatedTimeInMinutes;
	private double totalCost;
	private boolean rejected;

	public RideForReportDTO(LocalDateTime startTime, LocalDateTime endTime, double distance, int estimatedTimeInMinutes,
			double totalCost, boolean rejected) {
		super();
		this.startTime = startTime;
		this.endTime = endTime;
		this.distance = distance;
		this.estimatedTimeInMinutes = estimatedTimeInMinutes;
		this.totalCost = totalCost;
		this.setRejected(rejected);
	}
	
	public RideForReportDTO(Ride ride) {
		this.startTime = ride.getStartTime();
		this.endTime = ride.getEndTime();
		this.distance = ride.getDistance();
		this.estimatedTimeInMinutes = ride.getEstimatedTimeInMinutes();
		this.totalCost = ride.getTotalCost();
		if (ride.getRejectionNotice() == null)
			this.rejected = false;
		else
			this.rejected = true;
	}

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	public LocalDateTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
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

	public double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}

	public boolean isRejected() {
		return rejected;
	}

	public void setRejected(boolean rejected) {
		this.rejected = rejected;
	}

}
