package com.hopin.HopIn.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hopin.HopIn.dtos.PanicRideDTO;
import com.hopin.HopIn.dtos.ReasonDTO;
import com.hopin.HopIn.dtos.RideDTO;
import com.hopin.HopIn.dtos.RideReturnedDTO;
import com.hopin.HopIn.dtos.RideReturnedWithRejectionDTO;
import com.hopin.HopIn.dtos.UnregisteredRideSuggestionDTO;
import com.hopin.HopIn.dtos.UserInPanicRideDTO;
import com.hopin.HopIn.enums.RideStatus;
import com.hopin.HopIn.exceptions.NoActiveDriverException;
import com.hopin.HopIn.services.interfaces.IRideService;
import com.hopin.HopIn.validations.ExceptionDTO;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/ride")
public class RideController {

	@Autowired
	private IRideService service;

	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> add(@RequestBody RideDTO dto) {
		try {
			return new ResponseEntity<RideReturnedDTO>(service.add(dto), HttpStatus.OK);
		} catch (NoActiveDriverException e) {
			ExceptionDTO ex = new ExceptionDTO("There are not any active drivers!");
			return new ResponseEntity<ExceptionDTO>(ex, HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RideReturnedWithRejectionDTO> getRide(@PathVariable int id) {
		RideReturnedWithRejectionDTO ride = service.getRide(id);
		if(ride != null) {
			return new ResponseEntity<RideReturnedWithRejectionDTO>(ride, HttpStatus.OK);
		}
		return new ResponseEntity<RideReturnedWithRejectionDTO>(HttpStatus.NOT_FOUND);
	}
	
	  
	@GetMapping(value = "/driver/{driverId}/active", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RideReturnedDTO> getActiveRideForDriver(@PathVariable int driverId) {
		RideReturnedDTO activeRide = service.getActiveRideForDriver(driverId);
		if(activeRide != null) {
			return new ResponseEntity<RideReturnedDTO>(activeRide, HttpStatus.OK);
		}
		return new ResponseEntity<RideReturnedDTO>(HttpStatus.NOT_FOUND);
	}
	
	
	@GetMapping(value = "/passenger/{passengerId}/active", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RideReturnedDTO> getActiveRideForPassenger(@PathVariable int passengerId) {
		RideReturnedDTO activeRide = service.getActiveRideForPassenger(passengerId);
		if(activeRide != null) {
			return new ResponseEntity<RideReturnedDTO>(activeRide, HttpStatus.OK);
		}
		return new ResponseEntity<RideReturnedDTO>(HttpStatus.NOT_FOUND);
	}
	
	
	@PutMapping(value = "/{id}/withdraw")
	public ResponseEntity<RideReturnedDTO> cancelRide(@PathVariable int id) {
		RideReturnedDTO ride = service.cancelRide(id);
		if(ride != null) {
			return new ResponseEntity<RideReturnedDTO>(ride, HttpStatus.OK);
		}
		return new ResponseEntity<RideReturnedDTO>(HttpStatus.NOT_FOUND);
	}
	
	
	@PutMapping(value = "/{id}/panic", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PanicRideDTO> panicRide(@PathVariable int id, @RequestBody ReasonDTO dto) {
		PanicRideDTO ride = service.panicRide(id, dto);
		if (ride != null) {
//			TODO: add getting user from other service
			ride.setUser(new UserInPanicRideDTO("Pera", "Perić", "+381123123", "pera.peric@email.com", "Bulevar Oslobodjenja 74", "U3dhZ2dlciByb2Nrcw=="));
			return new ResponseEntity<PanicRideDTO>(ride, HttpStatus.OK);
		}
		return new ResponseEntity<PanicRideDTO>(HttpStatus.NOT_FOUND);
	}
	
	
	@PutMapping(value = "/{id}/accept", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RideReturnedDTO> acceptRide(@PathVariable int id) {
		RideReturnedDTO ride = service.changeRideStatus(id, RideStatus.ACCEPTED);
		if(ride != null) {
			return new ResponseEntity<RideReturnedDTO>(ride, HttpStatus.OK);
		}
		return new ResponseEntity<RideReturnedDTO>(HttpStatus.NOT_FOUND);
	}
	
	
	@PutMapping(value = "/{id}/end", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RideReturnedDTO> endRide(@PathVariable int id) {
		RideReturnedDTO ride = service.changeRideStatus(id, RideStatus.FINISHED);
		if(ride != null) {
			return new ResponseEntity<RideReturnedDTO>(ride, HttpStatus.OK);
		}
		return new ResponseEntity<RideReturnedDTO>(HttpStatus.NOT_FOUND);
	}
	
	
	@PutMapping(value = "/{id}/cancel", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RideReturnedDTO> rejectRide(@PathVariable int id, @RequestBody ReasonDTO dto) {
		RideReturnedDTO ride = service.rejectRide(id, dto);
		if(ride != null) {
			return new ResponseEntity<RideReturnedDTO>(ride, HttpStatus.OK);
		}
		return new ResponseEntity<RideReturnedDTO>(HttpStatus.NOT_FOUND);
	}
	
	@PostMapping(value = "/price", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Double> getRideSugestionPrice(@RequestBody UnregisteredRideSuggestionDTO dto) {
		return new ResponseEntity<Double>(service.getRideSugestionPrice(dto), HttpStatus.OK);
	}
	

	
	
}
