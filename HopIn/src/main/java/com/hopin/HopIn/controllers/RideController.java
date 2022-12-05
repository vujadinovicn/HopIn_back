package com.hopin.HopIn.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
import com.hopin.HopIn.dtos.UserInPanicRideDTO;
import com.hopin.HopIn.enums.RideStatus;
import com.hopin.HopIn.services.interfaces.IRideService;

@RestController
@RequestMapping("/api/ride")
public class RideController {

	@Autowired
	private IRideService rideService;

	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RideReturnedDTO> create(@RequestBody RideDTO dto) {
		return new ResponseEntity<RideReturnedDTO>(rideService.create(dto), HttpStatus.OK);
	}
	
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RideReturnedWithRejectionDTO> getRide(@PathVariable int id) {
		RideReturnedWithRejectionDTO ride = rideService.getRide(id);
		if(ride != null) {
			return new ResponseEntity<RideReturnedWithRejectionDTO>(ride, HttpStatus.OK);
		}
		return new ResponseEntity<RideReturnedWithRejectionDTO>(HttpStatus.NOT_FOUND);
	}
	
	  
	@GetMapping(value = "/active/{driverId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RideReturnedDTO> getActiveRideForDriver(@PathVariable int driverId) {
		RideReturnedDTO activeRide = rideService.getActiveRideForDriver(driverId);
		if(activeRide != null) {
			return new ResponseEntity<RideReturnedDTO>(activeRide, HttpStatus.OK);
		}
		return new ResponseEntity<RideReturnedDTO>(HttpStatus.NOT_FOUND);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<String> cancelRide(@PathVariable int id) {
		if(rideService.cancelRide(id)) {
			return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
	}
	
	
	@PutMapping(value = "/{id}/panic", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PanicRideDTO> panicRide(@PathVariable int id, @RequestBody ReasonDTO dto) {
		PanicRideDTO ride = rideService.panicRide(id, dto);
		if (ride != null) {
//			TODO: add getting user from other service
			ride.setUser(new UserInPanicRideDTO("Pera", "Perić", "U3dhZ2dlciByb2Nrcw==", "+381123123", "pera.peric@email.com", "Bulevar Oslobodjenja 74"));
			return new ResponseEntity<PanicRideDTO>(ride, HttpStatus.OK);
		}
		return new ResponseEntity<PanicRideDTO>(HttpStatus.NOT_FOUND);
	}
	
	
	@PutMapping(value = "/{id}/accept", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RideReturnedDTO> acceptRide(@PathVariable int id) {
		RideReturnedDTO ride = rideService.changeRideStatus(id, RideStatus.ACCEPTED);
		if(ride != null) {
			return new ResponseEntity<RideReturnedDTO>(ride, HttpStatus.OK);
		}
		return new ResponseEntity<RideReturnedDTO>(HttpStatus.NOT_FOUND);
	}
	
	
	@PutMapping(value = "/{id}/end", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RideReturnedDTO> endRide(@PathVariable int id) {
		RideReturnedDTO ride = rideService.changeRideStatus(id, RideStatus.FINISHED);
		if(ride != null) {
			return new ResponseEntity<RideReturnedDTO>(ride, HttpStatus.OK);
		}
		return new ResponseEntity<RideReturnedDTO>(HttpStatus.NOT_FOUND);
	}
	
	
	@PutMapping(value = "/{id}/cancel", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RideReturnedDTO> rejectRide(@PathVariable int id, @RequestBody ReasonDTO dto) {
		RideReturnedDTO ride = rideService.rejectRide(id, dto);
		if(ride != null) {
			return new ResponseEntity<RideReturnedDTO>(ride, HttpStatus.OK);
		}
		return new ResponseEntity<RideReturnedDTO>(HttpStatus.NOT_FOUND);
	}
	
	
	
	
	
	
	
	
	
}
