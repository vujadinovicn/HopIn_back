package com.hopin.HopIn.controllers;

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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.hopin.HopIn.dtos.PanicRideDTO;
import com.hopin.HopIn.dtos.ReasonDTO;
import com.hopin.HopIn.dtos.RideDTO;
import com.hopin.HopIn.dtos.RideReturnedDTO;
import com.hopin.HopIn.dtos.UnregisteredRideSuggestionDTO;
import com.hopin.HopIn.dtos.UserInPanicRideDTO;
import com.hopin.HopIn.enums.RideStatus;
import com.hopin.HopIn.exceptions.NoActiveDriverException;
import com.hopin.HopIn.exceptions.NoAvailableDriversException;
import com.hopin.HopIn.exceptions.NoDriverWithAppropriateVehicleForRideException;
import com.hopin.HopIn.exceptions.PassengerAlreadyInRideException;
import com.hopin.HopIn.services.interfaces.IRideService;
import com.hopin.HopIn.validations.ExceptionDTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

@Validated
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
			ExceptionDTO ex = new ExceptionDTO("There are no any active drivers!");
			return new ResponseEntity<ExceptionDTO>(ex, HttpStatus.BAD_REQUEST);
		} catch (NoDriverWithAppropriateVehicleForRideException e) {
			ExceptionDTO ex = new ExceptionDTO("There are no drivers with requested vehicle!");
			return new ResponseEntity<ExceptionDTO>(ex, HttpStatus.BAD_REQUEST);
		} catch (PassengerAlreadyInRideException e) {
			ExceptionDTO ex = new ExceptionDTO("Passenger already in drive!");
			return new ResponseEntity<ExceptionDTO>(ex, HttpStatus.BAD_REQUEST);
		} catch (NoAvailableDriversException e) {
				ExceptionDTO ex = new ExceptionDTO("No available drivers!");
				return new ResponseEntity<ExceptionDTO>(ex, HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RideReturnedDTO> getRide(@PathVariable int id) {
		RideReturnedDTO ride = service.getRide(id);
		if (ride != null) {
			return new ResponseEntity<RideReturnedDTO>(ride, HttpStatus.OK);
		}
		return new ResponseEntity<RideReturnedDTO>(HttpStatus.NOT_FOUND);
	}

	@GetMapping(value = "/driver/{driverId}/active", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RideReturnedDTO> getActiveRideForDriver(@PathVariable int driverId) {
		RideReturnedDTO activeRide = service.getActiveRideForDriver(driverId);
		if (activeRide != null) {
			return new ResponseEntity<RideReturnedDTO>(activeRide, HttpStatus.OK);
		}
		return new ResponseEntity<RideReturnedDTO>(HttpStatus.NOT_FOUND);
	}

	@GetMapping(value = "/passenger/{passengerId}/active", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RideReturnedDTO> getActiveRideForPassenger(@PathVariable int passengerId) {
		RideReturnedDTO activeRide = service.getActiveRideForPassenger(passengerId);
		if (activeRide != null) {
			return new ResponseEntity<RideReturnedDTO>(activeRide, HttpStatus.OK);
		}
		return new ResponseEntity<RideReturnedDTO>(HttpStatus.NOT_FOUND);
	}

	@PutMapping(value = "/{id}/withdraw")
	public ResponseEntity<?> cancelRide(
			@PathVariable @Min(value = 0, message = "Field id must be greater than 0.") int id) {
		try {
			RideReturnedDTO ride = service.cancelRide(id);
			return new ResponseEntity<RideReturnedDTO>(ride, HttpStatus.OK);
		} catch (ResponseStatusException ex) {
			if (ex.getStatusCode() == HttpStatus.BAD_REQUEST) {
				return new ResponseEntity<ExceptionDTO>(new ExceptionDTO(ex.getReason()), HttpStatus.BAD_REQUEST);
			} else {
				return new ResponseEntity<String>(ex.getReason(), HttpStatus.NOT_FOUND);
			}
		}
	}

	@PutMapping(value = "/{id}/panic", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> panicRide(
			@PathVariable @Min(value = 0, message = "Field id must be greater than 0.") int id,
			@Valid @RequestBody ReasonDTO dto) {
		PanicRideDTO ride = service.panicRide(id, dto);
		if (ride == null) {
			return new ResponseEntity<String>("Ride does not exist!", HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<PanicRideDTO>(ride, HttpStatus.OK);
	}

	@PutMapping(value = "/{id}/accept", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> acceptRide(
			@PathVariable @Min(value = 0, message = "Field id must be greater than 0.") int id) {
		try {
			RideReturnedDTO ride = service.acceptRide(id);
			return new ResponseEntity<RideReturnedDTO>(ride, HttpStatus.OK);
		} catch (ResponseStatusException ex) {
			if (ex.getStatusCode() == HttpStatus.BAD_REQUEST) {
				return new ResponseEntity<ExceptionDTO>(new ExceptionDTO(ex.getReason()), HttpStatus.BAD_REQUEST);
			} else {
				return new ResponseEntity<String>(ex.getReason(), HttpStatus.NOT_FOUND);
			}
		}
	}

	@PutMapping(value = "/{id}/start", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> startRide(
			@PathVariable @Min(value = 0, message = "Field id must be greater than 0.") int id) {
		try {
			RideReturnedDTO ride = service.startRide(id);
			return new ResponseEntity<RideReturnedDTO>(ride, HttpStatus.OK);
		} catch (ResponseStatusException ex) {
			if (ex.getStatusCode() == HttpStatus.BAD_REQUEST) {
				return new ResponseEntity<ExceptionDTO>(new ExceptionDTO(ex.getReason()), HttpStatus.BAD_REQUEST);
			} else {
				return new ResponseEntity<String>(ex.getReason(), HttpStatus.NOT_FOUND);
			}
		}

	}

	@PutMapping(value = "/{id}/end", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> endRide(
			@PathVariable @Min(value = 0, message = "Field id must be greater than 0.") int id) {
		try {
			RideReturnedDTO ride = service.finishRide(id);
			return new ResponseEntity<RideReturnedDTO>(ride, HttpStatus.OK);
		} catch (ResponseStatusException ex) {
			if (ex.getStatusCode() == HttpStatus.BAD_REQUEST) {
				return new ResponseEntity<ExceptionDTO>(new ExceptionDTO(ex.getReason()), HttpStatus.BAD_REQUEST);
			} else {
				return new ResponseEntity<String>(ex.getReason(), HttpStatus.NOT_FOUND);
			}
		}
	}

	@PutMapping(value = "/{id}/cancel", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> rejectRide(
			@PathVariable @Min(value = 0, message = "Field id must be greater than 0.") int id,
			@Valid @RequestBody ReasonDTO dto) {
		try {
			RideReturnedDTO ride = service.rejectRide(id, dto);
			return new ResponseEntity<RideReturnedDTO>(ride, HttpStatus.OK);
		} catch (ResponseStatusException ex) {
			if (ex.getStatusCode() == HttpStatus.BAD_REQUEST) {
				return new ResponseEntity<ExceptionDTO>(new ExceptionDTO(ex.getReason()), HttpStatus.BAD_REQUEST);
			} else {
				return new ResponseEntity<String>(ex.getReason(), HttpStatus.NOT_FOUND);
			}
		}
	}

	@PostMapping(value = "/price", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Double> getRideSugestionPrice(@RequestBody UnregisteredRideSuggestionDTO dto) {
		return new ResponseEntity<Double>(service.getRideSugestionPrice(dto), HttpStatus.OK);
	}

}
