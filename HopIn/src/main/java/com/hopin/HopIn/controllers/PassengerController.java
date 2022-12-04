package com.hopin.HopIn.controllers;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hopin.HopIn.dtos.AllUsersDTO;
import com.hopin.HopIn.dtos.UserDTO;
import com.hopin.HopIn.dtos.UserReturnedDTO;
import com.hopin.HopIn.entities.User;
import com.hopin.HopIn.services.interfaces.IPassengerService;

@RestController
@RequestMapping("/api/passenger")
public class PassengerController {
	
	@Autowired
	private IPassengerService service;
	
	
	@PostMapping()
	public UserReturnedDTO insertPassenger(@RequestBody UserDTO dto) {
		return service.insert(dto);
	}
	
	@GetMapping()
	public AllUsersDTO getPassengers() {
		return service.getAll();
	}

}
