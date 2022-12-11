package com.hopin.HopIn.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;

import com.hopin.HopIn.dtos.LocationNoIdDTO;
import com.hopin.HopIn.services.interfaces.IVehicleService;

@RestController
@RequestMapping("/api/vehicle")
public class VehicleController {
	
	@Autowired
	private IVehicleService service;
	
	@PutMapping(value="/{id}/location", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE) 
	public ResponseEntity<LocationNoIdDTO> updateLocation(@PathVariable("id") int vehicleId, @RequestBody LocationNoIdDTO newLocation) {
		if (service.updateLocation(vehicleId, newLocation))
			return new ResponseEntity<LocationNoIdDTO>(HttpStatus.NO_CONTENT);
		// for now
		return new ResponseEntity<LocationNoIdDTO>(HttpStatus.NO_CONTENT);
	}

}
