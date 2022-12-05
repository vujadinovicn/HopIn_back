package com.HopIn.HopIn.services.interfaces;

import com.HopIn.HopIn.dtos.AllUsersDTO;
import com.HopIn.HopIn.dtos.CredentialsDTO;
import com.HopIn.HopIn.dtos.TokenDTO;

public interface IUserService {
	
	public AllUsersDTO getAll(int page, int size);
	
	public TokenDTO login(CredentialsDTO credentials);

}