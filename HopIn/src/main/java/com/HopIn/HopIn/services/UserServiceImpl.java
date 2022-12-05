package com.HopIn.HopIn.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.HopIn.HopIn.dtos.AllUsersDTO;
import com.HopIn.HopIn.entities.User;
import com.HopIn.HopIn.services.interfaces.IUserService;

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
}