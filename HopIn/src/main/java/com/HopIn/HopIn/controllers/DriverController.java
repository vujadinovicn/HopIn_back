package com.hopin.HopIn.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
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

import com.hopin.HopIn.dtos.AllUsersDTO;
import com.hopin.HopIn.dtos.DocumentDTO;
import com.hopin.HopIn.dtos.UserDTO;
import com.hopin.HopIn.dtos.UserReturnedDTO;
import com.hopin.HopIn.entities.Document;
import com.hopin.HopIn.services.interfaces.IDriverService;

@RestController
@RequestMapping("/api/driver")
public class DriverController {

	@Autowired
	private IDriverService service;
	
	public void heY() {}
	
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserReturnedDTO> getById(@PathVariable int id) {
		return new ResponseEntity<UserReturnedDTO>(service.getById(id), HttpStatus.OK);
	}
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Pageable> getAll(Pageable pageable) {
		return new ResponseEntity<Pageable>(pageable, HttpStatus.OK);
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
	
}
