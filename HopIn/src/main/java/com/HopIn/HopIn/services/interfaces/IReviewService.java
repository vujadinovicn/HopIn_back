package com.HopIn.HopIn.services.interfaces;

import com.HopIn.HopIn.dtos.AllReviewsReturnedDTO;
import com.HopIn.HopIn.dtos.ReviewDTO;
import com.HopIn.HopIn.dtos.ReviewReturnedDTO;

public interface IReviewService {
	
	//public AllReviewsReturnedDTO getRideReviews();
	
	public AllReviewsReturnedDTO getDriverReviews();
	
	public AllReviewsReturnedDTO getVehicleReviews();
	
	public ReviewReturnedDTO addVehicleReview(int vehicleId, ReviewDTO review);
	
	public ReviewReturnedDTO addDriverReview(int driverId, ReviewDTO review);
}
