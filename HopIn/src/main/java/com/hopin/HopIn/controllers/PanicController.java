package com.hopin.HopIn.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hopin.HopIn.dtos.AllPanicRidesDTO;
import com.hopin.HopIn.services.interfaces.IRideService;

@Controller
@RequestMapping("/api/panic")
public class PanicController {
	
	@Autowired
	IRideService service;
	
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AllPanicRidesDTO> getAll() {
		return new ResponseEntity<AllPanicRidesDTO>(service.getAllPanicRides(), HttpStatus.OK);
	}
}
