package com.HopIn.HopIn.controllers;

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

import com.HopIn.HopIn.dtos.AllUsersDTO;
import com.HopIn.HopIn.dtos.ReviewDTO;
import com.HopIn.HopIn.dtos.ReviewReturnedDTO;
import com.HopIn.HopIn.services.interfaces.IReviewService;
import com.HopIn.HopIn.services.interfaces.IRideService;

@RestController
@RequestMapping("/api/review")
public class ReviewController {
	
	@Autowired
	private IReviewService reviewService;
	
	@PostMapping(value="/{vehicleId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ReviewReturnedDTO> addVehicleReview(@PathVariable int vehicleId, @RequestBody ReviewDTO review) {
		return new ResponseEntity<ReviewReturnedDTO>( reviewService.addVehicleReview(vehicleId, review), HttpStatus.OK);
	}
	
	@PostMapping(value="/driver/{driverId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ReviewReturnedDTO> addDriverReview(@PathVariable int driverId, @RequestBody ReviewDTO review) {
		return new ResponseEntity<ReviewReturnedDTO>( reviewService.addVehicleReview(driverId, review), HttpStatus.OK);
	}
}
