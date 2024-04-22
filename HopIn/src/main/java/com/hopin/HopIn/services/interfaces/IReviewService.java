package com.hopin.HopIn.services.interfaces;

import java.util.ArrayList;
import java.util.List;

import com.hopin.HopIn.dtos.AllReviewsReturnedDTO;
import com.hopin.HopIn.dtos.CompleteRideReviewDTO;
import com.hopin.HopIn.dtos.ReviewDTO;
import com.hopin.HopIn.dtos.ReviewReturnedDTO;
import com.hopin.HopIn.enums.ReviewType;


public interface IReviewService {
	
	public ArrayList<CompleteRideReviewDTO> getRideReviews(int rideId);
	
	public AllReviewsReturnedDTO getDriverReviews(int driverId);
	
	public AllReviewsReturnedDTO getVehicleReviews(int vehicleId);
	
	public ReviewReturnedDTO addReview(int vehicleId, ReviewDTO review, ReviewType vehicle);

	public void addCompleteReview(
			int rideId, List<ReviewDTO> reviews);
}
