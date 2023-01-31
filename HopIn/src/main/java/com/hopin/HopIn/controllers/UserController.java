package com.hopin.HopIn.controllers;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
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
import com.hopin.HopIn.dtos.AllPassengerRidesDTO;
import com.hopin.HopIn.dtos.AllUsersDTO;
import com.hopin.HopIn.dtos.ChangePasswordDTO;
import com.hopin.HopIn.dtos.CredentialsDTO;
import com.hopin.HopIn.dtos.InboxReturnedDTO;
import com.hopin.HopIn.dtos.MessageDTO;
import com.hopin.HopIn.dtos.MessageReturnedDTO;
import com.hopin.HopIn.dtos.NoteDTO;
import com.hopin.HopIn.dtos.NoteReturnedDTO;
import com.hopin.HopIn.dtos.ResetPasswordDTO;
import com.hopin.HopIn.dtos.TokenDTO;
import com.hopin.HopIn.dtos.UserReturnedDTO;
import com.hopin.HopIn.exceptions.BlockedUserException;
import com.hopin.HopIn.exceptions.RideNotFoundException;
import com.hopin.HopIn.exceptions.UserNotFoundException;
import com.hopin.HopIn.services.interfaces.IUserService;
import com.hopin.HopIn.util.TokenUtils;
import com.hopin.HopIn.validations.ExceptionDTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

@Validated
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/user")
public class UserController {
	@Autowired
	IUserService userService;

	@Autowired
	private TokenUtils tokenUtils;

	@Autowired
	private AuthenticationManager authenticationManager;

	private HashMap<String, String> refreshTokens = new HashMap<String, String>();

//	@PreAuthorize("hasRole('ADMIN')"
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserReturnedDTO> getById(@PathVariable int id) {
		System.out.println("USAO SAMMMM");
		return new ResponseEntity<UserReturnedDTO>(userService.getUser(id), HttpStatus.OK);
	}

