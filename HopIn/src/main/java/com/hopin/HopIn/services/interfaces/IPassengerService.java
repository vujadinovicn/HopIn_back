package com.hopin.HopIn.services.interfaces;

import java.util.List;

import com.hopin.HopIn.dtos.AllUsersDTO;
import com.hopin.HopIn.dtos.RouteDTO;
import com.hopin.HopIn.dtos.UserDTO;
import com.hopin.HopIn.dtos.UserDTOOld;
import com.hopin.HopIn.dtos.UserReturnedDTO;

public interface IPassengerService {
	
	public UserReturnedDTO insert(UserDTO passenger);
	
	public AllUsersDTO getAll();
	
	public UserReturnedDTO getPassenger(int id);
	
	public boolean Activate(int id);
	
	public UserReturnedDTO update(int id, UserDTOOld dto);
	
	public List<RouteDTO> getFavouriteRoutes(int id);
	
	public boolean removeFavouriteRoute(int passengerId, int routeId);

	public boolean addFavouriteRoute(int passwordId, int routeId);

	UserReturnedDTO getById(int id);

	public Boolean verifyRegistration(String verificationCode);

	public Boolean resendVerificationMail(int passengerId);
}
