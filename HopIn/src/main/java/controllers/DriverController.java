package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dtos.UserDTO;
import entities.Driver;
import service.interfaces.IDriverService;

@RestController
@RequestMapping("/api/driver")
public class DriverController {

	@Autowired
	private IDriverService service;
	
	
	@PostMapping
	public Driver insert(@RequestBody UserDTO dto) {
		return service.insert(dto);
	}
	
}
