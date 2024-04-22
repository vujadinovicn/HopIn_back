package com.hopin.HopIn.dtos;

public class CompleteRideReviewDTO {

	ReviewReturnedDTO vehicleReview;
	ReviewReturnedDTO driverReview;
	
	public CompleteRideReviewDTO() {}
	
	public CompleteRideReviewDTO(ReviewReturnedDTO vehicleReview, ReviewReturnedDTO driverReview) {
		super();
		this.vehicleReview = vehicleReview;
		this.driverReview = driverReview;
	}
	
	public ReviewReturnedDTO getVehicleReview() {
		return vehicleReview;
	}
	
	public void setVehicleReview(ReviewReturnedDTO vehicleReview) {
		this.vehicleReview = vehicleReview;
	}
	
	public ReviewReturnedDTO getDriverReview() {
		return driverReview;
	}
	
	public void setDriverReview(ReviewReturnedDTO driverReview) {
		this.driverReview = driverReview;
	}

	@Override
	public String toString() {
		return "CompleteRideReviewDTO [vehicleReview=" + vehicleReview + ", driverReview=" + driverReview + "]";
	}
	
	
}
