package com.HopIn.HopIn.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.hibernate.mapping.List;
import org.springframework.stereotype.Service;
import com.HopIn.HopIn.dtos.AllReviewsReturnedDTO;
import com.HopIn.HopIn.dtos.ReviewDTO;
import com.HopIn.HopIn.dtos.ReviewReturnedDTO;
import com.HopIn.HopIn.entities.Review;
import com.HopIn.HopIn.entities.User;
import com.HopIn.HopIn.services.interfaces.IReviewService;

@Service
public class ReviewServiceImpl implements IReviewService{
	
	Map<Integer, ArrayList<Review>> allVehicleReviews = new HashMap<Integer, ArrayList<Review>>();

	@Override
	public AllReviewsReturnedDTO getDriverReviews() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AllReviewsReturnedDTO getVehicleReviews() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReviewReturnedDTO addVehicleReview(int vehicleId, ReviewDTO reviewDTO) {
		ArrayList<Review> currentVehicleReviews = getByVehicle(vehicleId);
		int generatedId;
		if (currentVehicleReviews == null) {
			generatedId = 1;
			currentVehicleReviews = new ArrayList<Review>();
		} else {
			generatedId = currentVehicleReviews.size()+1;
		}
		Review review = generateReview(generatedId, reviewDTO);
		currentVehicleReviews.add(review);
		return new ReviewReturnedDTO(review);
	}
	
	public ArrayList<Review> getByVehicle(int vehicleId){
		return allVehicleReviews.get(vehicleId);
	}
	
	private Review generateReview(int id, ReviewDTO reviewDTO) {
		return new Review(id, reviewDTO.getRating(), reviewDTO.getComment());
	}

	@Override
	public ReviewReturnedDTO addDriverReview() {
		// TODO Auto-generated method stub
		return null;
	}
	

}
