package com.hopin.HopIn.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hopin.HopIn.dtos.RejectedRideDTO;
import com.hopin.HopIn.services.interfaces.IRideService;

@RestController
@RequestMapping("/api/ride")
public class RideController {
	
	@Autowired
	private IRideService service;
	
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RejectedRideDTO> getRide(@PathVariable int id) {
		RejectedRideDTO ride =  service.getRide(id);
		if(ride != null) {
			return new ResponseEntity<RejectedRideDTO>(ride, HttpStatus.OK);
		}
		return new ResponseEntity<RejectedRideDTO>(HttpStatus.NOT_FOUND);
	}
	
}
