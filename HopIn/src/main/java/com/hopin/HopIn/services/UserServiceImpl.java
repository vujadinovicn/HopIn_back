package com.hopin.HopIn.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.hopin.HopIn.dtos.AllMessagesDTO;
import com.hopin.HopIn.dtos.AllNotesDTO;
import com.hopin.HopIn.dtos.AllUserRidesReturnedDTO;
import com.hopin.HopIn.dtos.AllUsersDTO;
import com.hopin.HopIn.dtos.CredentialsDTO;
import com.hopin.HopIn.dtos.MessageDTO;
import com.hopin.HopIn.dtos.MessageReturnedDTO;
import com.hopin.HopIn.dtos.NoteDTO;
import com.hopin.HopIn.dtos.NoteReturnedDTO;
import com.hopin.HopIn.dtos.RejectionNoticeDTO;
import com.hopin.HopIn.dtos.TokenDTO;
import com.hopin.HopIn.entities.Driver;
import com.hopin.HopIn.entities.Message;
import com.hopin.HopIn.entities.Note;
import com.hopin.HopIn.entities.Passenger;
import com.hopin.HopIn.entities.RejectionNotice;
import com.hopin.HopIn.entities.Ride;
import com.hopin.HopIn.entities.User;
import com.hopin.HopIn.enums.MessageType;
import com.hopin.HopIn.enums.RideStatus;
import com.hopin.HopIn.enums.VehicleTypeName;
import com.hopin.HopIn.exceptions.UserNotFoundException;
import com.hopin.HopIn.services.interfaces.IUserService;

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
			Message message = new Message(1, 123, 123, LocalDateTime.now(), "Message is here!", MessageType.RIDE, 123);
			allMessages.put(message.getId(), message);
		}
		return new AllMessagesDTO(this.allMessages);
	}

	@Override
	public AllUserRidesReturnedDTO getRides(int userId, int page, int size, String sort, String from, String to) {
		User user = getById(userId);
		if (allRides.size() == 0) {
			Ride ride = new Ride(1, LocalDateTime.now(), LocalDateTime.now(), 
					123, 123, null, true, true, null, null, new RejectionNotice("Partizan sampion"), null, null, null);
			allRides.put(ride.getId(), ride);
		}
		return new AllUserRidesReturnedDTO(this.allRides);
	}
}