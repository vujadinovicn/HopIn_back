package com.hopin.HopIn.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hopin.HopIn.dtos.DocumentRequestDTO;
import com.hopin.HopIn.dtos.InfoRequestDTO;
import com.hopin.HopIn.dtos.PasswordRequestDTO;
import com.hopin.HopIn.dtos.RequestDTO;
import com.hopin.HopIn.dtos.VehicleRequestDTO;
import com.hopin.HopIn.services.interfaces.IAdministratorService;

@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RequestMapping("/api/request")
public class RequestController {
	
	@Autowired
	IAdministratorService service;
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<RequestDTO>> getAll() {
		List<RequestDTO> requests = this.service.getAll();
		return new ResponseEntity<List<RequestDTO>>(requests, HttpStatus.OK);
	}
	
	@GetMapping(value = "/pending",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<RequestDTO>> getAllPending() {
		List<RequestDTO> requests = this.service.getAllPending();
		return new ResponseEntity<List<RequestDTO>>(requests, HttpStatus.OK);
	}
	
	@GetMapping(value = "/processed",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<RequestDTO>> getAllProcessed() {
		List<RequestDTO> requests = this.service.getAllProcessed();
		return new ResponseEntity<List<RequestDTO>>(requests, HttpStatus.OK);
	}
	
	@GetMapping(value = "/{id}/driver/pending", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<RequestDTO>> getAllDriverPending(@PathVariable int id) {
		List<RequestDTO> requests = this.service.getAllDriverPending(id);
		return new ResponseEntity<List<RequestDTO>>(requests, HttpStatus.OK);
	}
	
	@GetMapping(value = "/{id}/driver/processed", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<RequestDTO>> getAllDriverProcessed(@PathVariable int id) {
		List<RequestDTO> requests = this.service.getAllDriverProcessed(id);
		return new ResponseEntity<List<RequestDTO>>(requests, HttpStatus.OK);
	}
	
	@GetMapping(value = "/{id}/admin/processed", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<RequestDTO>> getAllAdminProcessed(@PathVariable int id) {
		List<RequestDTO> requests = this.service.getAllAdminProcessed(id);
		return new ResponseEntity<List<RequestDTO>>(requests, HttpStatus.OK);
	}
	
	@GetMapping(value = "/{id}/info", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<InfoRequestDTO> getInfoById(@PathVariable int id) {
		return new ResponseEntity<InfoRequestDTO>(this.service.getInfoById(id), HttpStatus.OK);
	}
	
	@GetMapping(value = "/{id}/password", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PasswordRequestDTO> getPasswordById(@PathVariable int id) {
		return new ResponseEntity<PasswordRequestDTO>(this.service.getPasswordById(id), HttpStatus.OK);
	}
	
	@GetMapping(value = "/{id}/document", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<DocumentRequestDTO> getDocumentById(@PathVariable int id) {
		return new ResponseEntity<DocumentRequestDTO>(this.service.getDocumentById(id), HttpStatus.OK);
	}
	
	@GetMapping(value = "/{id}/vehicle", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<VehicleRequestDTO> getVehicleById(@PathVariable int id) {
		return new ResponseEntity<VehicleRequestDTO>(this.service.getVehicleById(id), HttpStatus.OK);
	}
	
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RequestDTO> getRequestById(@PathVariable int id) {
		return new ResponseEntity<RequestDTO>(this.service.getRequestById(id), HttpStatus.OK);
	}
	
	@PostMapping(value = "/{requestId}/{adminId}/accept")
	public ResponseEntity<String> acceptRequestById(@PathVariable int requestId, @PathVariable int adminId) {
		this.service.acceptRequest(requestId, adminId);
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
	@PostMapping(value = "/{requestId}/{adminId}/deny")
	public ResponseEntity<String> denyRequestById(@PathVariable int requestId, @PathVariable int adminId, @RequestBody String reason) {
		this.service.denyRequest(requestId, adminId, reason);
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
	@PostMapping(value = "/{driverId}/password/request")
	public ResponseEntity<String> insertPasswordRequest(@PathVariable int driverId, @RequestBody PasswordRequestDTO request) {
		this.service.insertPasswordRequest(driverId, request);
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
	@PostMapping(value = "/{driverId}/info/request")
	public ResponseEntity<String> insertInfoRequest(@PathVariable int driverId, @RequestBody InfoRequestDTO request) {
		this.service.insertInfoRequest(driverId, request);
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
	@PostMapping(value = "/{driverId}/vehicle/request")
	public ResponseEntity<String> insertVehicleRequest(@PathVariable int driverId, @RequestBody VehicleRequestDTO request) {
		this.service.insertVehicleRequest(driverId, request);
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
	@PostMapping(value = "/{driverId}/{operationNumber}/document/request")
	public ResponseEntity<String> insertDocumentRequest(@PathVariable int driverId, @PathVariable int operationNumber, @RequestBody DocumentRequestDTO request) {
		this.service.insertDocumentRequest(driverId, operationNumber, request);
		return new ResponseEntity<String>(HttpStatus.OK);
	}

}
