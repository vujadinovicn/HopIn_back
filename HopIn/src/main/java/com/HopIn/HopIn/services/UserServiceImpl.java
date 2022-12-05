package com.HopIn.HopIn.services;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.HopIn.HopIn.dtos.AllUsersDTO;
import com.HopIn.HopIn.dtos.CredentialsDTO;
import com.HopIn.HopIn.dtos.NoteDTO;
import com.HopIn.HopIn.dtos.NoteReturnedDTO;
import com.HopIn.HopIn.dtos.TokenDTO;
import com.HopIn.HopIn.entities.User;
import com.HopIn.HopIn.exceptions.UserNotFoundException;
import com.HopIn.HopIn.services.interfaces.IUserService;

import ch.qos.logback.core.subst.Token;

@Service
public class UserServiceImpl implements IUserService{

	Map<Integer, User> allUsers = new HashMap<Integer, User>();
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
			//throw new UserNotFoundException();
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
		//if (user != null) {
			//user.addNote();
		return new NoteReturnedDTO(15, LocalDateTime.now(), note.getMessage());	
		//}
		//return null;
	}
}