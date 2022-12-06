package com.HopIn.HopIn.services.interfaces;

import com.HopIn.HopIn.dtos.AllMessagesDTO;
import com.HopIn.HopIn.dtos.AllNotesDTO;
import com.HopIn.HopIn.dtos.AllUsersDTO;
import com.HopIn.HopIn.dtos.CredentialsDTO;
import com.HopIn.HopIn.dtos.MessageDTO;
import com.HopIn.HopIn.dtos.MessageReturnedDTO;
import com.HopIn.HopIn.dtos.NoteDTO;
import com.HopIn.HopIn.dtos.NoteReturnedDTO;
import com.HopIn.HopIn.dtos.TokenDTO;
import com.HopIn.HopIn.entities.User;

public interface IUserService {
	
	public User getById(int userId);
	
	public AllUsersDTO getAll(int page, int size);
	
	public TokenDTO login(CredentialsDTO credentials);

	public boolean block(int userId);
	
	public boolean unblock(int userId);
	
	public NoteReturnedDTO addNote(int userId, NoteDTO note);
	
	public AllNotesDTO getNotes(int userId, int page, int size);
	
	public MessageReturnedDTO sendMessage(int userId, MessageDTO sentMessage);
	
	public AllMessagesDTO getMessages(int userId);
}