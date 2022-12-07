package com.HopIn.HopIn.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.HopIn.HopIn.dtos.AllMessagesDTO;
import com.HopIn.HopIn.dtos.AllNotesDTO;
import com.HopIn.HopIn.dtos.AllUserRidesReturnedDTO;
import com.HopIn.HopIn.dtos.AllUsersDTO;
import com.HopIn.HopIn.dtos.CredentialsDTO;
import com.HopIn.HopIn.dtos.MessageDTO;
import com.HopIn.HopIn.dtos.MessageReturnedDTO;
import com.HopIn.HopIn.dtos.NoteDTO;
import com.HopIn.HopIn.dtos.NoteReturnedDTO;
import com.HopIn.HopIn.dtos.TokenDTO;
import com.HopIn.HopIn.entities.Driver;
import com.HopIn.HopIn.entities.Message;
import com.HopIn.HopIn.entities.Note;
import com.HopIn.HopIn.entities.Passenger;
import com.HopIn.HopIn.entities.Ride;
import com.HopIn.HopIn.entities.User;
import com.HopIn.HopIn.enums.MessageType;
import com.HopIn.HopIn.enums.RideStatus;
import com.HopIn.HopIn.enums.VehicleType;
import com.HopIn.HopIn.exceptions.UserNotFoundException;
import com.HopIn.HopIn.services.interfaces.IUserService;

import ch.qos.logback.core.subst.Token;

@Service
public class UserServiceImpl implements IUserService{

	Map<Integer, User> allUsers = new HashMap<Integer, User>();
	Map<Integer, Note> allNotes = new HashMap<Integer, Note>();
	Map<Integer, Message> allMessages = new HashMap<Integer, Message>();
	Map<Integer, Ride> allRides = new HashMap<Integer, Ride>();
	
	@Override
	public AllUsersDTO getAll(int page, int size) {
		if (allUsers.size() == 0) {
			User user = new User();
			allUsers.put(1, user);
		}
		return new AllUsersDTO(this.allUsers);
	}
	
	@Override
	public TokenDTO login(CredentialsDTO credentials) {
		return new TokenDTO(credentials.getEmail(), credentials.getPassword());
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
		User user = allUsers.get(userId);
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
	public NoteReturnedDTO addNote(int userId, NoteDTO note) {
		User user = getById(userId);
		return new NoteReturnedDTO(15, LocalDateTime.now(), note.getMessage());	
	}

	@Override
	public AllNotesDTO getNotes(int userId, int page, int size) {
		User user = getById(userId);
		if (allNotes.size() == 0) {
			Note note = new Note(15, LocalDateTime.now(), "Message is here!");
			allNotes.put(1, note);
		}
		return new AllNotesDTO(this.allNotes);
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
		if (allMessages.size() == 0) {
			Message message = new Message(1, 123, 123, LocalDateTime.now(), "Message is here!", MessageType.VOZNJA, 123);
			allMessages.put(message.getId(), message);
		}
		return new AllMessagesDTO(this.allMessages);
	}

	@Override
	public AllUserRidesReturnedDTO getRides(int userId, int page, int size, String sort, String from, String to) {
		User user = getById(userId);
		if (allRides.size() == 0) {
			Ride ride = new Ride(1, LocalDateTime.now(), LocalDateTime.now(), 
					123, 123, null, true, true, true, null, null, null, new ArrayList<Passenger>(), null, new Driver());
			allRides.put(ride.getId(), ride);
		}
		return new AllUserRidesReturnedDTO(this.allRides);
	}
}