package com.HopIn.HopIn.services.interfaces;

import com.HopIn.HopIn.dtos.AllUsersDTO;

public interface IUserService {
	
	public AllUsersDTO getAll(int page, int size);

}