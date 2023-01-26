package com.hopin.HopIn.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hopin.HopIn.dtos.LocationNoIdDTO;
import com.hopin.HopIn.exceptions.VehicleNotFoundException;
import com.hopin.HopIn.services.interfaces.IVehicleService;
import com.hopin.HopIn.validations.ExceptionDTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

@RestController
@RequestMapping("/api/vehicle")
public class VehicleController {
	
	@Autowired
	private IVehicleService service;
	
	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;
	
	@PutMapping(value="/{id}/location", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE) 
	@PreAuthorize("hasRole('DRIVER')")  
	public ResponseEntity<?> updateLocation(@PathVariable("id") @Min(value = 0, message = "Field id must be greater than 0.") int vehicleId, @Valid @RequestBody LocationNoIdDTO newLocation) {
		try {
			System.out.println("uslo mi u guzu");
			ObjectMapper mapper = new ObjectMapper();       
			mapper.findAndRegisterModules();
			// Java object to JSON string 
			String jsonString = mapper.writeValueAsString(this.service.updateLocation(vehicleId, newLocation));
			this.simpMessagingTemplate.convertAndSend("/topic/map-updates/update-vehicle-position", jsonString);
			return new ResponseEntity<String>("Coordinates successfully updated!", HttpStatus.NO_CONTENT);
		} catch (VehicleNotFoundException e){
			this.simpMessagingTemplate.convertAndSend("/topic/map-updates/update-vehicle-position", newLocation.getAddress());
			return new ResponseEntity<String>("Vehicle does not exist!", HttpStatus.NOT_FOUND);	
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<ExceptionDTO>(HttpStatus.BAD_REQUEST);
		} 
	}

}
