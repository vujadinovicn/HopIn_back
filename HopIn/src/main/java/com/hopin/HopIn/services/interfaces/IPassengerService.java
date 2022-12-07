package com.hopin.HopIn.services.interfaces;

import com.hopin.HopIn.dtos.AllUsersDTO;
import com.hopin.HopIn.dtos.UserDTO;
import com.hopin.HopIn.dtos.UserReturnedDTO;

public interface IPassengerService {
	
	public UserReturnedDTO insert(UserDTO passenger);
	
	public AllUsersDTO getAll();
	
	public UserReturnedDTO getPassenger(int id);
	
	public boolean Activate(int id);
	
	public UserReturnedDTO update(int id, UserDTO dto);
}
