package com.HopIn.HopIn.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.HopIn.HopIn.dtos.AllUsersDTO;
import com.HopIn.HopIn.services.interfaces.IUserService;

@RestController
@RequestMapping("/api/user")
public class UserController {
	@Autowired
	IUserService userService;
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AllUsersDTO> getAll(@RequestParam int page, @RequestParam int size) {
		return new ResponseEntity<AllUsersDTO>(userService.getAll(page, size), HttpStatus.OK);
	}
}
