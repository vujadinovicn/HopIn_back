package com.HopIn.HopIn.services.interfaces;

import com.HopIn.HopIn.dtos.AllUsersDTO;
import com.HopIn.HopIn.dtos.UserDTO;
import com.HopIn.HopIn.dtos.UserReturnedDTO;

public interface IPassengerService {
	
	public UserReturnedDTO insert(UserDTO passenger);
	
	public AllUsersDTO getAll();
	
	public UserReturnedDTO getPassenger(int id);
	
	public boolean Activate(int id);
	
	public UserReturnedDTO update(int id, UserDTO dto);
}
