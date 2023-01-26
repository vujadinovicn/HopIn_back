package com.hopin.HopIn.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
import com.hopin.HopIn.dtos.ActiveVehicleDTO;
import com.hopin.HopIn.dtos.AllHoursDTO;
import com.hopin.HopIn.dtos.AllPassengerRidesDTO;
import com.hopin.HopIn.dtos.AllUsersDTO;
import com.hopin.HopIn.dtos.DocumentDTO;
import com.hopin.HopIn.dtos.DocumentReturnedDTO;
import com.hopin.HopIn.dtos.DriverReturnedDTO;
import com.hopin.HopIn.dtos.RideForReportDTO;
import com.hopin.HopIn.dtos.UserDTO;
import com.hopin.HopIn.dtos.UserDTOOld;
import com.hopin.HopIn.dtos.UserReturnedDTO;
import com.hopin.HopIn.dtos.VehicleDTO;
import com.hopin.HopIn.dtos.VehicleReturnedDTO;
import com.hopin.HopIn.dtos.WorkingHoursDTO;
import com.hopin.HopIn.dtos.WorkingHoursEndDTO;
import com.hopin.HopIn.dtos.WorkingHoursStartDTO;
import com.hopin.HopIn.entities.Document;
import com.hopin.HopIn.exceptions.BadDateTimeFormatException;
import com.hopin.HopIn.exceptions.BadIdFormatException;
import com.hopin.HopIn.exceptions.DriverAlreadyActiveException;
import com.hopin.HopIn.exceptions.EmailAlreadyInUseException;
import com.hopin.HopIn.exceptions.NoActiveDriverException;
import com.hopin.HopIn.exceptions.NoVehicleException;
import com.hopin.HopIn.exceptions.UserNotFoundException;
import com.hopin.HopIn.exceptions.VehicleNotAssignedException;
import com.hopin.HopIn.exceptions.WorkingHoursException;
import com.hopin.HopIn.services.interfaces.IDriverService;
import com.hopin.HopIn.services.interfaces.IRideService;
import com.hopin.HopIn.services.interfaces.IWorkingHoursService;
import com.hopin.HopIn.validations.ExceptionDTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

@Validated
@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RequestMapping("/api/driver")
public class DriverController {

	@Autowired
	private IDriverService service;
	
	@Autowired
	private IRideService rideService;
	
