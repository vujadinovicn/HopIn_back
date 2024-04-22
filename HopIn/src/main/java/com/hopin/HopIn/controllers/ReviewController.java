package com.hopin.HopIn.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hopin.HopIn.dtos.AllReviewsReturnedDTO;
import com.hopin.HopIn.dtos.AllRideReviewsDTO;
import com.hopin.HopIn.dtos.AllUsersDTO;
import com.hopin.HopIn.dtos.CompleteRideReviewDTO;
import com.hopin.HopIn.dtos.ReviewDTO;
import com.hopin.HopIn.dtos.ReviewReturnedDTO;
import com.hopin.HopIn.dtos.RideReturnedDTO;
import com.hopin.HopIn.enums.ReviewType;
import com.hopin.HopIn.exceptions.UserNotFoundException;
import com.hopin.HopIn.services.interfaces.IReviewService;
import com.hopin.HopIn.services.interfaces.IRideService;
import com.hopin.HopIn.validations.ExceptionDTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

@Validated
@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RequestMapping("/api/review")
public class ReviewController {
	
	@Autowired
	private IReviewService reviewService;      
	

	@PostMapping(value="/{rideId}/complete-review", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('PASSENGER')")
	public ResponseEntity<?> addReview(@PathVariable @Min(value = 0, message = "Field rideId must be greater than 0.") int rideId, @Valid @RequestBody List<ReviewDTO> reviews) {
		try {
			reviewService.addCompleteReview(rideId, reviews);
			ObjectMapper mapper = new ObjectMapper();       
			mapper.findAndRegisterModules();
			// Java object to JSON string 
			String jsonString = mapper.writeValueAsString("Reviews successfully added!");
			return new ResponseEntity<String>(jsonString, HttpStatus.OK);
		} catch (ResponseStatusException ex) {
			ex.printStackTrace();
			return new ResponseEntity<String>(ex.getReason(), HttpStatus.NOT_FOUND);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} 
	} 
	
	
	@PostMapping(value="{rideId}/vehicle", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> addVehicleReview(@PathVariable @Min(value = 0, message = "Field rideId must be greater than 0.") int rideId, @Valid @RequestBody ReviewDTO review) {
		try {
			return new ResponseEntity<ReviewReturnedDTO>( reviewService.addReview(rideId, review, ReviewType.VEHICLE), HttpStatus.OK);
		} catch (ResponseStatusException ex) {
			return new ResponseEntity<String>(ex.getReason(), HttpStatus.NOT_FOUND);
		}	
	}
	
	@PostMapping(value="{rideId}/driver", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> addDriverReview(@PathVariable @Min(value = 0, message = "Field rideId must be greater than 0.") int rideId, @Valid @RequestBody ReviewDTO review) {
		try {
			return new ResponseEntity<ReviewReturnedDTO>(reviewService.addReview(rideId, review, ReviewType.DRIVER), HttpStatus.OK);
		} catch (ResponseStatusException ex) {
			return new ResponseEntity<String>(ex.getReason(), HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping(value="/vehicle/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getVehicleReviews(@PathVariable @Min(value = 0, message = "Field rideId must be greater than 0.") int id){
		try {
			return new ResponseEntity<AllReviewsReturnedDTO>(reviewService.getVehicleReviews(id), HttpStatus.OK);
		} catch (ResponseStatusException ex) {
			return new ResponseEntity<String>(ex.getReason(), HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping(value="/driver/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getDriverReviews(@PathVariable int id){
		try {
			return new ResponseEntity<AllReviewsReturnedDTO>(reviewService.getDriverReviews(id), HttpStatus.OK);
		} catch (ResponseStatusException ex) {
			return new ResponseEntity<String>(ex.getReason(), HttpStatus.NOT_FOUND);
		} catch (UserNotFoundException ex) {
			return new ResponseEntity<String>("Driver does not exist!", HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping(value="/{rideId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getRideReviews(@PathVariable @Min(value = 0, message = "Field id must be greater than 0.") int rideId){
		try {
			return new ResponseEntity<ArrayList<CompleteRideReviewDTO>>(reviewService.getRideReviews(rideId), HttpStatus.OK);
		} catch (ResponseStatusException ex) {
			return new ResponseEntity<String>(ex.getReason(), HttpStatus.NOT_FOUND);
		}	
	}
}
