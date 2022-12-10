package com.hopin.HopIn.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.hibernate.mapping.List;
import org.springframework.stereotype.Service;
import com.hopin.HopIn.dtos.AllReviewsReturnedDTO;
import com.hopin.HopIn.dtos.AllRideReviewsDTO;
import com.hopin.HopIn.dtos.ReviewDTO;
import com.hopin.HopIn.dtos.ReviewReturnedDTO;
import com.hopin.HopIn.entities.Review;
import com.hopin.HopIn.entities.User;
import com.hopin.HopIn.services.interfaces.IReviewService;

@Service
public class ReviewServiceImpl implements IReviewService{
	
	Map<Integer, ArrayList<Review>> allVehicleReviews = new HashMap<Integer, ArrayList<Review>>();
	Map<Integer, ArrayList<Review>> allDriverReviews = new HashMap<Integer, ArrayList<Review>>();
	Map<Integer, ArrayList<Review>> allRideReviews = new HashMap<Integer, ArrayList<Review>>();

	@Override
	public AllReviewsReturnedDTO getDriverReviews(int driverId) {
		ArrayList<Review> currentDriverReviews = getByDriver(driverId);
		if (currentDriverReviews == null) {
			currentDriverReviews = new ArrayList<Review>();
			currentDriverReviews.add(new Review(1, 3, "Stinky driver!", null));
		}
		return new AllReviewsReturnedDTO(currentDriverReviews);
	}

	@Override
	public AllReviewsReturnedDTO getVehicleReviews(int vehicleId) {
		ArrayList<Review> currentVehicleReviews = getByVehicle(vehicleId);
		if (currentVehicleReviews == null) {
			currentVehicleReviews = new ArrayList<Review>();
			currentVehicleReviews.add(new Review(1, 3, "Messy vehicle!", null));
		}
		return new AllReviewsReturnedDTO(currentVehicleReviews);
	}

	@Override
	public ReviewReturnedDTO addVehicleReview(int vehicleId, int rideId, ReviewDTO reviewDTO) {
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
	public ReviewReturnedDTO addDriverReview(int driverId, int rideId, ReviewDTO reviewDTO) {
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
	
	@Override
	public AllRideReviewsDTO getRideReviews(int rideId) {
		ArrayList<Review> reviews = new ArrayList<Review>();
		reviews.add(new Review(1, 3, "Bad vehicle", null));
		return new AllRideReviewsDTO(reviews, reviews);
	}
	
	public ArrayList<Review> getByVehicle(int vehicleId){
		return allVehicleReviews.get(vehicleId);
	}
	
	public ArrayList<Review> getByDriver(int driverId){
		return allDriverReviews.get(driverId);
	}
	
	private Review generateReview(int id, ReviewDTO reviewDTO) {
		return new Review(id, reviewDTO.getRating(), reviewDTO.getComment(), null);
	}
}
