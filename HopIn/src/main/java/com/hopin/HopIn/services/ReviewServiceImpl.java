package com.hopin.HopIn.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.hopin.HopIn.dtos.AllReviewsReturnedDTO;
import com.hopin.HopIn.dtos.CompleteRideReviewDTO;
import com.hopin.HopIn.dtos.ReviewDTO;
import com.hopin.HopIn.dtos.ReviewReturnedDTO;
import com.hopin.HopIn.entities.Passenger;
import com.hopin.HopIn.entities.Review;
import com.hopin.HopIn.entities.Ride;
import com.hopin.HopIn.enums.ReviewType;
import com.hopin.HopIn.repositories.PassengerRepository;
import com.hopin.HopIn.repositories.ReviewRepository;
import com.hopin.HopIn.repositories.RideRepository;
import com.hopin.HopIn.repositories.VehicleRepository;
import com.hopin.HopIn.services.interfaces.IDriverService;
import com.hopin.HopIn.services.interfaces.IReviewService;

@Service
public class ReviewServiceImpl implements IReviewService{
	
	@Autowired
	private RideRepository allRides;
	
	@Autowired
	private PassengerRepository allPassengers;
	
	@Autowired
	private VehicleRepository allVehicles;
	
	@Autowired
	private ReviewRepository allReviews;
	
	@Autowired
	private IDriverService driverService;
	
	Map<Integer, ArrayList<Review>> allVehicleReviews = new HashMap<Integer, ArrayList<Review>>();
	Map<Integer, ArrayList<Review>> allDriverReviews = new HashMap<Integer, ArrayList<Review>>();
	Map<Integer, ArrayList<Review>> allRideReviews = new HashMap<Integer, ArrayList<Review>>();

	@Override
	public AllReviewsReturnedDTO getDriverReviews(int driverId) {
		driverService.getById(driverId);
		return new AllReviewsReturnedDTO(allReviews.findAllReviewsByDriverId(driverId));
	}

	@Override
	public AllReviewsReturnedDTO getVehicleReviews(int vehicleId) {
		this.allVehicles.findById(vehicleId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Vehicle does not exist!"));
		
		ArrayList<Review> reviews = (ArrayList<Review>) this.allReviews.findAllReviewsByVehicleId(vehicleId);
		
		return new AllReviewsReturnedDTO(reviews);
	}
	
	private Ride getRideIfExists(int id) {
		return this.allRides.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ride does not exist!"));
	}

	@Override
	public ReviewReturnedDTO addReview(int rideId, ReviewDTO reviewDTO, ReviewType type) {
		Ride ride = this.getRideIfExists(rideId);
		
		if (ride.getDriver() == null || ride.getDriver().getVehicle() == null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Vehicle does not exist!");
		
		Review review = this.dtoToReview(reviewDTO);
		review.setType(type);
		review.setRide(ride);
		Review savedReview = allReviews.save(review);
		allReviews.flush();
		
		ride.addReview(savedReview);
		allRides.save(ride);
		allRides.flush();
		
		return new ReviewReturnedDTO(savedReview);
	
	}

	private Review dtoToReview(ReviewDTO reviewDTO) {
		Review review = new Review();
		review.setComment(reviewDTO.getComment());
		review.setPassenger(null);
		review.setRating(reviewDTO.getRating());
		review.setType(ReviewType.VEHICLE);
		
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		Passenger passenger = allPassengers.findPassengerByEmail(authentication.getName()).orElse(null);
//		review.setPassenger(passenger);
		
		review.setPassenger(allPassengers.findById(1).orElse(null));
		
		return review;
	}
	
	@Override
	public ArrayList<CompleteRideReviewDTO> getRideReviews(int rideId) {
		Ride ride = getRideIfExists(rideId);
		System.out.println(ride.getReviews().size());
		return this.extractReviewFromRide(ride);
	}

	public ArrayList<Review> getByDriver(int driverId){
		return allDriverReviews.get(driverId);
	}
	
	private Review generateReview(int id, ReviewDTO reviewDTO) {
		return new Review(id, reviewDTO.getRating(), reviewDTO.getComment(), null, null);
	}
	
	private ArrayList<CompleteRideReviewDTO> extractReviewFromRide(Ride ride) {
		ReviewReturnedDTO driverReview = null;
		ReviewReturnedDTO vehicleReview = null;
		ArrayList<CompleteRideReviewDTO> ret = new ArrayList<CompleteRideReviewDTO>();
		
		for (Passenger passenger : ride.getPassengers()) {
			for (Review review : ride.getReviews()) {
				if (passenger == review.getPassenger() && review.getType() == ReviewType.DRIVER) {
					driverReview = new ReviewReturnedDTO(review);
				} 
				else if (passenger == review.getPassenger() && review.getType() == ReviewType.VEHICLE) {
					vehicleReview = new ReviewReturnedDTO(review);
				}
			}
			if (driverReview != null || vehicleReview != null) {
				ret.add(new CompleteRideReviewDTO(vehicleReview, driverReview));
				driverReview = null;
				vehicleReview = null;
			}
		}
		
		return ret;
	}
}
