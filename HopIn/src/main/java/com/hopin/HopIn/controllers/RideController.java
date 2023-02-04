package com.hopin.HopIn.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hopin.HopIn.dtos.FavoriteRideDTO;
import com.hopin.HopIn.dtos.FavoriteRideReturnedDTO;
import com.hopin.HopIn.dtos.PanicRideDTO;
import com.hopin.HopIn.dtos.ReasonDTO;
import com.hopin.HopIn.dtos.RideDTO;
import com.hopin.HopIn.dtos.RideForReportDTO;
import com.hopin.HopIn.dtos.RideOfferResponseDTO;
import com.hopin.HopIn.dtos.RideReturnedDTO;
import com.hopin.HopIn.dtos.UnregisteredRideSuggestionDTO;
import com.hopin.HopIn.exceptions.FavoriteRideException;
import com.hopin.HopIn.exceptions.NoActiveDriverException;
import com.hopin.HopIn.exceptions.NoActiveDriverRideException;
import com.hopin.HopIn.exceptions.NoActivePassengerRideException;
import com.hopin.HopIn.exceptions.NoAvailableDriversException;
import com.hopin.HopIn.exceptions.NoDriverWithAppropriateVehicleForRideException;
import com.hopin.HopIn.exceptions.NoRideAfterFiveHoursException;
import com.hopin.HopIn.exceptions.PassengerAlreadyInRideException;
import com.hopin.HopIn.exceptions.PassengerHasAlreadyPendingRide;
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
	@PreAuthorize("hasRole('PASSENGER')")
	public ResponseEntity<?> add(@Valid @RequestBody(required = false) RideDTO dto) {
		System.out.println(dto.getVehicleType());
		ResponseEntity<ExceptionDTO> res;

		try {
//			if (dto.getScheduledTime() != null)
//				dto.setScheduledTime(dto.getScheduledTime().plusHours(1));
			RideReturnedDTO ride = service.add(dto);
			if (ride.getScheduledTime() != null)
				ride.setScheduledTimeFormatted(ride.getScheduledTime().toString());
			System.out.println("/topic/driver/ride-offers/" + ride.getDriver().getId());
			this.simpMessagingTemplate.convertAndSend("/topic/ride-pending", ride.getDriver().getId());
			this.simpMessagingTemplate.convertAndSend("/topic/driver/ride-offers/" + ride.getDriver().getId(), ride);
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
		}  catch (PassengerHasAlreadyPendingRide e) {
			ExceptionDTO ex = new ExceptionDTO("Cannot create a ride while you have one already pending!");
			System.out.println("Cannot create a ride while you have one already pending!");
			res = new ResponseEntity<ExceptionDTO>(ex, HttpStatus.BAD_REQUEST);
		} catch (NoRideAfterFiveHoursException e) {
			ExceptionDTO ex = new ExceptionDTO("Cannot create a ride 5 hours from now!");
			System.out.println("Cannot create a ride 5 hours from now!");
			res = new ResponseEntity<ExceptionDTO>(ex, HttpStatus.BAD_REQUEST);
		}
//		this.simpMessagingTemplate.convertAndSend("/topic/driver/ride-offer-response/" + dto.getPassengers().get(0).getId(), "noDriver");
		return res;
	}

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ADMIN')" + " || " + "hasRole('DRIVER')")
	public ResponseEntity<?> getRide(
			@PathVariable @Min(value = 0, message = "Field id must be greater than 0.") int id) {
		try {
			RideReturnedDTO ride = service.getRide(id);
			System.out.println(ride);
			return new ResponseEntity<RideReturnedDTO>(ride, HttpStatus.OK);
		} catch (RideNotFoundException e) {
			return new ResponseEntity<String>("Ride does not exist", HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping(value = "/driver/{driverId}/active", produces = MediaType.APPLICATION_JSON_VALUE)
//	@PreAuthorize("hasRole('ADMIN')" + " || " + "hasRole('DRIVER')")
	public ResponseEntity<?> getActiveRideForDriver(
			@PathVariable @Min(value = 0, message = "Field id must be greater than 0.") int driverId) {
		try {
			return new ResponseEntity<RideReturnedDTO>(service.getActiveRideForDriver(driverId), HttpStatus.OK);
		} catch (NoActiveDriverRideException e) {
			return new ResponseEntity<String>("Active ride does not exist", HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping(value = "/driver/{driverId}/pending", produces = MediaType.APPLICATION_JSON_VALUE)
//	@PreAuthorize("hasRole('ADMIN')" + " || " + "hasRole('DRIVER')")
	public ResponseEntity<?> getPendingRideForDriver(
			@PathVariable @Min(value = 0, message = "Field id must be greater than 0.") int driverId) {
		try {
			return new ResponseEntity<RideReturnedDTO>(service.getPendingRideForDriver(driverId), HttpStatus.OK);
		} catch (NoActiveDriverRideException e) {
			return new ResponseEntity<String>("Active ride does not exist", HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping(value = "/scheduled-rides/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('DRIVER')" + " || " + "hasRole('PASSENGER')")
	public ResponseEntity<?> getScheduledRidesForUser(
			@PathVariable @Min(value = 0, message = "Field id must be greater than 0.") int userId) {
		List<RideReturnedDTO> rides = service.getScheduledRidesForUser(userId);
		System.out.println(rides);
		return new ResponseEntity<List<RideReturnedDTO>>(rides, HttpStatus.OK);
	}

	@GetMapping(value = "/passenger/{passengerId}/active", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> getActiveRideForPassenger(
			@PathVariable @Min(value = 0, message = "Field id must be greater than 0.") int passengerId) {
		RideReturnedDTO ride = service.getPendingRideForPassenger(passengerId);
		if (ride != null)
			return new ResponseEntity<RideReturnedDTO>(service.getPendingRideForPassenger(passengerId), HttpStatus.OK);
		else
			return new ResponseEntity<String>("Active ride does not exist", HttpStatus.NOT_FOUND);
	}

	@PutMapping(value = "/{id}/withdraw")
	@PreAuthorize("hasRole('PASSENGER')")
	public ResponseEntity<?> cancelRide(
			@PathVariable @Min(value = 0, message = "Field id must be greater than 0.") int id) {
		try {
			RideReturnedDTO ride = service.cancelRide(id);
			return new ResponseEntity<RideReturnedDTO>(ride, HttpStatus.OK);
		} catch (ResponseStatusException ex) {
			if (ex.getStatusCode() == HttpStatus.BAD_REQUEST) {
				return new ResponseEntity<ExceptionDTO>(new ExceptionDTO(ex.getReason()), HttpStatus.BAD_REQUEST);
			} else {
				return new ResponseEntity<String>("Ride does not exist!", HttpStatus.NOT_FOUND);
			}
		}
	}

	@PutMapping(value = "/{id}/panic", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('PASSENGER')" + " || " + "hasRole('DRIVER')")
	public ResponseEntity<?> panicRide(
			@PathVariable @Min(value = 0, message = "Field id must be greater than 0.") int id,
			@Valid @RequestBody(required = false) ReasonDTO dto) {
		System.out.println("PANIC " + id + " " + dto.getReason());
		PanicRideDTO ride = service.panicRide(id, dto);
		if (ride == null) {
			return new ResponseEntity<String>("Ride does not exist!", HttpStatus.NOT_FOUND);
		}
		this.simpMessagingTemplate.convertAndSend("/topic/panic", ride);
		return new ResponseEntity<PanicRideDTO>(ride, HttpStatus.OK);
	}

	@PutMapping(value = "/{id}/accept", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('DRIVER')")
	public ResponseEntity<?> acceptRide(
			@PathVariable @Min(value = 0, message = "Field id must be greater than 0.") int id) {
//		System.out.println("ACCEPT REQ: " + id);
		try {
			RideReturnedDTO ride = service.acceptRide(id);
			this.simpMessagingTemplate.convertAndSend("/topic/ride-accept", ride.getDriver().getId());
			System.out.println("/topic/ride-offer-response/" + ride.getPassengers().get(0).getId());
			this.simpMessagingTemplate.convertAndSend(
					"/topic/ride-offer-response/" + ride.getPassengers().get(0).getId(),
					new RideOfferResponseDTO(true, ride));
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
	@PreAuthorize("hasRole('DRIVER')")
	public ResponseEntity<?> startRide(
			@PathVariable @Min(value = 0, message = "Field id must be greater than 0.") int id) {
		try {
			RideReturnedDTO ride = service.startRide(id);
			System.out.println("START");
			this.simpMessagingTemplate.convertAndSend("/topic/ride-start-finish/" + ride.getDriver().getId(), ride);
			return new ResponseEntity<RideReturnedDTO>(ride, HttpStatus.OK);
		} catch (ResponseStatusException ex) {
			if (ex.getStatusCode() == HttpStatus.BAD_REQUEST) {
				return new ResponseEntity<ExceptionDTO>(new ExceptionDTO(ex.getReason()), HttpStatus.BAD_REQUEST);
			} else {
				return new ResponseEntity<String>("Ride does not exist!", HttpStatus.NOT_FOUND);
			}
		}
		   

	}

	@PutMapping(value = "/{id}/end", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('DRIVER')")
	public ResponseEntity<?> endRide(
			@PathVariable @Min(value = 0, message = "Field id must be greater than 0.") int id) {
		try {
			RideReturnedDTO ride = service.finishRide(id);
			this.simpMessagingTemplate.convertAndSend("/topic/ride-finish", ride.getDriver().getId());
			this.simpMessagingTemplate.convertAndSend("/topic/ride-start-finish/" + ride.getDriver().getId(), ride);
			return new ResponseEntity<RideReturnedDTO>(ride, HttpStatus.OK);
		} catch (ResponseStatusException ex) {
			if (ex.getStatusCode() == HttpStatus.BAD_REQUEST) {
				return new ResponseEntity<ExceptionDTO>(new ExceptionDTO(ex.getReason()), HttpStatus.BAD_REQUEST);
			} else {
				return new ResponseEntity<String>("Ride does not exist!", HttpStatus.NOT_FOUND);
			}
		}
		
	}

	@PutMapping(value = "/{id}/cancel", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('DRIVER')")
	public ResponseEntity<?> rejectRide(
			@PathVariable @Min(value = 0, message = "Field id must be greater than 0.") int id,
			@Valid @RequestBody(required = false) ReasonDTO dto) {
		System.out.println("REJECT " + dto.getReason());
		try {
			RideReturnedDTO ride = service.rejectRide(id, dto);
			this.simpMessagingTemplate.convertAndSend("/topic/ride-cancel", ride.getDriver().getId());
			this.simpMessagingTemplate.convertAndSend("/topic/ride-cancel/" + ride.getDriver().getId(), true);
			this.simpMessagingTemplate.convertAndSend(
					"/topic/ride-offer-response/" + ride.getPassengers().get(0).getId(),
					new RideOfferResponseDTO(false, null));

			return new ResponseEntity<RideReturnedDTO>(ride, HttpStatus.OK);
		} catch (ResponseStatusException ex) {
			if (ex.getStatusCode() == HttpStatus.BAD_REQUEST) {
				return new ResponseEntity<ExceptionDTO>(new ExceptionDTO(ex.getReason()), HttpStatus.BAD_REQUEST);
			} else {
				return new ResponseEntity<String>("Ride does not exist!", HttpStatus.NOT_FOUND);
			}
		}
	}

	@PostMapping(value = "/price", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Double> getRideSugestionPrice(@RequestBody UnregisteredRideSuggestionDTO dto) {
		return new ResponseEntity<Double>(service.getRideSugestionPrice(dto), HttpStatus.OK);
	}

	@PostMapping(value = "/favorites", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('PASSENGER')")
	public ResponseEntity<?> addFavoriteRide(@Valid @RequestBody(required = false) FavoriteRideDTO dto) {
		System.out.println("neca");
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

	@DeleteMapping(value = "/favorites/{id}")
	@PreAuthorize("hasRole('PASSENGER')")
	public ResponseEntity<?> deleteFavoriteRide(
			@PathVariable @Min(value = 0, message = "Field id must be greater than 0.") int id) {
		try {
			this.service.deleteFavoriteRide(id);
			return new ResponseEntity<String>("Successful deletion of favorite location!", HttpStatus.NO_CONTENT);
		} catch (FavoriteRideException ex) {
			return new ResponseEntity<String>("Favorite location does not exist!", HttpStatus.NOT_FOUND);
		}
	}

	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping(value = "/date/range", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<RideForReportDTO>> getAllRidesBetweenDates(@RequestParam String from,
			@RequestParam String to) {
		return new ResponseEntity<List<RideForReportDTO>>(this.service.getAllRidesBetweenDates(from, to),
				HttpStatus.OK);
	}

	@PostMapping(value = "/driver-took-off/{rideId}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('DRIVER')")
	public ResponseEntity<?> startRideToDeparture(
			@PathVariable @Min(value = 0, message = "Field id must be greater than 0.") int rideId) {
		System.out.println("TU");
		try {
			RideReturnedDTO rideDto = service.startRideToDeparture(rideId);
			this.simpMessagingTemplate.convertAndSend("/topic/scheduled-ride/driver-took-off/" + rideId, rideDto);
			System.out.println("VRACENO: " + rideDto);
			return new ResponseEntity<RideReturnedDTO>(rideDto, HttpStatus.OK);

		} catch (ResponseStatusException ex) {
			if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
				return new ResponseEntity<String>("Ride does not exist!", HttpStatus.NOT_FOUND);
			} else {
				return new ResponseEntity<String>("Something bad happened", HttpStatus.BAD_REQUEST);
			}
		}
	}

}
