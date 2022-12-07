package com.HopIn.HopIn.services.interfaces;

import com.HopIn.HopIn.dtos.AllReviewsReturnedDTO;
import com.HopIn.HopIn.dtos.AllRideReviewsDTO;
import com.HopIn.HopIn.dtos.ReviewDTO;
import com.HopIn.HopIn.dtos.ReviewReturnedDTO;

public interface IReviewService {
	
	public AllRideReviewsDTO getRideReviews(int rideId);
	
	public AllReviewsReturnedDTO getDriverReviews(int driverId);
	
	public AllReviewsReturnedDTO getVehicleReviews(int vehicleId);
	
	public ReviewReturnedDTO addVehicleReview(int vehicleId, ReviewDTO review);
	
	public ReviewReturnedDTO addDriverReview(int driverId, ReviewDTO review);
}