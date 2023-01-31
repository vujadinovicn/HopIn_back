package com.hopin.HopIn.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
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
import com.hopin.HopIn.entities.Inbox;
import com.hopin.HopIn.entities.Message;
import com.hopin.HopIn.entities.Note;
import com.hopin.HopIn.entities.Ride;
import com.hopin.HopIn.entities.User;
import com.hopin.HopIn.enums.SecureTokenType;
import com.hopin.HopIn.exceptions.BlockedUserException;
import com.hopin.HopIn.exceptions.UserNotFoundException;
import com.hopin.HopIn.mail.IMailService;
import com.hopin.HopIn.repositories.InboxRepository;
import com.hopin.HopIn.repositories.MessageRepository;
import com.hopin.HopIn.repositories.NoteRepository;
import com.hopin.HopIn.repositories.RideRepository;
import com.hopin.HopIn.repositories.UserRepository;
import com.hopin.HopIn.services.interfaces.IRideService;
import com.hopin.HopIn.services.interfaces.IUserService;
import com.hopin.HopIn.tokens.ISecureTokenService;
import com.hopin.HopIn.tokens.SecureToken;

@Service
public class UserServiceImpl implements IUserService, UserDetailsService {

	@Autowired 
	private UserRepository allUsers;
	@Autowired 
	private MessageRepository allMessages;
	@Autowired
	private NoteRepository allNotes;
	@Autowired
	private InboxRepository allInboxes;
	@Autowired
	private IRideService rideService;
	@Autowired
	private RideRepository allRides;
	@Autowired
	private BCryptPasswordEncoder encoder;
	@Autowired
	private ISecureTokenService tokenService;
	@Autowired
	private IMailService mailService;
	
	
	Map<Integer, User> allUsersMap = new HashMap<Integer, User>();
	Map<Integer, Note> allNotesMap = new HashMap<Integer, Note>();
	Map<Integer, Message> allMessagesMap = new HashMap<Integer, Message>();
	Map<Integer, Ride> allRidesss = new HashMap<Integer, Ride>();
	
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
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User does not exist.");
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
		Pageable pageable = PageRequest.of(page, size);
		
