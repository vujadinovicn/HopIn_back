package com.hopin.HopIn.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hopin.HopIn.dtos.AllPassengerRidesDTO;
import com.hopin.HopIn.dtos.AllUsersDTO;
import com.hopin.HopIn.dtos.RideForReportDTO;
import com.hopin.HopIn.dtos.RouteDTO;
import com.hopin.HopIn.dtos.UserDTO;
import com.hopin.HopIn.dtos.UserReturnedDTO;
import com.hopin.HopIn.services.interfaces.IPassengerService;
import com.hopin.HopIn.services.interfaces.IRideService;

@Validated
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/api/passenger")
public class PassengerController {
	
	@Autowired
	private IPassengerService passengerService;
	
	@Autowired
	private IRideService rideService;
	
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AllUsersDTO> getPassengers() {
		AllUsersDTO passengers = this.passengerService.getAll();
		return new ResponseEntity<AllUsersDTO>(passengers, HttpStatus.OK);
	}
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserReturnedDTO> insertPassenger(@RequestBody UserDTO dto) {
		UserReturnedDTO passenger = passengerService.insert(dto);
		return new ResponseEntity<UserReturnedDTO>(passenger, HttpStatus.OK);
	}
	
	@GetMapping(value = "/activate/{activationId}")
	public ResponseEntity<Void> activatePassenger(@PathVariable int activationId) {
		if(passengerService.Activate(activationId)) {
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
		return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
	}
	
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserReturnedDTO> getPassenger(@PathVariable int id) {
		UserReturnedDTO passenger = passengerService.getById(id);
		if(passenger != null) {
			passengerService.getFavouriteRoutes(id);
			return new ResponseEntity<UserReturnedDTO>(passenger, HttpStatus.OK);
		}
		return new ResponseEntity<UserReturnedDTO>(HttpStatus.NOT_FOUND);
	}
	
	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserReturnedDTO> updatePassenger(@RequestBody UserDTO dto, @PathVariable int id) {
		UserReturnedDTO passenger = passengerService.update(id, dto);
		if(passenger != null) {
			return new ResponseEntity<UserReturnedDTO>(passenger, HttpStatus.OK);
		}
		return new ResponseEntity<UserReturnedDTO>(HttpStatus.NOT_FOUND);
	}
	
	@GetMapping(value = "{id}/ride", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AllPassengerRidesDTO> getAllRides(@PathVariable int id, @RequestParam int page, @RequestParam int size, @RequestParam String sort, @RequestParam String from, @RequestParam String to) {
		return new ResponseEntity<AllPassengerRidesDTO>(this.rideService.getAllPassengerRides(id, page, size, sort, from, to), HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping(value = "/{id}/favouriteRoutes", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<RouteDTO>> getFavouriteRides(@PathVariable int id) {
		List<RouteDTO> favouriteRoutes = passengerService.getFavouriteRoutes(id);
		if(favouriteRoutes != null && favouriteRoutes.size() != 0) {
			return new ResponseEntity<List<RouteDTO>>(favouriteRoutes, HttpStatus.OK);
		}
		return new ResponseEntity<List<RouteDTO>>(HttpStatus.NOT_FOUND);
	}
	 	
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping(value = "{id}/ride/date", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<RideForReportDTO>> getAllRidesBetweenDates(@PathVariable int id, @RequestParam String from, @RequestParam String to) {
		return new ResponseEntity<List<RideForReportDTO>>(this.rideService.getAllPassengerRidesBetweenDates(id, from, to), HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping(value = "{passengerId}/remove/route")
	public ResponseEntity<Void> removeFavouriteRoute(@PathVariable int passengerId, @RequestParam int routeId) {
		if (passengerService.removeFavouriteRoute(passengerId, routeId)) {
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
		return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping(value = "{passengerId}/add/route")
	public ResponseEntity<Void> addFavouriteRoute(@PathVariable int passengerId, @RequestParam int routeId) {
		if (passengerService.addFavouriteRoute(passengerId, routeId)) {
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
		return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
	}
	

}
