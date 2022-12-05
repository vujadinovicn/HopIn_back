package com.HopIn.HopIn.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.HopIn.HopIn.dtos.AllUsersDTO;
import com.HopIn.HopIn.dtos.CredentialsDTO;
import com.HopIn.HopIn.dtos.TokenDTO;
import com.HopIn.HopIn.entities.User;
import com.HopIn.HopIn.services.interfaces.IUserService;

import ch.qos.logback.core.subst.Token;

@Service
public class UserServiceImpl implements IUserService{

	Map<Integer, User> allStudents = new HashMap<Integer, User>();
	@Override
	public AllUsersDTO getAll(int page, int size) {
		if (allStudents.size() == 0) {
			User user = new User();
			allStudents.put(1, user);
		}
		return new AllUsersDTO(this.allStudents);
	}
	
	@Override
	public TokenDTO login(CredentialsDTO credentials) {
		return new TokenDTO(credentials.getEmail(), credentials.getPassword());
	}
	
	
}