		List<User> users = allUsers.findAll(pageable).getContent();
		int totalCount = users.size();
		Set<UserReturnedDTO> results =  new HashSet<UserReturnedDTO>();
		for(User user : users) {
			results.add(new UserReturnedDTO(user));
		}
		return new AllUsersDTO(totalCount, results);
	}
	
	@Override
	public TokenDTO login(CredentialsDTO credentials) {
		return null;
	}

	@Override
	public void block(int userId) {
		User user = getById(userId);
		if (user.isBlocked() == true) {
			throw new BlockedUserException();
		}
		user.setBlocked(true);
		allUsers.save(user);
		allUsers.flush();
	}

	@Override
	public User getById(int userId) {
		Optional<User> found = allUsers.findById(userId);
		if (found.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User does not exist!");
		}
		return found.get();
	}

	@Override
	public void unblock(int userId) {
		User user = getById(userId);
		if (user.isBlocked() == false) {
			throw new BlockedUserException();
		}
		user.setBlocked(false);
		allUsers.save(user);
		allUsers.flush();
	}

	@Override
	public NoteReturnedDTO addNote(int userId, NoteDTO noteDTO) {
		User user = this.allUsers.findById(userId).orElse(null);
		User admin = getCurrentUser();
		if (user == null){
			throw new UserNotFoundException();
		}
		
		Note note = new Note(LocalDateTime.now(), noteDTO.getMessage());
		note.setUser(user);
		note.setAdmin(admin);
		this.allNotes.save(note);
		this.allNotes.flush();
		
		return new NoteReturnedDTO(note);	
	}

	@Override
	public AllNotesDTO getNotes(int userId) {
		User user = this.allUsers.findById(userId).orElse(null);
		if (user == null){
			throw new UserNotFoundException();
		}
		List<Note> notes = this.allNotes.findAllByUserId(userId);
		return new AllNotesDTO(notes);
	}

	@Override
	public MessageReturnedDTO sendMessage(int receiverId, MessageDTO dto) {
		try {
			getById(receiverId);
		} catch (ResponseStatusException ex) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Receiver does not exist!");
		}
		
		rideService.getRide(dto.getRideId());
		User sender = getCurrentUser();
		User receiver = getById(receiverId);
		
		Message message = new Message(sender.getId(), receiverId, dto);
		Inbox inbox = allInboxes.findAllInboxesByIds(sender.getId(), receiverId);
		if (inbox == null) {
			inbox = new Inbox(sender, receiver);
		}
		inbox.getMessages().add(message);
		allMessages.save(message);
		allInboxes.save(inbox);
		System.out.println(message);
		allMessages.flush();
		allInboxes.flush();
		return createDetailedMessage(message);
	}
	
	private MessageReturnedDTO createDetailedMessage(Message sentMessage) {
		return new MessageReturnedDTO(sentMessage.getId(),
				sentMessage.getSenderId(),
				sentMessage.getReceiverId(),
				sentMessage.getTimeOfSending(),
				sentMessage.getMessage(),
				sentMessage.getType(),
				sentMessage.getRideId());
	}

	@Override
	public AllMessagesDTO getMessages(int userId) {
		getById(userId);
		List<Message> messages = allMessages.findAllMessagesById(userId);
		return new AllMessagesDTO(messages);
	}
	
	@Override
	public List<InboxReturnedDTO> getInboxes(int id) {
		List<Inbox> inboxes = this.allInboxes.findAllInboxesByUserId(id);
		List<InboxReturnedDTO> ret = new ArrayList<InboxReturnedDTO>();
		for (Inbox inbox : inboxes) {
			ret.add(new InboxReturnedDTO(inbox));
		}
		Collections.sort(ret, new Comparator<InboxReturnedDTO>() {

	        public int compare(InboxReturnedDTO i1, InboxReturnedDTO i2) {
	            return i2.getLastMessage().compareTo(i1.getLastMessage());
	        }
	    });
		return ret;
	}
	
	@Override
	public InboxReturnedDTO getInboxById(int id) {
		Optional<Inbox> found = this.allInboxes.findById(id);
		if (found.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Inbox does not exist!");
		}
		return new InboxReturnedDTO(found.get());
	}

	@Override
	public AllPassengerRidesDTO getRidesPaginated(int userId, int page, int size, String sort, String from, String to) {
		Pageable pageable = PageRequest.of(page, size);
		
		Optional<User> user = this.allUsers.findById(userId);
		if (user.isEmpty()) {
			throw new UserNotFoundException();
		}
		
		
		List<Ride> rides = this.allRides.getAllUserRides(userId, pageable);
		return new AllPassengerRidesDTO(rides);
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
	
	@Override 
	public boolean isIdMatching(int id) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return this.getByEmail(auth.getName()).getId() == id;
	}
	
	@Override
	public User getCurrentUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return this.getByEmail(auth.getName());
	}

	@Override
	public void sendResetPasswordMail(int id) {
		User user = this.allUsers.findById(id).orElse(null);
		if (user == null){
			throw new UserNotFoundException();
		}
		
		SecureToken token = tokenService.createToken(user, SecureTokenType.FORGOT_PASSWORD);
		
//		this.mailService.sendResetPasswordMail(user, token.getToken());
	}
	
	@Override
	public void sendResetPasswordMail(String email) {
		User user = this.allUsers.findByEmail(email).orElse(null);
		if (user == null){
			throw new UserNotFoundException();
		}
		
		SecureToken token = tokenService.createToken(user, SecureTokenType.FORGOT_PASSWORD);

		this.mailService.sendForgotPasswordMail(user, token.getToken());
	}

	@Override
	public void resetPassword(int id, ResetPasswordDTO dto) {
//		User user = this.allUsers.findById(id).orElse(null);
//		Commented out because it was added only to fit swagger spec and tests
//		if (user == null){
//			throw new UserNotFoundException();
//		}
		
		SecureToken token = this.tokenService.findByToken(dto.getCode());
		if (token == null || !this.tokenService.isValid(token) || token.isExpired() || token.getType() != SecureTokenType.FORGOT_PASSWORD) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Code is expired or not correct!");
		}
		
		User user = token.getUser();
		
		user.setPassword(encoder.encode(dto.getNewPassword()));
		allUsers.save(user);
		allUsers.flush();

		tokenService.markAsUsed(token);
	
	}

	@Override
	public void changePassword(int id, ChangePasswordDTO dto) {
		User user = this.allUsers.findById(id).orElse(null);
		if (user == null){
			throw new UserNotFoundException();
		}
		
		if (!encoder.matches(dto.getOldPassword(), user.getPassword())) {
			System.out.println(encoder.encode(dto.getOldPassword()));
			System.out.println(user.getPassword());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		
		user.setPassword(encoder.encode(dto.getNewPassword()));
		allUsers.save(user);
		allUsers.flush();
		
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