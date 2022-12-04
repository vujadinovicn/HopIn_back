package com.HopIn.HopIn.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.HopIn.HopIn.dtos.UserDTO;
import com.HopIn.HopIn.dtos.UserReturnedDTO;
import com.HopIn.HopIn.services.interfaces.IDriverService;

@RestController
@RequestMapping("/api/driver")
public class DriverController {

	@Autowired
	private IDriverService service;
	
	@GetMapping("/{id}")
	public UserReturnedDTO getById(@PathVariable int id) {
		return service.getById(id);
	}
	
	@PostMapping
	public UserReturnedDTO insert(@RequestBody UserDTO dto) {
		return service.insert(dto);
	}
	
}
