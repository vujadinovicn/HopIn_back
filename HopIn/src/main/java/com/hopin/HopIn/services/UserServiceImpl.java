package com.hopin.HopIn.services;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
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
import com.hopin.HopIn.entities.Message;
import com.hopin.HopIn.entities.Note;
import com.hopin.HopIn.entities.Ride;
import com.hopin.HopIn.entities.User;
import com.hopin.HopIn.enums.MessageType;
import com.hopin.HopIn.exceptions.UserNotFoundException;
import com.hopin.HopIn.repositories.MessageRepository;
import com.hopin.HopIn.repositories.NoteRepository;
import com.hopin.HopIn.repositories.UserRepository;
import com.hopin.HopIn.services.interfaces.IUserService;

@Service
public class UserServiceImpl implements IUserService, UserDetailsService {

	@Autowired 
	private UserRepository allUsers;
	@Autowired 
	private MessageRepository allMessages;
	@Autowired
	private NoteRepository allNotes;
	
	Map<Integer, User> allUsersMap = new HashMap<Integer, User>();
	Map<Integer, Note> allNotesMap = new HashMap<Integer, Note>();
	Map<Integer, Message> allMessagesMap = new HashMap<Integer, Message>();
	Map<Integer, Ride> allRides = new HashMap<Integer, Ride>();
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<User> ret = allUsers.findByEmail(email);
		if (!ret.isEmpty() && !ret.get().isBlocked()) {
			return org.springframework.security.core.userdetails.User.withUsername(email).password(ret.get().getPassword()).roles(ret.get().getRole().toString()).build();
		}
		throw new UsernameNotFoundException("User not found with this username: " + email);
	}
	
	@Override
	public User getByEmail(String email) {
		Optional<User> found = allUsers.findByEmail(email);
		if (found.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
		}
		return found.get();
	}
	
	@Override
	public UserReturnedDTO getUser(int id) {
		Optional<User> found = allUsers.findById(id);
		if (found.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
		}
		return new UserReturnedDTO(found.get());
	}
	
	@Override
	public AllUsersDTO getAll(int page, int size) {
		if (allUsersMap.size() == 0) {
			User user = new User();
			allUsersMap.put(1, user);
		}
		return new AllUsersDTO(this.allUsersMap);
	}
	
	@Override
	public TokenDTO login(CredentialsDTO credentials) {
		return null;
	}

	@Override
	public boolean block(int userId) {
		User user = getById(userId);
		if (user != null) {
			user.setActivated(false);	
			return true;
		}
		return false;
	}

	@Override
	public User getById(int userId) {
		User user = allUsersMap.get(userId);
		if (user == null)
			return null;
		return user;
	}

	@Override
	public boolean unblock(int userId) {
		User user = getById(userId);
		if (user != null) {
			user.setActivated(true);
			return true;
		}
		return false;
	}

	@Override
	public NoteReturnedDTO addNote(int userId, NoteDTO noteDTO) {
		User user = this.allUsers.findById(userId).orElse(null);
		if (user == null){
			throw new UserNotFoundException();
		}
		
		Note note = new Note(LocalDateTime.now(), noteDTO.getMessage());
		note.setUser(user);
		this.allNotes.save(note);
		this.allNotes.flush();
		
		return new NoteReturnedDTO(note);	
	}

	@Override
	public AllNotesDTO getNotes(int userId, int page, int size) {
		User user = this.allUsers.findById(userId).orElse(null);
		if (user == null){
			throw new UserNotFoundException();
		}
		Pageable pageable = PageRequest.of(page, size);
		List<Note> notes = this.allNotes.findAllByUserId(userId, pageable);
		return new AllNotesDTO(notes);
	}

	@Override
	public MessageReturnedDTO sendMessage(int userId, MessageDTO sentMessage) {
		User user = getById(userId);
		return createDetailedMessage(sentMessage);
	}
	
	private MessageReturnedDTO createDetailedMessage(MessageDTO sentMessage) {
		return new MessageReturnedDTO(sentMessage.getReceiverId(),
				sentMessage.getReceiverId(),
				sentMessage.getReceiverId(),
				LocalDateTime.now(),
				sentMessage.getMessage(),
				sentMessage.getType(),
				sentMessage.getRideId());
	}

	@Override
	public AllMessagesDTO getMessages(int userId) {
		User user = getById(userId);
		if (allMessagesMap.size() == 0) {
			Message message = new Message(1, 123, 123, LocalDateTime.now(), "Message is here!", MessageType.RIDE, 123);
			allMessagesMap.put(message.getId(), message);
		}
		return new AllMessagesDTO(this.allMessagesMap);
	}

	@Override
	public AllUserRidesReturnedDTO getRides(int userId, int page, int size, String sort, String from, String to) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean userAlreadyExists(String email) {
		return (!this.allUsers.getUserByEmail(email).isEmpty());
	}

	@Override
	public void activateUser(User user) {
		user.setActivated(true);
		this.allUsers.save(user);
		this.allUsers.flush();
	}

//	@Override
//	public AllUserRidesReturnedDTO getRides(int userId, int page, int size, String sort, String from, String to) {
//		User user = getById(userId);
//		if (allRides.size() == 0) {
//			Ride ride = new Ride(1, LocalDateTime.now(), LocalDateTime.now(), 
//					123, 123, null, true, true, null, null, new RejectionNotice("Partizan sampion"), null, null, null);
//			allRides.put(ride.getId(), ride);
//		}
//		return new AllUserRidesReturnedDTO(this.allRides);
//	}
}