	@GetMapping(value = "/email", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserReturnedDTO> getByEmail(@RequestParam String email) {
		return new ResponseEntity<UserReturnedDTO>(new UserReturnedDTO(userService.getByEmail(email)), HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AllUsersDTO> getAll(@RequestParam int page, @RequestParam int size) {
		return new ResponseEntity<AllUsersDTO>(userService.getAll(page, size), HttpStatus.OK);
	}

	@PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> login(@RequestBody CredentialsDTO credentials) {
		Authentication authentication;
		try {
			authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(credentials.getEmail(), credentials.getPassword()));
		} catch (BadCredentialsException e) {
			return new ResponseEntity<ExceptionDTO>(new ExceptionDTO("Wrong username or password!"), HttpStatus.BAD_REQUEST);
		} catch (Exception ex) {
			System.out.println(ex.getStackTrace());
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
				return new ResponseEntity<ExceptionDTO>(new ExceptionDTO("CAN'T FIND REFRESH TOKEN."),
						HttpStatus.UNAUTHORIZED);
			} else if (!this.refreshTokens.get(dto.getRefreshToken()).equals(email)) {
				return new ResponseEntity<ExceptionDTO>(new ExceptionDTO("CAN'T FIND REFRESH TOKEN."),
						HttpStatus.UNAUTHORIZED);
			}
		} catch (Exception ex) {
			this.refreshTokens.remove(dto.getRefreshToken());
			return new ResponseEntity<ExceptionDTO>(new ExceptionDTO("UNAUTHORIZED"), HttpStatus.UNAUTHORIZED);
		}

		String newToken = tokenUtils.renewToken(dto.getRefreshToken());
		return new ResponseEntity<TokenDTO>(new TokenDTO(newToken, dto.getRefreshToken()), HttpStatus.OK);
	}

	@PutMapping(value = "{id}/block", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> block(@PathVariable @Min(value = 0, message = "Field id must be greater than 0.") int id){
		try {
			this.userService.block(id);
			return new ResponseEntity<String>("User is successfully blocked", HttpStatus.NO_CONTENT);
		} catch (BlockedUserException ex) {
			return new ResponseEntity<ExceptionDTO>(new ExceptionDTO("User already blocked!"), HttpStatus.BAD_REQUEST);
		} catch (ResponseStatusException ex) {
			return new ResponseEntity<String>(ex.getReason(), HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping(value = "{id}/unblock", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> unblock(@PathVariable @Min(value = 0, message = "Field id must be greater than 0.") int id){
		try {
			this.userService.unblock(id);
			return new ResponseEntity<String>("User is successfully unblocked", HttpStatus.NO_CONTENT);
		} catch (BlockedUserException ex) {
			return new ResponseEntity<ExceptionDTO>(new ExceptionDTO("User is not blocked!"), HttpStatus.BAD_REQUEST);
		} catch (ResponseStatusException ex) {
			return new ResponseEntity<String>(ex.getReason(), HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping(value = "{id}/note", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> addNote(@PathVariable @Min(value = 0, message = "Field id must be greater than 0.") int id,
			@Valid @RequestBody NoteDTO note) {
		try {
			NoteReturnedDTO noteR = userService.addNote(id, note);
			System.out.println(noteR);
			return new ResponseEntity<NoteReturnedDTO>(noteR, HttpStatus.OK);
		} catch (UserNotFoundException e) {
			return new ResponseEntity<String>("User does not exist!", HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping(value = "{id}/note", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> getNotes(
			@PathVariable @Min(value = 0, message = "Field id must be greater than 0.") int id) {
		try {
			AllNotesDTO all = userService.getNotes(id);
			System.out.println(all);
			return new ResponseEntity<AllNotesDTO>(all, HttpStatus.OK);
		} catch (UserNotFoundException e) {
			return new ResponseEntity<String>("User does not exist!", HttpStatus.NOT_FOUND);
		}
	}

	@PreAuthorize("hasRole('ADMIN')" + " || " + "hasRole('PASSENGER')"+ " || " + "hasRole('DRIVER')")
	@PostMapping(value="{id}/message", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> sendMessage(@PathVariable @Min(value = 0, message = "Field id must be greater than 0.") int id, @Valid @RequestBody MessageDTO message){
		try {
			MessageReturnedDTO mess = userService.sendMessage(id, message);
			System.out.println(mess);
			return new ResponseEntity<MessageReturnedDTO>(mess, HttpStatus.OK);
		} catch (ResponseStatusException ex) {
			return new ResponseEntity<String>(ex.getReason(), HttpStatus.NOT_FOUND);
		} catch (RideNotFoundException e) {
			return new ResponseEntity<String>("Ride does not exist!", HttpStatus.NOT_FOUND);
		}
	}

	@PreAuthorize("hasRole('ADMIN')" + " || " + "hasRole('PASSENGER')"+ " || " + "hasRole('DRIVER')")
	@GetMapping(value = "{id}/message", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getMessages(@PathVariable int id) {
		try {
			AllMessagesDTO all = userService.getMessages(id);
			System.out.println(all);
			return new ResponseEntity<AllMessagesDTO>(all, HttpStatus.OK);
		} catch (ResponseStatusException ex) {
			return new ResponseEntity<String>(ex.getReason(), HttpStatus.NOT_FOUND);
		}
	}
	
	@PreAuthorize("hasRole('ADMIN')" + " || " + "hasRole('PASSENGER')"+ " || " + "hasRole('DRIVER')")
	@GetMapping(value = "{id}/inbox/all", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getInboxes(@PathVariable int id) {
		try {
			List<InboxReturnedDTO> all = userService.getInboxes(id);
			System.out.println(all);
			return new ResponseEntity<List<InboxReturnedDTO>>(all, HttpStatus.OK);
		} catch (ResponseStatusException ex) {
			return new ResponseEntity<String>(ex.getReason(), HttpStatus.NOT_FOUND);
		}
	}
	
	@PreAuthorize("hasRole('ADMIN')" + " || " + "hasRole('PASSENGER')"+ " || " + "hasRole('DRIVER')")
	@GetMapping(value = "{id}/inbox", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getInboxById(@PathVariable int id) {
		try {
			return new ResponseEntity<InboxReturnedDTO>(userService.getInboxById(id), HttpStatus.OK);
		} catch (ResponseStatusException ex) {
			return new ResponseEntity<String>(ex.getReason(), HttpStatus.NOT_FOUND);
		}
	}

	@PreAuthorize("hasRole('ADMIN')" + " || " + "hasRole('PASSENGER')"+ " || " + "hasRole('DRIVER')")
	@GetMapping(value = "{id}/ride", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getRidesPaginated(@PathVariable int id, @RequestParam int page,
			@RequestParam int size, @RequestParam(required = false) String sort, @RequestParam(required = false) String from, @RequestParam(required = false) String to) {
		try {
		return new ResponseEntity<AllPassengerRidesDTO>(userService.getRidesPaginated(id, page, size, sort, from, to),
				HttpStatus.OK);
		} catch (UserNotFoundException e) {
			return new ResponseEntity<String>("User does not exist!", HttpStatus.NOT_FOUND);
		}
	}

	
//	@PreAuthorize("hasRole('ANONYMOUS')")
	@GetMapping(value = "{id}/resetPassword")
	public ResponseEntity<?> resetPassword(@PathVariable @Min(value = 0, message = "Field id must be greater than 0.") int id) {
		try {
			this.userService.sendResetPasswordMail(id);
			return new ResponseEntity<String>("Email with reset code has been sent!", HttpStatus.NO_CONTENT);
		} catch (UserNotFoundException e) {
			return new ResponseEntity<String>("User does not exist!", HttpStatus.NOT_FOUND);
		}
		
	}
	
	@GetMapping(value = "{email}/resetPasswordEmail")
	public ResponseEntity<?> resetPassword(@PathVariable String email) {
		System.out.println("tu");
		try {
			
			this.userService.sendResetPasswordMail(email);
			System.out.println("ev");
			return new ResponseEntity<String>("Email with reset code has been sent!", HttpStatus.NO_CONTENT);
		} catch (UserNotFoundException e) {
			return new ResponseEntity<String>("User does not exist!", HttpStatus.NOT_FOUND);
		}
		
	}

	
//	@PreAuthorize("hasRole('ANONYMOUS')")
	@PutMapping(value = "{id}/resetPassword", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> resetPassword(@PathVariable @Min(value = 0, message = "Field id must be greater than 0.") int id, @Valid @RequestBody ResetPasswordDTO dto) {
		try {
			this.userService.resetPassword(id, dto);
			return new ResponseEntity<String>("Password successfully changed!", HttpStatus.NO_CONTENT);
		} catch (UserNotFoundException e) {
			return new ResponseEntity<String>("User does not exist!", HttpStatus.NOT_FOUND);
		} catch (ResponseStatusException e) {
			return new ResponseEntity<ExceptionDTO>(new ExceptionDTO(e.getReason()), HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@PreAuthorize("hasRole('ADMIN')" + " || " + "hasRole('PASSENGER')"+ " || " + "hasRole('DRIVER')")
	@PutMapping(value = "{id}/changePassword", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> changePassword(@PathVariable @Min(value = 0, message = "Field id must be greater than 0.") int id, @Valid @RequestBody ChangePasswordDTO dto) {
		try {
			this.userService.changePassword(id, dto);
			return new ResponseEntity<String>("Password successfully changed!", HttpStatus.NO_CONTENT);
		} catch (UserNotFoundException e) {
			return new ResponseEntity<String>("User does not exist!", HttpStatus.NOT_FOUND);
		} catch (ResponseStatusException e) {
			return new ResponseEntity<ExceptionDTO>(new ExceptionDTO("Current password is not matching!"), HttpStatus.BAD_REQUEST);
		}
		
	}

}