	@Autowired
	private IWorkingHoursService workingHoursService;
	
	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getById(@PathVariable @Min(value = 0, message = "Field id must be greater than 0.") int id) {
		try {
			return new ResponseEntity<DriverReturnedDTO>(service.getById(id), HttpStatus.OK);
		}
		catch (UserNotFoundException e){
			return new ResponseEntity<String>("Driver does not exist!", HttpStatus.NOT_FOUND);
		} catch (NullPointerException e) {
			return new ResponseEntity<String>("Vehicle does not exist!", HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<AllUsersDTO> getAllPaginated(@RequestParam  int page, @RequestParam int size, @RequestParam(required = false) String sort, 
			@RequestParam(required = false) String from, @RequestParam(required = false) String to) {
		return new ResponseEntity<AllUsersDTO>(service.getAllPaginated(page, size), HttpStatus.OK);
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> insert(@Valid @RequestBody UserDTO dto) {
		try {
			System.out.println("tu");
			return new ResponseEntity<UserReturnedDTO>(service.insert(dto), HttpStatus.OK);
		} catch (EmailAlreadyInUseException e) {
			return new ResponseEntity<ExceptionDTO>(new ExceptionDTO("User with that email already exists!"), HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ADMIN')" + " || " + "hasRole('DRIVER')")
	public ResponseEntity<?> update(@PathVariable("id") @Min(value = 0, message = "Field id must be greater than 0.") int driverId, @Valid @RequestBody UserDTO newData) {
		try {
			return new ResponseEntity<UserReturnedDTO>(service.update(driverId, newData), HttpStatus.OK);
		} catch (ResponseStatusException ex) {
			return new ResponseEntity<String>(ex.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping(value = "/{id}/documents", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ADMIN')" + " || " + "hasRole('DRIVER')")
	public ResponseEntity<?> getDocuments(@PathVariable("id") @Min(value = 0, message = "Field id must be greater than 0.") int driverId) {
		try {
			return new ResponseEntity<List<DocumentReturnedDTO>>(service.getDocuments(driverId), HttpStatus.OK);
		} catch (UserNotFoundException e){
			return new ResponseEntity<String>("Driver does not exist!", HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping(value = "/{id}/documents", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ADMIN')" + " || " + "hasRole('DRIVER')")
	public ResponseEntity<?> addDocument(@PathVariable int id, @Valid @RequestBody DocumentDTO newDocument) {
		DocumentReturnedDTO doc = service.addDocument(id, newDocument);
		if (doc != null) { return new ResponseEntity<DocumentReturnedDTO>(service.addDocument(id, newDocument), HttpStatus.OK); }
		return new ResponseEntity<String>("Driver does not exist!", HttpStatus.NOT_FOUND);
	}
	
	@DeleteMapping(value = "/document/{document-id}")
	@PreAuthorize("hasRole('ADMIN')" + " || " + "hasRole('DRIVER')")
	public ResponseEntity<?> deleteDocument(@PathVariable("document-id")  int documentId) {
		try {
			service.deleteDocument(documentId);
			return new ResponseEntity<Document>(HttpStatus.NO_CONTENT);			
		} catch (ResponseStatusException ex) {
			return new ResponseEntity<String>("Document does not exist!", HttpStatus.NOT_FOUND);
		}
	} 
	
	@GetMapping(value="/active-vehicles", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getAllVehicles(){
		try {
			return new ResponseEntity<List<ActiveVehicleDTO>>(service.getAllVehicles(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("Error happened", HttpStatus.BAD_REQUEST);
		}   
	}

	@GetMapping(value = "/{id}/vehicle", produces = MediaType.APPLICATION_JSON_VALUE)
//	@PreAuthorize("hasRole('ADMIN')" + " || " + "hasRole('DRIVER')")
//	@PreAuthorize("hasRole('ANONYMOUS')")
	public ResponseEntity<?> getVehicle(@PathVariable("id") @Min(value = 0, message = "Field id must be greater than 0.") int driverId) {
		try {
			return new ResponseEntity<VehicleReturnedDTO>(service.getVehicle(driverId), HttpStatus.OK);
		} catch (UserNotFoundException e) {
			return new ResponseEntity<String>("Driver does not exist!", HttpStatus.NOT_FOUND);
		} catch (VehicleNotAssignedException e) {
			return new ResponseEntity<ExceptionDTO>(new ExceptionDTO("Vehicle is not assigned!"), HttpStatus.BAD_REQUEST);
		}
	}
	      
	@PostMapping(value = "/{id}/vehicle", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ADMIN')" + " || " + "hasRole('DRIVER')")
	public ResponseEntity<?> insertVehicle(@PathVariable("id") int driverId, @Valid @RequestBody VehicleDTO vehicle) {
		try {
			return new ResponseEntity<VehicleReturnedDTO>(service.insertVehicle(driverId, vehicle), HttpStatus.OK);
		} catch (ResponseStatusException ex) {
			return new ResponseEntity<String>("Driver does not exist!", HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping(value = "/{id}/vehicle", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ADMIN')" + " || " + "hasRole('DRIVER')")
	public ResponseEntity<?> updateVehicle(@PathVariable("id") int driverId, @Valid @RequestBody VehicleDTO vehicle) {
		try {
			return new ResponseEntity<VehicleReturnedDTO>(service.updateVehicle(driverId, vehicle), HttpStatus.OK);
		} catch (ResponseStatusException ex) {
			return new ResponseEntity<String>("Driver does not exist!", HttpStatus.NOT_FOUND);
		}
	}  
	
	@GetMapping(value="/{id}/ride", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ADMIN')" + " || " + "hasRole('DRIVER')")
	public ResponseEntity<?> getAllRidesPaginated(@PathVariable("id") int driverId, @RequestParam  int page, @RequestParam int size, @RequestParam(required = false) String sort, 
			@RequestParam(required = false) String from, @RequestParam(required = false) String to) {
		try {
			return new ResponseEntity<AllPassengerRidesDTO>(rideService.getAllDriverRidesPaginated(driverId, page, size, sort, from, to), HttpStatus.OK);
		} catch (UserNotFoundException ex) {
			return new ResponseEntity<String>("Driver does not exist!", HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping(value="/{id}/all/rides", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ADMIN')" + " || " + "hasRole('DRIVER')")
	public ResponseEntity<?> getAllRides(@PathVariable("id") int driverId) {
		try {
			return new ResponseEntity<AllPassengerRidesDTO>(rideService.getAllDriverRides(driverId), HttpStatus.OK);
		} catch (UserNotFoundException ex) {
			return new ResponseEntity<String>("Driver does not exist!", HttpStatus.NOT_FOUND);
		} 
	}
	
	@GetMapping(value="/{id}/working-hour", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ADMIN')" + " || " + "hasRole('DRIVER')")
	public ResponseEntity<?> getAllHours(@PathVariable int id, @RequestParam  int page, @RequestParam int size, @RequestParam(required = false) String sort, 
			@RequestParam(required = false) String from, @RequestParam(required = false) String to) {
		try {
			return new ResponseEntity<AllHoursDTO>(workingHoursService.getAllHours(id, page, size), HttpStatus.OK);
		} catch (UserNotFoundException ex) {
			return new ResponseEntity<String>("Driver does not exist!", HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping(value = "/working-hour/{working-hour-id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ADMIN')" + " || " + "hasRole('DRIVER')")
	public ResponseEntity<?> getWorkingHours(@PathVariable("working-hour-id") @Min(value = 0, message = "Field id must be greater than 0.") int hoursId) {
		try {
			return new ResponseEntity<WorkingHoursDTO>(workingHoursService.getWorkingHoursById(hoursId), HttpStatus.OK);
		} catch (WorkingHoursException ex) {
			return new ResponseEntity<String>("Working hour does not exist!", HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping(value = "/{id}/working-hour", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//	@PreAuthorize("hasRole('DRIVER')")
	public ResponseEntity<?> addWorkingHours(@PathVariable("id") @Min(value = 0, message = "Field id must be greater than 0.") int driverId, @Valid @RequestBody WorkingHoursStartDTO dto) {
		try {     
			ObjectMapper mapper = new ObjectMapper();
			mapper.findAndRegisterModules(); 
			// Java object to JSON string 
			String jsonString = mapper.writeValueAsString(driverId);
			this.simpMessagingTemplate.convertAndSend("/topic/vehicle/activation", jsonString);
			return new ResponseEntity<WorkingHoursDTO>(workingHoursService.addWorkingHours(driverId, dto), HttpStatus.OK);
		} catch (ResponseStatusException ex) {
			return new ResponseEntity<ExceptionDTO>(new ExceptionDTO(ex.getMessage()), HttpStatus.NOT_FOUND);
		} catch (WorkingHoursException ex) {
			return new ResponseEntity<ExceptionDTO>(new ExceptionDTO("Cannot start shift because you exceeded the 8 hours limit in last 24 hours!"), HttpStatus.BAD_REQUEST);
		} catch (DriverAlreadyActiveException ex) {
			return new ResponseEntity<ExceptionDTO>(new ExceptionDTO("Shift already ongoing!"), HttpStatus.BAD_REQUEST);
		} catch (NoVehicleException ex) { 
			return new ResponseEntity<ExceptionDTO>(new ExceptionDTO("Cannot start shift because the vehicle is not defined!"), HttpStatus.BAD_REQUEST);
		} catch (JsonProcessingException e) {
			return new ResponseEntity<ExceptionDTO>(HttpStatus.BAD_REQUEST);
		}
	}    
	
	@PutMapping(value = "/working-hour/{working-hour-id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//	@PreAuthorize("hasRole('DRIVER')")
	public ResponseEntity<?> updateWorkingHours(@PathVariable("working-hour-id") @Min(value = 0, message = "Field id must be greater than 0.") int hoursId, @Valid @RequestBody WorkingHoursEndDTO dto) {
		try { 
			ObjectMapper mapper = new ObjectMapper();
			mapper.findAndRegisterModules(); 
			
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			System.out.println(authentication.getName());
			//int driverId = this.service.getByEmail(authentication.getName()).getId();
			String jsonString = mapper.writeValueAsString(8);
			
			this.simpMessagingTemplate.convertAndSend("/topic/vehicle/deactivation", jsonString);
			return new ResponseEntity<WorkingHoursDTO>(workingHoursService.updateWorkingHours(hoursId, dto), HttpStatus.OK);
		} catch (BadIdFormatException ex) {
			return new ResponseEntity<ExceptionDTO>(new ExceptionDTO("Working hour does not exist!"), HttpStatus.BAD_REQUEST);
		} catch (BadDateTimeFormatException ex) {
			return new ResponseEntity<ExceptionDTO>(new ExceptionDTO("Bad Date format, or future date is sent!"), HttpStatus.BAD_REQUEST);
		} catch (NoActiveDriverException ex) {
			return new ResponseEntity<ExceptionDTO>(new ExceptionDTO("No shift is ongoing!"), HttpStatus.BAD_REQUEST);
		} catch (NoVehicleException ex) {
			return new ResponseEntity<ExceptionDTO>(new ExceptionDTO("Cannot end shift because the vehicle is not defined!"), HttpStatus.BAD_REQUEST);
		}  catch (JsonProcessingException e) {
			return new ResponseEntity<ExceptionDTO>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping(value = "{id}/ride/date", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<RideForReportDTO>> getAllRidesBetweenDates(@PathVariable int id, @RequestParam String from, @RequestParam String to) {
		return new ResponseEntity<List<RideForReportDTO>>(this.rideService.getAllDriverRidesBetweenDates(id, from, to), HttpStatus.OK);
	}
}
