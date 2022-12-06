package com.HopIn.HopIn.dtos;

import java.util.ArrayList;
import java.util.List;

import com.HopIn.HopIn.entities.Review;

public class AllRideReviewsDTO {
	
	List<Review> vehicleReview = new ArrayList<Review>();
	List<Review> driverReview = new ArrayList<Review>();
	
	public AllRideReviewsDTO(ArrayList<Review> vehicleReview, ArrayList<Review> driverReview) {
		super();
		this.vehicleReview = vehicleReview;
		this.driverReview = driverReview;
	}

	public List<Review> getVehicleReview() {
		return vehicleReview;
	}

	public void setVehicleReview(List<Review> vehicleReview) {
		this.vehicleReview = vehicleReview;
	}

	public List<Review> getDriverReview() {
		return driverReview;
	}

	public void setDriverReview(List<Review> driverReview) {
		this.driverReview = driverReview;
	}
}
