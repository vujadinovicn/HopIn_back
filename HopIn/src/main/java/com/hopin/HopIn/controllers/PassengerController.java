package com.hopin.HopIn.controllers;

import org.apache.catalina.connector.Response;
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

import com.hopin.HopIn.dtos.AllUsersDTO;
import com.hopin.HopIn.dtos.UserDTO;
import com.hopin.HopIn.dtos.UserReturnedDTO;
import com.hopin.HopIn.services.interfaces.IPassengerService;

@RestController
@RequestMapping("/api/passenger")
public class PassengerController {
	
	@Autowired
	private IPassengerService service;
	
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AllUsersDTO> getPassengers() {
		AllUsersDTO passengers = this.service.getAll();
		return new ResponseEntity<AllUsersDTO>(passengers, HttpStatus.OK);
	}
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserReturnedDTO> insertPassenger(@RequestBody UserDTO dto) {
		UserReturnedDTO passenger = service.insert(dto);
		return new ResponseEntity<UserReturnedDTO>(passenger, HttpStatus.OK);
	}
	
	@PostMapping(value = "/{activationId}")
	public ResponseEntity<String> activatePassenger(@PathVariable int activationId) {
		if(service.Activate(activationId)) {
			return new ResponseEntity<String>("", HttpStatus.OK);
		}
		return new ResponseEntity<String>("", HttpStatus.NOT_FOUND);
	}
	
	
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserReturnedDTO> getPassenger(@PathVariable int id) {
		UserReturnedDTO passenger = service.getPassenger(id);
		if(passenger != null) {
			return new ResponseEntity<UserReturnedDTO>(passenger, HttpStatus.OK);
		}
		return new ResponseEntity<UserReturnedDTO>(HttpStatus.NOT_FOUND);
	}
	
	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserReturnedDTO> updatePassenger(@RequestBody UserDTO dto, @PathVariable int id) {
		UserReturnedDTO passenger = service.update(id, dto);
		if(passenger != null) {
			return new ResponseEntity<UserReturnedDTO>(passenger, HttpStatus.OK);
		}
		return new ResponseEntity<UserReturnedDTO>(HttpStatus.NOT_FOUND);
	}

}
