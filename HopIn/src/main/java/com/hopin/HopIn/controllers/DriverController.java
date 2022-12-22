package com.hopin.HopIn.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

import com.hopin.HopIn.dtos.AllHoursDTO;
import com.hopin.HopIn.dtos.AllUserRidesReturnedDTO;
import com.hopin.HopIn.dtos.AllUsersDTO;
import com.hopin.HopIn.dtos.DocumentDTO;
import com.hopin.HopIn.dtos.RideForReportDTO;
import com.hopin.HopIn.dtos.UserDTO;
import com.hopin.HopIn.dtos.UserReturnedDTO;
import com.hopin.HopIn.dtos.VehicleDTO;
import com.hopin.HopIn.dtos.WorkingHoursDTO;
import com.hopin.HopIn.entities.Document;
import com.hopin.HopIn.entities.Vehicle;
import com.hopin.HopIn.services.interfaces.IDriverService;
import com.hopin.HopIn.services.interfaces.IRideService;

@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RequestMapping("/api/driver")
public class DriverController {

	@Autowired
	private IDriverService service;
	
	@Autowired
	private IRideService rideService;

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserReturnedDTO> getById(@PathVariable int id) {
		return new ResponseEntity<UserReturnedDTO>(service.getById(id), HttpStatus.OK);
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AllUsersDTO> getAllPaginated(Pageable pageable) {
		return new ResponseEntity<AllUsersDTO>(service.getAllPaginated(pageable), HttpStatus.OK);
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserReturnedDTO> insert(@RequestBody UserDTO dto) {
		return new ResponseEntity<UserReturnedDTO>(service.insert(dto), HttpStatus.OK);
	}

	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserReturnedDTO> update(@PathVariable("id") int driverId, @RequestBody UserDTO newData) {
		return new ResponseEntity<UserReturnedDTO>(service.update(driverId, newData), HttpStatus.OK);
	}

	@GetMapping(value = "/{id}/documents", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Document>> getDocuments(@PathVariable("id") int driverId) {
		return new ResponseEntity<List<Document>>(service.getDocuments(driverId), HttpStatus.OK);
	}

	@PostMapping(value = "/{id}/documents", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Document> addDocument(@PathVariable int id, @RequestBody DocumentDTO newDocument) {
		return new ResponseEntity<Document>(service.addDocument(id, newDocument), HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/document/{document-id}")
	public ResponseEntity<Document> deleteDocument(@PathVariable("document-id") int documentId) {
		//TODO: kako je ovo zamisljeno
		return new ResponseEntity<Document>(HttpStatus.NO_CONTENT);
	}

	@GetMapping(value = "/{id}/vehicle", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<VehicleDTO> getVehicle(@PathVariable("id") int driverId) {
		VehicleDTO vehicle = service.getVehicle(driverId);
		if (vehicle == null)
			return new ResponseEntity<VehicleDTO>(HttpStatus.NOT_FOUND);
		return new ResponseEntity<VehicleDTO>(vehicle, HttpStatus.OK);
	}
	
	@PostMapping(value = "/{id}/vehicle", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Vehicle> setVehicle(@PathVariable("id") int driverId, @RequestBody VehicleDTO vehicle) {
		return new ResponseEntity<Vehicle>(service.setVehicle(driverId, vehicle), HttpStatus.OK);
	}
	
	@PutMapping(value = "/{id}/vehicle", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Vehicle> updateVehicle(@PathVariable("id") int driverId, @RequestBody VehicleDTO vehicle) {
		return new ResponseEntity<Vehicle>(service.updateVehicle(driverId, vehicle), HttpStatus.OK);
	}
	
	@GetMapping(value="/{id}/ride", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AllUserRidesReturnedDTO> getAllRides(@PathVariable("id") int driverId, @RequestParam int page, @RequestParam int size, @RequestParam String sort, 
													@RequestParam String from, @RequestParam String to) {
		return new ResponseEntity<AllUserRidesReturnedDTO>(service.getAllRides(driverId, page, size, sort, from, to), HttpStatus.OK);
	}
	
	@GetMapping(value="/{id}/working-hour", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AllHoursDTO> getAllHours(@PathVariable int id, @RequestParam int page, @RequestParam int size, 
													@RequestParam String from, @RequestParam String to) {
		return new ResponseEntity<AllHoursDTO>(service.getAllHours(id, page, size, from, to), HttpStatus.OK);
		
	}
	
	@GetMapping(value = "/working-hour/{working-hour-id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<WorkingHoursDTO> getWorkingHours(@PathVariable("working-hour-id") int hoursId) {
		return new ResponseEntity<WorkingHoursDTO>(service.getWorkingHours(hoursId), HttpStatus.OK);
	}
	
	@PostMapping(value = "/{id}/working-hour", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<WorkingHoursDTO> addWorkingHours(@PathVariable("id") int driverId, @RequestBody WorkingHoursDTO hours) {
		return new ResponseEntity<WorkingHoursDTO>(service.addWorkingHours(driverId, hours), HttpStatus.OK);
	}
	
	@PutMapping(value = "/working-hour/{working-hour-id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<WorkingHoursDTO> updateWorkingHours(@PathVariable("working-hour-id") int hoursId, @RequestBody WorkingHoursDTO hours) {
		return new ResponseEntity<WorkingHoursDTO>(service.updateWorkingHours(hoursId, hours), HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping(value = "{id}/ride/date", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<RideForReportDTO>> getAllRidesBetweenDates(@PathVariable int id, @RequestParam String from, @RequestParam String to) {
		return new ResponseEntity<List<RideForReportDTO>>(this.rideService.getAllDriverRidesBetweenDates(id, from, to), HttpStatus.OK);
	}
}
