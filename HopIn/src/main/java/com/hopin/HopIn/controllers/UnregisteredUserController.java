package com.HopIn.HopIn.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.HopIn.HopIn.dtos.EstimatedRideDetailsDTO;
import com.HopIn.HopIn.dtos.UnregisteredUserRideDTO;
import com.HopIn.HopIn.services.interfaces.IUnregisteredUserService;

@RestController
@RequestMapping("/api/unregisteredUser")
public class UnregisteredUserController {
	@Autowired
	IUnregisteredUserService unregisteredUserService;
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<EstimatedRideDetailsDTO> getEstimatedRideDetails(@RequestBody UnregisteredUserRideDTO ride){
		EstimatedRideDetailsDTO details = unregisteredUserService.getEstimatedRideDetails(ride);
		return new ResponseEntity<EstimatedRideDetailsDTO>(details, HttpStatus.OK);
	}

}
