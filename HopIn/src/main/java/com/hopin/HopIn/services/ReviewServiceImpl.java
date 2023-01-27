package com.hopin.HopIn.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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
import com.hopin.HopIn.entities.User;
import com.hopin.HopIn.enums.ReviewType;
import com.hopin.HopIn.repositories.PassengerRepository;
import com.hopin.HopIn.repositories.ReviewRepository;
import com.hopin.HopIn.repositories.RideRepository;
import com.hopin.HopIn.repositories.VehicleRepository;
import com.hopin.HopIn.services.interfaces.IDriverService;
import com.hopin.HopIn.services.interfaces.IReviewService;
import com.hopin.HopIn.services.interfaces.IUserService;

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
	
	@Autowired
	private IUserService userService;
	
	Map<Integer, ArrayList<Review>> allVehicleReviews = new HashMap<Integer, ArrayList<Review>>();
	Map<Integer, ArrayList<Review>> allDriverReviews = new HashMap<Integer, ArrayList<Review>>();
	Map<Integer, ArrayList<Review>> allRideReviews = new HashMap<Integer, ArrayList<Review>>();

	@Override
	public AllReviewsReturnedDTO getDriverReviews(int rideId) {
		Optional<Ride> found = allRides.findById(rideId);
		if (found.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Driver does not exist!");
		}
		else if (found.get().getDriver() == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Driver does not exist!");
		}
		return new AllReviewsReturnedDTO(allReviews.findAllReviewsByDriverId(found.get().getDriver().getId(), rideId));
	}

	@Override
	public AllReviewsReturnedDTO getVehicleReviews(int rideId) {
		Optional<Ride> found = allRides.findById(rideId);
		if (found.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Vehicle does not exist!");
		}
		else if (found.get().getDriver().getVehicle() == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Vehicle does not exist!");
		}
		
		ArrayList<Review> reviews = (ArrayList<Review>) this.allReviews.findAllReviewsByRideId(rideId);
		
		return new AllReviewsReturnedDTO(reviews);
	}
	
	private Ride getRideIfExists(int id) {
		return this.allRides.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ride does not exist!"));
	}

	@Override
	public ReviewReturnedDTO addReview(int rideId, ReviewDTO reviewDTO, ReviewType type) {
		Ride ride = this.getRideIfExists(rideId);
		User user = this.userService.getCurrentUser();
		
		if (ride.getDriver() == null || ride.getDriver().getVehicle() == null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Vehicle does not exist!");
		
		
		
		for (Review r : ride.getReviews()) {
			if (r.getPassenger().getId() == user.getId() && r.getType() == type) {
				ride.getReviews().remove(r);
				allRides.save(ride);
				break;
			}
		}
		
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
		review.setRating(reviewDTO.getRating());
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Passenger passenger = allPassengers.findPassengerByEmail(authentication.getName()).orElse(null);
		review.setPassenger(passenger);
		
		return review;
	}
	
	@Override
	public ArrayList<CompleteRideReviewDTO> getRideReviews(int rideId) {
		Ride ride = getRideIfExists(rideId);
		ArrayList<CompleteRideReviewDTO> res = this.extractReviewFromRide(ride);
		System.out.println(res);
		return res;
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
		
		System.out.println(ride.getReviews());
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
