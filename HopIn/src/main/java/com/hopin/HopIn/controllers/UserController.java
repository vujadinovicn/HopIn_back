package com.hopin.HopIn.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
import org.springframework.web.server.ResponseStatusException;

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
import com.hopin.HopIn.entities.User;
import com.hopin.HopIn.exceptions.BlockedUserException;
import com.hopin.HopIn.services.WorkingHoursServiceImpl;
import com.hopin.HopIn.services.interfaces.IUserService;
import com.hopin.HopIn.util.TokenUtils;
import com.hopin.HopIn.validations.ExceptionDTO;

@Validated
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/user")
public class UserController {
	@Autowired
	IUserService userService;
	
	@Autowired
	WorkingHoursServiceImpl workingHoursService;
	
	@Autowired
	private TokenUtils tokenUtils;

	@Autowired
	private AuthenticationManager authenticationManager;
	
	private HashMap<String, String> refreshTokens = new HashMap<String, String>();
	
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
		Authentication authentication;
		try {
			authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					credentials.getEmail(), credentials.getPassword()));
		}
		catch(Exception ex) {
			return new ResponseEntity<TokenDTO>(HttpStatus.UNAUTHORIZED);
		}
		SecurityContextHolder.getContext().setAuthentication(authentication);

		UserDetails user = (UserDetails) authentication.getPrincipal();
		int userId = this.userService.getByEmail(credentials.getEmail()).getId(); 
		String jwt = tokenUtils.generateToken(user, userId);
		String refreshToken = tokenUtils.generateRefreshToken(user, userId);
		this.refreshTokens.put(refreshToken, credentials.getEmail());

		return new ResponseEntity<TokenDTO>(new TokenDTO(jwt, refreshToken), HttpStatus.OK);
	}
	
	@PostMapping(value = "/refresh")
	public ResponseEntity<?> refresh(@RequestBody TokenDTO dto) {
		dto.setRefreshToken(dto.getRefreshToken().substring(1, dto.getRefreshToken().length() - 1));

		try {
			String email = tokenUtils.getUsernameFromToken(dto.getRefreshToken());
			if (this.refreshTokens.get(dto.getRefreshToken()) == null) {
				return new ResponseEntity<ExceptionDTO>(new ExceptionDTO("CAN'T FIND REFRESH TOKEN."), HttpStatus.UNAUTHORIZED);
			}
			else if (!this.refreshTokens.get(dto.getRefreshToken()).equals(email)) {
				return new ResponseEntity<ExceptionDTO>(new ExceptionDTO("CAN'T FIND REFRESH TOKEN."), HttpStatus.UNAUTHORIZED);
			}
		} catch(Exception ex) {
			this.refreshTokens.remove(dto.getRefreshToken());
			return new ResponseEntity<ExceptionDTO>(new ExceptionDTO("UNAUTHORIZED"), HttpStatus.UNAUTHORIZED);
		}
		
		String newToken = tokenUtils.renewToken(dto.getRefreshToken());
		return new ResponseEntity<TokenDTO>(new TokenDTO(newToken, dto.getRefreshToken()), HttpStatus.OK);
	}
	
	@PutMapping(value="{id}/block", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> block(@PathVariable int id){
		try {
			this.userService.block(id);
			return new ResponseEntity<String>("User is successfully blocked", HttpStatus.NO_CONTENT);
		} catch (BlockedUserException ex) {
			return new ResponseEntity<ExceptionDTO>(new ExceptionDTO("User already blocked!"), HttpStatus.BAD_REQUEST);
		} catch (ResponseStatusException ex) {
			return new ResponseEntity<String>(ex.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping(value="{id}/unblock", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> unblock(@PathVariable int id){
		try {
			this.userService.block(id);
			return new ResponseEntity<String>("User is successfully unblocked", HttpStatus.NO_CONTENT);
		} catch (BlockedUserException ex) {
			return new ResponseEntity<ExceptionDTO>(new ExceptionDTO("User is not blocked!"), HttpStatus.BAD_REQUEST);
		} catch (ResponseStatusException ex) {
			return new ResponseEntity<String>(ex.getMessage(), HttpStatus.NOT_FOUND);
		}
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
