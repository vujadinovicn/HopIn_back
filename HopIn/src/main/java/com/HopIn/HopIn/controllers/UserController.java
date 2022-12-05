package com.HopIn.HopIn.controllers;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.HopIn.HopIn.dtos.AllUsersDTO;
import com.HopIn.HopIn.dtos.CredentialsDTO;
import com.HopIn.HopIn.dtos.TokenDTO;
import com.HopIn.HopIn.services.interfaces.IUserService;

import jakarta.websocket.server.PathParam;

@RestController
@RequestMapping("/api/user")
public class UserController {
	@Autowired
	IUserService userService;
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AllUsersDTO> getAll(@RequestParam int page, @RequestParam int size) {
		return new ResponseEntity<AllUsersDTO>(userService.getAll(page, size), HttpStatus.OK);
	}
	
	@PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TokenDTO> login(@RequestBody CredentialsDTO credentials){
		TokenDTO token = userService.login(credentials);
		if (token == null)
			return new ResponseEntity<TokenDTO>(HttpStatus.BAD_REQUEST);
		return new ResponseEntity<TokenDTO>(token, HttpStatus.OK);
	}
	
	@PutMapping(value="{id}/block", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> block(@PathVariable int id){
		boolean isSuccesfullyBlocked = userService.block(id);
		//if (isSuccesfullyBlocked)
			return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
		//return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		//return new ResponseEntity<String>(HttpStatus.NOT_FOUND)
	}
}
