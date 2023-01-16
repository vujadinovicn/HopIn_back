package com.hopin.HopIn.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
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

import com.hopin.HopIn.dtos.AllHoursDTO;
import com.hopin.HopIn.dtos.AllUserRidesReturnedDTO;
import com.hopin.HopIn.dtos.AllUsersDTO;
import com.hopin.HopIn.dtos.DocumentDTO;
import com.hopin.HopIn.dtos.DocumentReturnedDTO;
import com.hopin.HopIn.dtos.DriverReturnedDTO;
import com.hopin.HopIn.dtos.RideForReportDTO;
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
import com.hopin.HopIn.exceptions.NoActiveDriverException;
import com.hopin.HopIn.exceptions.NoVehicleException;
import com.hopin.HopIn.exceptions.WorkingHoursException;
import com.hopin.HopIn.services.interfaces.IDriverService;
import com.hopin.HopIn.services.interfaces.IRideService;
import com.hopin.HopIn.services.interfaces.IWorkingHoursService;
import com.hopin.HopIn.validations.ExceptionDTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

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

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<DriverReturnedDTO> getById(@PathVariable @Min(value = 0, message = "Field id must be greater than 0.") int id) {
		return new ResponseEntity<DriverReturnedDTO>(service.getById(id), HttpStatus.OK);
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AllUsersDTO> getAllPaginated(Pageable pageable) {
		return new ResponseEntity<AllUsersDTO>(service.getAllPaginated(pageable), HttpStatus.OK);
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserReturnedDTO> insert(@Valid @RequestBody UserDTOOld dto) {
		return new ResponseEntity<UserReturnedDTO>(service.insert(dto), HttpStatus.OK);
	}

	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserReturnedDTO> update(@PathVariable("id") @Min(value = 0, message = "Field id must be greater than 0.") int driverId, @Valid @RequestBody UserDTOOld newData) {
		return new ResponseEntity<UserReturnedDTO>(service.update(driverId, newData), HttpStatus.OK);
	}

	@GetMapping(value = "/{id}/documents", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<DocumentReturnedDTO>> getDocuments(@PathVariable("id")  int driverId) {
		return new ResponseEntity<List<DocumentReturnedDTO>>(service.getDocuments(driverId), HttpStatus.OK);
	}

	@PostMapping(value = "/{id}/documents", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<DocumentReturnedDTO> addDocument(@PathVariable int id, @Valid @RequestBody DocumentDTO newDocument) {
		return new ResponseEntity<DocumentReturnedDTO>(service.addDocument(id, newDocument), HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/document/{document-id}")
	public ResponseEntity<Document> deleteDocument(@PathVariable("document-id")  int documentId) {
		//TODO: kako je ovo zamisljeno
		return new ResponseEntity<Document>(HttpStatus.NO_CONTENT);
	}

	@GetMapping(value = "/{id}/vehicle", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<VehicleDTO> getVehicle(@PathVariable("id") int driverId) {
		return new ResponseEntity<VehicleDTO>(service.getVehicle(driverId), HttpStatus.OK);
	}
	
	@PostMapping(value = "/{id}/vehicle", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<VehicleReturnedDTO> insertVehicle(@PathVariable("id") int driverId, @Valid @RequestBody VehicleDTO vehicle) {
		return new ResponseEntity<VehicleReturnedDTO>(service.insertVehicle(driverId, vehicle), HttpStatus.OK);
	}
	
	@PutMapping(value = "/{id}/vehicle", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<VehicleReturnedDTO> updateVehicle(@PathVariable("id") int driverId, @Valid @RequestBody VehicleDTO vehicle) {
		return new ResponseEntity<VehicleReturnedDTO>(service.updateVehicle(driverId, vehicle), HttpStatus.OK);
	}
	
	@GetMapping(value="/{id}/ride", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AllUserRidesReturnedDTO> getAllRides(@PathVariable("id") int driverId, @RequestParam  int page, @RequestParam int size, @RequestParam String sort, 
													@RequestParam String from, @RequestParam String to) {
		return new ResponseEntity<AllUserRidesReturnedDTO>(service.getAllRides(driverId, page, size, sort, from, to), HttpStatus.OK);
	}
	
	@GetMapping(value="/{id}/working-hour", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AllHoursDTO> getAllHours(@PathVariable int id, @RequestParam int page, @RequestParam int size, 
													@RequestParam String from, @RequestParam String to) {
		return new ResponseEntity<AllHoursDTO>(service.getAllHours(id, page, size, from, to), HttpStatus.OK);
		
	}
	
	@GetMapping(value = "/working-hour/{working-hour-id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('DRIVER')")
	public ResponseEntity<?> getWorkingHours(@PathVariable("working-hour-id") @Min(value = 0, message = "Field id must be greater than 0.") int hoursId) {
		try {
			return new ResponseEntity<WorkingHoursDTO>(workingHoursService.getWorkingHoursById(hoursId), HttpStatus.OK);
		} catch (WorkingHoursException ex) {
			return new ResponseEntity<String>("Working hour does not exist!", HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping(value = "/{id}/working-hour", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('DRIVER')")
	public ResponseEntity<?> addWorkingHours(@PathVariable("id") @Min(value = 0, message = "Field id must be greater than 0.") int driverId, @RequestBody @Valid WorkingHoursStartDTO dto) {
		try {
			return new ResponseEntity<WorkingHoursDTO>(workingHoursService.addWorkingHours(driverId, dto), HttpStatus.OK);
		} catch (ResponseStatusException ex) {
			return new ResponseEntity<ExceptionDTO>(new ExceptionDTO(ex.getMessage()), HttpStatus.NOT_FOUND);
		} catch (WorkingHoursException ex) {
			return new ResponseEntity<ExceptionDTO>(new ExceptionDTO("Cannot start shift because you exceeded the 8 hours limit in last 24 hours!"), HttpStatus.BAD_REQUEST);
		} catch (DriverAlreadyActiveException ex) {
			return new ResponseEntity<ExceptionDTO>(new ExceptionDTO("Shifth already ongoing!"), HttpStatus.BAD_REQUEST);
		} catch (NoVehicleException ex) {
			return new ResponseEntity<ExceptionDTO>(new ExceptionDTO("Cannot start shift because the vehicle is not defined!"), HttpStatus.BAD_REQUEST);
		}	 
	}
	
	@PutMapping(value = "/working-hour/{working-hour-id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('DRIVER')")
	public ResponseEntity<?> updateWorkingHours(@PathVariable("working-hour-id") @Min(value = 0, message = "Field id must be greater than 0.") int hoursId, @RequestBody WorkingHoursEndDTO dto) {
		try {
			return new ResponseEntity<WorkingHoursDTO>(workingHoursService.updateWorkingHours(hoursId, dto), HttpStatus.OK);
		} catch (BadIdFormatException ex) {
			return new ResponseEntity<ExceptionDTO>(new ExceptionDTO("Working hour does not exist!"), HttpStatus.BAD_REQUEST);
		} catch (BadDateTimeFormatException ex) {
			return new ResponseEntity<ExceptionDTO>(new ExceptionDTO("Bad Date format, or future date is sent!"), HttpStatus.BAD_REQUEST);
		} catch (NoActiveDriverException ex) {
			return new ResponseEntity<ExceptionDTO>(new ExceptionDTO("No shift is ongoing!"), HttpStatus.BAD_REQUEST);
		} catch (NoVehicleException ex) {
			return new ResponseEntity<ExceptionDTO>(new ExceptionDTO("Cannot end shift because the vehicle is not defined!"), HttpStatus.BAD_REQUEST);
		}	
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping(value = "{id}/ride/date", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<RideForReportDTO>> getAllRidesBetweenDates(@PathVariable int id, @RequestParam String from, @RequestParam String to) {
		return new ResponseEntity<List<RideForReportDTO>>(this.rideService.getAllDriverRidesBetweenDates(id, from, to), HttpStatus.OK);
	}
}
