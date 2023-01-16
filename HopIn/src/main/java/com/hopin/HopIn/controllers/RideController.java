package com.hopin.HopIn.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hopin.HopIn.dtos.FavoriteRideDTO;
import com.hopin.HopIn.dtos.FavoriteRideReturnedDTO;
import com.hopin.HopIn.dtos.PanicRideDTO;
import com.hopin.HopIn.dtos.ReasonDTO;
import com.hopin.HopIn.dtos.RideDTO;
import com.hopin.HopIn.dtos.RideReturnedDTO;
import com.hopin.HopIn.dtos.UnregisteredRideSuggestionDTO;
import com.hopin.HopIn.exceptions.FavoriteRideException;
import com.hopin.HopIn.exceptions.NoActiveDriverException;
import com.hopin.HopIn.exceptions.NoActivePassengerRideException;
import com.hopin.HopIn.exceptions.NoAvailableDriversException;
import com.hopin.HopIn.exceptions.NoDriverWithAppropriateVehicleForRideException;
import com.hopin.HopIn.exceptions.PassengerAlreadyInRideException;
import com.hopin.HopIn.exceptions.RideNotFoundException;
import com.hopin.HopIn.services.interfaces.IRideService;
import com.hopin.HopIn.services.interfaces.ITokenService;
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

	@Autowired
	private ITokenService tokenService;

	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> add(@RequestBody RideDTO dto) {
		System.out.println(dto.getVehicleType());
		ResponseEntity<ExceptionDTO> res;
		try {
			RideReturnedDTO ride = service.add(dto);
			ride.setScheduledTime(null);
			ObjectMapper mapper = new ObjectMapper();
			mapper.findAndRegisterModules();
			// Java object to JSON string 
			String jsonString = mapper.writeValueAsString(ride);
			System.out.println("/topic/driver/ride-offers/" + ride.getDriver().getId());
			this.simpMessagingTemplate.convertAndSend("/topic/driver/ride-offers/" + ride.getDriver().getId(),
					jsonString);
			return new ResponseEntity<RideReturnedDTO>(ride, HttpStatus.OK);
		} catch (NoActiveDriverException e) {
			ExceptionDTO ex = new ExceptionDTO("There are no any active drivers!");
			System.out.println("There are no any active drivers!");
			res = new ResponseEntity<ExceptionDTO>(ex, HttpStatus.BAD_REQUEST);
		} catch (NoDriverWithAppropriateVehicleForRideException e) {
			ExceptionDTO ex = new ExceptionDTO("There are no drivers with requested vehicle!");
			System.out.println("There are no drivers with requested vehicle!");
			res = new ResponseEntity<ExceptionDTO>(ex, HttpStatus.BAD_REQUEST);
		} catch (PassengerAlreadyInRideException e) {
			ExceptionDTO ex = new ExceptionDTO("Passenger already in drive!");
			System.out.println("Passenger already in drive!");
			res = new ResponseEntity<ExceptionDTO>(ex, HttpStatus.BAD_REQUEST);
		} catch (NoAvailableDriversException e) {
			ExceptionDTO ex = new ExceptionDTO("No available drivers!");
			System.out.println("No available drivers!");
			res = new ResponseEntity<ExceptionDTO>(ex, HttpStatus.BAD_REQUEST);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			res = new ResponseEntity<ExceptionDTO>(HttpStatus.BAD_REQUEST);
		}
		this.simpMessagingTemplate.convertAndSend("/topic/driver/ride-offer-response/" + dto.getPassengers().get(0).getId(), "noDriver");
		return res;
	}

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getRide(
			@PathVariable @Min(value = 0, message = "Field id must be greater than 0.") int id) {
		try {
			return new ResponseEntity<RideReturnedDTO>(service.getRide(id), HttpStatus.OK);
		} catch (RideNotFoundException e) {
			return new ResponseEntity<String>("Ride does not exist!", HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping(value = "/driver/{driverId}/active", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('DRIVER')")
	public ResponseEntity<?> getActiveRideForDriver(
			@PathVariable @Min(value = 0, message = "Field id must be greater than 0.") int driverId) {
		try {
			return new ResponseEntity<RideReturnedDTO>(service.getActiveRideForDriver(driverId), HttpStatus.OK);
		} catch (NoActivePassengerRideException e) {
			return new ResponseEntity<String>("Ride does not exist!", HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping(value = "/passenger/{passengerId}/active", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('PASSENGER')")
	public ResponseEntity<?> getActiveRideForPassenger(
			@PathVariable @Min(value = 0, message = "Field id must be greater than 0.") int passengerId) {
		try {
			return new ResponseEntity<RideReturnedDTO>(service.getActiveRideForPassenger(passengerId), HttpStatus.OK);
		} catch (NoActivePassengerRideException e) {
			return new ResponseEntity<String>("Ride does not exist!", HttpStatus.NOT_FOUND);
		}
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
		System.out.println("panic");
		PanicRideDTO ride = service.panicRide(id, dto);
		if (ride == null) {
			return new ResponseEntity<String>("Ride does not exist!", HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<PanicRideDTO>(ride, HttpStatus.OK);
	}

	@PutMapping(value = "/{id}/accept", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> acceptRide(
			@PathVariable @Min(value = 0, message = "Field id must be greater than 0.") int id) {
		System.out.println("ACCEPT REQ: " + id);
		try {
			RideReturnedDTO ride = service.acceptRide(id);
			this.simpMessagingTemplate.convertAndSend("/topic/ride-offer-response/" + ride.getPassengers().get(0).getId(), "true");
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
		System.out.println("DECLINE REQ: " + id);
		System.out.println("DECLINE RES: " + dto.getReason());
		try {
			RideReturnedDTO ride = service.rejectRide(id, dto);
			this.simpMessagingTemplate.convertAndSend("/topic/ride-offer-response/" + ride.getPassengers().get(0).getId(), "false");
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

	@PostMapping(value = "/favorites", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('PASSENGER')")
	public ResponseEntity<?> addFavoriteRide(@RequestBody FavoriteRideDTO dto) {
		try {
			return new ResponseEntity<FavoriteRideReturnedDTO>(this.service.insertFavoriteRide(dto), HttpStatus.OK);
		} catch (FavoriteRideException ex) {
			return new ResponseEntity<ExceptionDTO>(new ExceptionDTO("Number of favorite rides cannot exceed 10!"),
					HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping(value = "/favorites", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('PASSENGER')")
	public ResponseEntity<?> getFavoriteRides() {
		return new ResponseEntity<List<FavoriteRideReturnedDTO>>(this.service.getFavoriteRides(), HttpStatus.OK);
	}

	@DeleteMapping(value = "favorites/{id}")
	@PreAuthorize("hasRole('PASSENGER')")
	public ResponseEntity<?> deleteFavoriteRide(@PathVariable int id) {
		try {
			this.service.deleteFavoriteRide(id);
			return new ResponseEntity<String>("Successful deletion of favorite location!", HttpStatus.NO_CONTENT);
		} catch (FavoriteRideException ex) {
			return new ResponseEntity<String>("Favorite location does not exist!", HttpStatus.NOT_FOUND);
		}
	}

}
