package com.hopin.HopIn.services.interfaces;

import com.hopin.HopIn.dtos.AllReviewsReturnedDTO;
import com.hopin.HopIn.dtos.AllRideReviewsDTO;
import com.hopin.HopIn.dtos.ReviewDTO;
import com.hopin.HopIn.dtos.ReviewReturnedDTO;

public interface IReviewService {
	
	public AllRideReviewsDTO getRideReviews(int rideId);
	
	public AllReviewsReturnedDTO getDriverReviews(int driverId);
	
	public AllReviewsReturnedDTO getVehicleReviews(int vehicleId);
	
	public ReviewReturnedDTO addVehicleReview(int vehicleId, ReviewDTO review);
	
	public ReviewReturnedDTO addDriverReview(int driverId, ReviewDTO review);
}
