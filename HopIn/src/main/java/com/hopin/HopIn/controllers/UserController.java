package com.hopin.HopIn.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hopin.HopIn.dtos.AllMessagesDTO;
import com.hopin.HopIn.dtos.AllNotesDTO;
import com.hopin.HopIn.dtos.AllUserRidesReturnedDTO;
import com.hopin.HopIn.dtos.AllUsersDTO;
import com.hopin.HopIn.dtos.CredentialsDTO;
import com.hopin.HopIn.dtos.MessageDTO;
import com.hopin.HopIn.dtos.MessageReturnedDTO;
import com.hopin.HopIn.dtos.NoteDTO;
import com.hopin.HopIn.dtos.NoteReturnedDTO;
import com.hopin.HopIn.dtos.TokenDTO;
import com.hopin.HopIn.dtos.UserReturnedDTO;
import com.hopin.HopIn.services.interfaces.IUserService;

@Validated
@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RequestMapping("/api/user")
public class UserController {
	@Autowired
	IUserService userService;
	
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserReturnedDTO> getById(@PathVariable int id) {
		return new ResponseEntity<UserReturnedDTO>(userService.getUser(id), HttpStatus.OK);
	}
	
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
		return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
	}
	
	@PutMapping(value="{id}/unblock", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> unblock(@PathVariable int id){
		boolean isSuccesfullyUnblocked = userService.unblock(id);
		return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
	}
	
	@PostMapping(value="{id}/note", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<NoteReturnedDTO> addNote(@PathVariable int id, @RequestBody NoteDTO note){
		return new ResponseEntity<NoteReturnedDTO>(userService.addNote(id, note), HttpStatus.OK);
	}
	
	@GetMapping(value="{id}/note", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AllNotesDTO> getNotes(@PathVariable int id, @RequestParam int page, @RequestParam int size){
		return new ResponseEntity<AllNotesDTO>(userService.getNotes(id, page, size), HttpStatus.OK);
	}
	
	@PostMapping(value="{id}/message", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MessageReturnedDTO> sendMessage(@PathVariable int id, @RequestBody MessageDTO message){
		return new ResponseEntity<MessageReturnedDTO>(userService.sendMessage(id, message), HttpStatus.OK);
	}
	
	@GetMapping(value="{id}/message", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AllMessagesDTO> getMessages(@PathVariable int id){
		return new ResponseEntity<AllMessagesDTO>(userService.getMessages(id), HttpStatus.OK);
	}
	
	@GetMapping(value="{id}/ride", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AllUserRidesReturnedDTO> getRides(@PathVariable int id, @RequestParam int page, @RequestParam int size, @RequestParam String sort, @RequestParam String from, @RequestParam String to){
		return new ResponseEntity<AllUserRidesReturnedDTO>(userService.getRides(id, page, size, sort, from, to), HttpStatus.OK);
	}
	
}
