package com.hopin.HopIn.services.interfaces;

import java.util.List;

import com.hopin.HopIn.dtos.AllUsersDTO;
import com.hopin.HopIn.dtos.RouteDTO;
import com.hopin.HopIn.dtos.UserDTO;
import com.hopin.HopIn.dtos.UserDTOOld;
import com.hopin.HopIn.dtos.UserReturnedDTO;
import com.hopin.HopIn.entities.Passenger;

public interface IPassengerService {
	
	public UserReturnedDTO insert(UserDTO passenger);
	
	public AllUsersDTO getAll();
	
	public Passenger getPassenger(int id);
	
	public boolean Activate(int id);

	UserReturnedDTO update(int id, UserDTO dto);
	
	public List<RouteDTO> getFavouriteRoutes(int id);
	
	public boolean removeFavouriteRoute(int passengerId, int routeId);

	public boolean addFavouriteRoute(int passwordId, int routeId);

	UserReturnedDTO getById(int id);

	public Boolean verifyRegistration(String verificationCode);

	public Boolean resendVerificationMail(String verificationCode);

	public UserReturnedDTO getByEmail(String email);
}
