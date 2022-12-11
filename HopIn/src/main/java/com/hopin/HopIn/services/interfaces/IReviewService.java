package com.hopin.HopIn.services.interfaces;

import java.util.ArrayList;

import com.hopin.HopIn.dtos.AllReviewsReturnedDTO;
import com.hopin.HopIn.dtos.CompleteRideReviewDTO;
import com.hopin.HopIn.dtos.ReviewDTO;
import com.hopin.HopIn.dtos.ReviewReturnedDTO;

public interface IReviewService {
	
	public ArrayList<CompleteRideReviewDTO> getRideReviews(int rideId);
	
	public AllReviewsReturnedDTO getDriverReviews(int driverId);
	
	public AllReviewsReturnedDTO getVehicleReviews(int vehicleId);
	
	public ReviewReturnedDTO addVehicleReview(int vehicleId, int rideId, ReviewDTO review);
	
	public ReviewReturnedDTO addDriverReview(int driverId, int rideId, ReviewDTO review);
}
