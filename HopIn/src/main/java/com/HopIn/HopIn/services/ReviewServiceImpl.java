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
	Map<Integer, ArrayList<Review>> allDriverReviews = new HashMap<Integer, ArrayList<Review>>();

	@Override
	public AllReviewsReturnedDTO getDriverReviews(int driverId) {
		ArrayList<Review> currentDriverReviews = getByVehicle(driverId);
		if (currentDriverReviews == null) {
			currentDriverReviews = new ArrayList<Review>();
			currentDriverReviews.add(new Review(1, 3, "Stinky driver!"));
		}
		return new AllReviewsReturnedDTO(currentDriverReviews);
	}

	@Override
	public AllReviewsReturnedDTO getVehicleReviews(int vehicleId) {
		ArrayList<Review> currentVehicleReviews = getByVehicle(vehicleId);
		if (currentVehicleReviews == null) {
			currentVehicleReviews = new ArrayList<Review>();
			currentVehicleReviews.add(new Review(1, 3, "Messy vehicle!"));
		}
		return new AllReviewsReturnedDTO(currentVehicleReviews);
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
	
	@Override
	public ReviewReturnedDTO addDriverReview(int driverId, ReviewDTO reviewDTO) {
		ArrayList<Review> currentDriverReviews = getByDriver(driverId);
		int generatedId;
		if (currentDriverReviews == null) {
			generatedId = 1;
			currentDriverReviews = new ArrayList<Review>();
		} else {
			generatedId = currentDriverReviews.size()+1;
		}
		Review review = generateReview(generatedId, reviewDTO);
		currentDriverReviews.add(review);
		return new ReviewReturnedDTO(review);
	}
	
	public ArrayList<Review> getByVehicle(int vehicleId){
		return allVehicleReviews.get(vehicleId);
	}
	
	public ArrayList<Review> getByDriver(int driverId){
		return allDriverReviews.get(driverId);
	}
	
	private Review generateReview(int id, ReviewDTO reviewDTO) {
		return new Review(id, reviewDTO.getRating(), reviewDTO.getComment());
	}

	
	

}
