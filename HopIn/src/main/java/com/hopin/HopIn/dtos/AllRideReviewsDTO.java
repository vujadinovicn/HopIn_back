package com.hopin.HopIn.dtos;

import java.util.ArrayList;
import java.util.List;

import com.hopin.HopIn.entities.Review;

public class AllRideReviewsDTO {

	private Review vehicleReview;
	private Review driverReview;

	public AllRideReviewsDTO(Review vehicleReview, Review driverReview) {
		super();
		this.vehicleReview = vehicleReview;
		this.driverReview = driverReview;
	}

	public Review getVehicleReview() {
		return vehicleReview;
	}

	public void setVehicleReview(Review vehicleReview) {
		this.vehicleReview = vehicleReview;
	}

	public Review getDriverReview() {
		return driverReview;
	}

	public void setDriverReview(Review driverReview) {
		this.driverReview = driverReview;
	}

}
