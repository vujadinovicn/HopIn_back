package com.hopin.HopIn.services.interfaces;

import java.util.List;

import com.hopin.HopIn.dtos.AllUsersDTO;
import com.hopin.HopIn.dtos.UserDTO;
import com.hopin.HopIn.dtos.UserReturnedDTO;
import com.hopin.HopIn.entities.Route;

public interface IPassengerService {
	
	public UserReturnedDTO insert(UserDTO passenger);
	
	public AllUsersDTO getAll();
	
	public UserReturnedDTO getPassenger(int id);
	
	public boolean Activate(int id);
	
	public UserReturnedDTO update(int id, UserDTO dto);
	
	public List<Route> getFavouriteRoutes(int id);
}
