package com.hopin.HopIn.services.interfaces;

import java.util.List;

import com.hopin.HopIn.dtos.AllPassengerRidesDTO;
import com.hopin.HopIn.dtos.AllUsersDTO;
import com.hopin.HopIn.dtos.RideForReportDTO;
import com.hopin.HopIn.dtos.RouteDTO;
import com.hopin.HopIn.dtos.UserDTO;
import com.hopin.HopIn.dtos.UserDTOOld;
import com.hopin.HopIn.dtos.UserReturnedDTO;
import com.hopin.HopIn.entities.FavoriteRide;
import com.hopin.HopIn.entities.Passenger;
import com.hopin.HopIn.entities.Route;

public interface IPassengerService {
	
	public UserReturnedDTO insert(UserDTO passenger);
	
	public AllUsersDTO getAll();
	
	public Passenger getPassenger(int id);
	
	public boolean Activate(int id);

	UserReturnedDTO update(int id, UserDTO dto);
	
	public List<RouteDTO> getFavouriteRoutes(int id);
	
	public boolean removeFavouriteRoute(int passengerId, int routeId);

	public boolean returnFavouriteRoute(int passwordId, int routeId);

	UserReturnedDTO getById(int id);

	public Boolean verifyRegistration(String verificationCode);

	public Boolean resendVerificationMail(String verificationCode);

	public UserReturnedDTO getByEmail(String email);

	public List<FavoriteRide> getFavoriteRides(int id);

	boolean addFavoriteRide(int passengerId, FavoriteRide ride);

	Route addFavouriteRoute(int passengerId, RouteDTO dto);

	public Boolean isFavouriteRoute(int rideId);
	
	public AllPassengerRidesDTO getAllPassengerRidesPaginated(int id, int page, int size, String sort, String from, String to);

	public AllPassengerRidesDTO getAllPassengerRides(int id);

	public List<RideForReportDTO> getAllPassengerRidesBetweenDates(int id, String from, String to);
	
}
