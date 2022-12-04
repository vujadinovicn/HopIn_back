package com.hopin.HopIn.services.interfaces;

import com.hopin.HopIn.dtos.AllPassengersDTO;
import com.hopin.HopIn.dtos.UserDTO;
import com.hopin.HopIn.dtos.UserReturnedDTO;

public interface IPassengerService {
	
	public UserReturnedDTO insert(UserDTO passenger);
	
	public AllPassengersDTO getAll();
}
