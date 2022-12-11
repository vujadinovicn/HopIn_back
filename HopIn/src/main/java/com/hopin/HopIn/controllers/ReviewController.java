package com.hopin.HopIn.controllers;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.hopin.HopIn.dtos.AllReviewsReturnedDTO;
import com.hopin.HopIn.dtos.AllRideReviewsDTO;
import com.hopin.HopIn.dtos.AllUsersDTO;
import com.hopin.HopIn.dtos.CompleteRideReviewDTO;
import com.hopin.HopIn.dtos.ReviewDTO;
import com.hopin.HopIn.dtos.ReviewReturnedDTO;
import com.hopin.HopIn.services.interfaces.IReviewService;
import com.hopin.HopIn.services.interfaces.IRideService;

@RestController
@RequestMapping("/api/review")
public class ReviewController {
	
	@Autowired
	private IReviewService reviewService;
	
	@PostMapping(value="{rideId}/vehicle/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ReviewReturnedDTO> addVehicleReview(@PathVariable int rideId, @PathVariable int id, @RequestBody ReviewDTO review) {
		return new ResponseEntity<ReviewReturnedDTO>( reviewService.addVehicleReview(rideId, id, review), HttpStatus.OK);
	}
	
	@PostMapping(value="{driverId}/driver/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ReviewReturnedDTO> addDriverReview(@PathVariable int driverId, @PathVariable int id, @RequestBody ReviewDTO review) {
		return new ResponseEntity<ReviewReturnedDTO>(reviewService.addVehicleReview(driverId, id, review), HttpStatus.OK);
	}
	
	@GetMapping(value="/vehicle/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AllReviewsReturnedDTO> getVehicleReviews(@PathVariable int id){
		return new ResponseEntity<AllReviewsReturnedDTO>(reviewService.getVehicleReviews(id), HttpStatus.OK);
	}
	
	@GetMapping(value="/driver/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AllReviewsReturnedDTO> getDriverReviews(@PathVariable int id){
		return new ResponseEntity<AllReviewsReturnedDTO>(reviewService.getDriverReviews(id), HttpStatus.OK);
	}
	
	@GetMapping(value="/{rideId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ArrayList<CompleteRideReviewDTO>> getRideReviews(@PathVariable int rideId){
		return new ResponseEntity<ArrayList<CompleteRideReviewDTO>>(reviewService.getRideReviews(rideId), HttpStatus.OK);
	}
}
