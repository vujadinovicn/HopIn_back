package com.hopin.HopIn.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hopin.HopIn.dtos.RejectedRideDTO;
import com.hopin.HopIn.dtos.RideDTO;
import com.hopin.HopIn.dtos.RideReturnedDTO;
import com.hopin.HopIn.services.interfaces.IDriverService;
import com.hopin.HopIn.services.interfaces.IPassengerService;
import com.hopin.HopIn.services.interfaces.IRideService;

@RestController
@RequestMapping("/api/ride")
public class RideController {
	
	@Autowired
	private IRideService rideService;
	
	@Autowired
	private IPassengerService passengerService;
	
	@Autowired
	private IDriverService driverSrevice;
	
	
	/*
	 * @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE) public
	 * ResponseEntity<RideReturnedDTO> create(@RequestBody RideDTO dto) {
	 * 
	 * }
	 */
	
	
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RejectedRideDTO> getRide(@PathVariable int id) {
		RejectedRideDTO ride =  rideService.getRide(id);
		if(ride != null) {
			return new ResponseEntity<RejectedRideDTO>(ride, HttpStatus.OK);
		}
		return new ResponseEntity<RejectedRideDTO>(HttpStatus.NOT_FOUND);
	}
	
}
