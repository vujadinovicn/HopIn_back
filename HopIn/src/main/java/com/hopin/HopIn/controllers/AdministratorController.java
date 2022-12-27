package com.hopin.HopIn.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hopin.HopIn.dtos.DriverAccountUpdateRequestDTO;
import com.hopin.HopIn.services.interfaces.IAdministratorService;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RequestMapping("/api/administrator")
public class AdministratorController {
	
	@Autowired
	IAdministratorService service;
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<DriverAccountUpdateRequestDTO>> getAll() {
		List<DriverAccountUpdateRequestDTO> requests = this.service.getAll();
		return new ResponseEntity<List<DriverAccountUpdateRequestDTO>>(requests, HttpStatus.OK);
	}
	
	@GetMapping(value = "/pending",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<DriverAccountUpdateRequestDTO>> getAllPending() {
		List<DriverAccountUpdateRequestDTO> requests = this.service.getAllPending();
		return new ResponseEntity<List<DriverAccountUpdateRequestDTO>>(requests, HttpStatus.OK);
	}
	
	@GetMapping(value = "/processed",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<DriverAccountUpdateRequestDTO>> getAllProcessed() {
		List<DriverAccountUpdateRequestDTO> requests = this.service.getAllPending();
		return new ResponseEntity<List<DriverAccountUpdateRequestDTO>>(requests, HttpStatus.OK);
	}

}
