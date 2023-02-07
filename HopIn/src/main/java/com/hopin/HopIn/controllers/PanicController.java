package com.hopin.HopIn.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hopin.HopIn.dtos.AllPanicRidesDTO;
import com.hopin.HopIn.services.interfaces.IPanicService;
import com.hopin.HopIn.services.interfaces.IRideService;

@Controller
@RequestMapping("/api/panic")
public class PanicController {

	@Autowired
	IPanicService panicService;
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<AllPanicRidesDTO> getAll() {
		AllPanicRidesDTO panicRides = panicService.getAllPanicRides();
		panicRides.getResults().forEach(item -> System.out.println(item.getId()));
		return new ResponseEntity<AllPanicRidesDTO>(panicRides, HttpStatus.OK);
	}
}
