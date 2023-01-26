package com.hopin.HopIn.controllers;

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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.hopin.HopIn.dtos.AllPassengerRidesDTO;
import com.hopin.HopIn.dtos.AllUsersDTO;
import com.hopin.HopIn.dtos.PassengerRideDTO;
import com.hopin.HopIn.dtos.RideForReportDTO;
import com.hopin.HopIn.dtos.RouteDTO;
import com.hopin.HopIn.dtos.UserDTO;
import com.hopin.HopIn.dtos.UserReturnedDTO;
import com.hopin.HopIn.exceptions.EmailAlreadyInUseException;
import com.hopin.HopIn.exceptions.UserNotFoundException;
import com.hopin.HopIn.services.interfaces.IPassengerService;
import com.hopin.HopIn.services.interfaces.IRideService;
import com.hopin.HopIn.validations.ExceptionDTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.websocket.server.PathParam;

@Validated
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/passenger")
public class PassengerController {
	
	@Autowired
	private IPassengerService passengerService;

	@Autowired
	private IRideService rideService;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('PASSENGER')" + " || " +"hasRole('ADMIN')")
	public ResponseEntity<AllUsersDTO> getPassengers() {
		AllUsersDTO passengers = this.passengerService.getAll();
		return new ResponseEntity<AllUsersDTO>(passengers, HttpStatus.OK);
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> insertPassenger(@Valid @RequestBody UserDTO dto) {
		System.out.println(dto);
		UserReturnedDTO passenger = passengerService.insert(dto);
		if (passenger == null) {
			return new ResponseEntity<ExceptionDTO>(new ExceptionDTO("User with that email already exists!"), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<UserReturnedDTO>(passenger, HttpStatus.OK);
	}


	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('PASSENGER')" + " || " +"hasRole('ADMIN')" + " || " +"hasRole('DRIVER')")
	public ResponseEntity<?> getPassenger(@PathVariable @Min(value = 0, message = "Field id must be greater than 0.") int id) {
		try {
			return new ResponseEntity<UserReturnedDTO>(passengerService.getById(id), HttpStatus.OK);
		} catch (ResponseStatusException ex) {
			return new ResponseEntity<String>("Passenger does not exist!", HttpStatus.NOT_FOUND);			
		}
	}

	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping(value = "/email/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserReturnedDTO> getPassenger(@PathVariable String email) {
		UserReturnedDTO passenger = passengerService.getByEmail(email);
		if (passenger != null) {
			return new ResponseEntity<UserReturnedDTO>(passenger, HttpStatus.OK);
		}
		return new ResponseEntity<UserReturnedDTO>(HttpStatus.NOT_FOUND);
	}

	@CrossOrigin(origins = "http://localhost:4200")
	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('PASSENGER')")
	public ResponseEntity<?> update(@Valid @RequestBody UserDTO dto, @PathVariable @Min(value = 0, message = "Field id must be greater than 0.") int id) {
		try {
			UserReturnedDTO passenger = passengerService.update(id, dto);
			return new ResponseEntity<UserReturnedDTO>(passenger, HttpStatus.OK);
		} catch (UserNotFoundException e) {
			return new ResponseEntity<String>("Passenger does not exist!", HttpStatus.NOT_FOUND);
		} catch (EmailAlreadyInUseException e) {
			return new ResponseEntity<String>("Email already in use!", HttpStatus.BAD_REQUEST);
		}
	}

	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping(value = "{id}/ride", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('PASSENGER')")
	public ResponseEntity<?> getAllRidesPaginated(@PathVariable int id, @RequestParam int page,
			@RequestParam int size, @RequestParam(required = false) String sort, @RequestParam(required = false) String from, @RequestParam(required = false) String to) {
		try {
			AllPassengerRidesDTO rides = this.rideService.getAllPassengerRidesPaginated(id, page, size, sort, from, to);
			for (PassengerRideDTO ride: rides.getResults()) {
				System.out.println(ride);
			}
			return new ResponseEntity<AllPassengerRidesDTO>(
					rides , HttpStatus.OK);
		} catch (UserNotFoundException e) {
			return new ResponseEntity<String>("Passenger does not exist!", HttpStatus.NOT_FOUND);
		}
		
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping(value = "{id}/all/rides", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ADMIN')" + " || " + "hasRole('PASSENGER')")
	public ResponseEntity<?> getAllRides(@PathVariable int id) {
		try {
			AllPassengerRidesDTO rides = this.rideService.getAllPassengerRides(id);
			for (PassengerRideDTO ride: rides.getResults()) {
				System.out.println(ride);
			}
			return new ResponseEntity<AllPassengerRidesDTO>( 
					rides , HttpStatus.OK);
		} catch (UserNotFoundException e) {
			return new ResponseEntity<String>("Passenger does not exist!", HttpStatus.NOT_FOUND);
		}
		
	}

	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping(value = "/{id}/favouriteRoutes", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<RouteDTO>> getFavouriteRides(@PathVariable int id) {
		System.out.println("FAVORUTIES");
		List<RouteDTO> favouriteRoutes = passengerService.getFavouriteRoutes(id);
		if (favouriteRoutes != null && favouriteRoutes.size() != 0) {
			return new ResponseEntity<List<RouteDTO>>(favouriteRoutes, HttpStatus.OK);
		}
		return new ResponseEntity<List<RouteDTO>>(HttpStatus.NOT_FOUND);
	}

	@CrossOrigin(origins = "http://localhost:4200")
//	@PreAuthorize("hasRole('PASSENGER')")
	@GetMapping(value = "{id}/ride/date", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<RideForReportDTO>> getAllRidesBetweenDates(@PathVariable int id,
			@RequestParam String from, @RequestParam String to) {
		return new ResponseEntity<List<RideForReportDTO>>(
				this.rideService.getAllPassengerRidesBetweenDates(id, from, to), HttpStatus.OK);
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
	@PostMapping(value = "{passengerId}/return/route")
	public ResponseEntity<Void> returnFavouriteRoute(@PathVariable int passengerId, @RequestParam int routeId) {
		if (passengerService.returnFavouriteRoute(passengerId, routeId)) {
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
		return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping(value = "{passengerId}/add/route")
	public ResponseEntity<Void> addFavouriteRoute(@PathVariable int passengerId, @RequestBody RouteDTO route) {
		if (passengerService.addFavouriteRoute(passengerId, route)) {
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
		return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
	}
	
	@GetMapping(value = "activate/{activationId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> verifyRegistration(@PathVariable("activationId") String verificationCode) {
		try {
			Boolean verified = this.passengerService.verifyRegistration(verificationCode);
			if (verified) {
				return new ResponseEntity<ExceptionDTO>(new ExceptionDTO("Successful account activation!"), HttpStatus.OK);
			} else {
				return new ResponseEntity<ExceptionDTO>(new ExceptionDTO("Activation expired. Register again!"), HttpStatus.BAD_REQUEST);
			}
		} catch (ResponseStatusException e) {
			return new ResponseEntity<String>(e.getReason(), HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping(value = "activate/resend")
	public ResponseEntity<Void> resendVerificationMail(@RequestParam("code") String verificationCode) {
		Boolean ret = this.passengerService.resendVerificationMail(verificationCode);
		if (ret) {
			return new ResponseEntity<Void>(HttpStatus.OK);
		} else {
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
	}

}
