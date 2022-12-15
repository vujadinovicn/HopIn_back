package com.hopin.HopIn.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.hopin.HopIn.dtos.AllUsersDTO;
import com.hopin.HopIn.dtos.RouteDTO;
import com.hopin.HopIn.dtos.UserDTO;
import com.hopin.HopIn.dtos.UserReturnedDTO;
import com.hopin.HopIn.entities.Location;
import com.hopin.HopIn.entities.Passenger;
import com.hopin.HopIn.entities.Route;
import com.hopin.HopIn.entities.User;
import com.hopin.HopIn.repositories.LocationRepository;
import com.hopin.HopIn.repositories.PassengerRepository;
import com.hopin.HopIn.repositories.RouteRepository;
import com.hopin.HopIn.services.interfaces.IPassengerService;

@Service
public class PassengerServiceImpl implements IPassengerService {
	
	@Autowired
	private PassengerRepository allPassengers;
	
	@Autowired 
	RouteRepository allRoutes;
	
	@Autowired
	LocationRepository allLocations;
	
	private Map<Integer, User> allPassengerss= new HashMap<Integer, User>();
	private int currId = 0;
	
	@Override
	public AllUsersDTO getAll() {
		return new AllUsersDTO(this.allPassengers.findAll());
	}
	
	@Override
	public UserReturnedDTO getPassenger(int id) {
		Optional<Passenger> found = allPassengers.findById(id);
		if (found.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
		}
		return new UserReturnedDTO(found.get());
	}
	
	@Override
	public UserReturnedDTO insert(UserDTO dto) {
		Passenger passenger = new Passenger(dto);
//		Location departure = new Location(1, "Novi Sad", 43.22222, 43.2222);
//		Location destination = new Location(2, "Novi Sad", 43.22222, 43.2222);
//		allLocations.save(departure);
//		allLocations.save(destination);
//		Route route = new Route(1, departure, destination, 20.0);
//		allRoutes.save(route);
//		allRoutes.flush();
//		passenger.getFavouriteRoutes().add(route);
		allPassengers.save(passenger);
		allPassengers.flush();
		return new UserReturnedDTO(passenger);
	}
	
	@Override
	public List<RouteDTO> getFavouriteRoutes(int id) {
		List<Route> routes = allPassengers.findAllRoutesById(id);
		List<RouteDTO> res = new ArrayList<RouteDTO>();
		for(Route route : routes) {
			res.add(new RouteDTO(route));
		}
		return res;
	}
	
	
	
	@Override
	public boolean Activate(int id) {
		User passenger = this.getById(id);
		if(passenger != null) {
			passenger.setActivated(true);
			return true;
		}
		return false;
	}
	
	@Override
	public UserReturnedDTO update(int id, UserDTO dto) {
		Passenger passenger = this.getById(id);
		if (passenger == null) {
			return null;
		}
		if (dto.getNewPassword() != "" && dto.getNewPassword() != null) {
			if (!this.checkPasswordMatch(passenger.getPassword(), dto.getPassword())) {
				System.out.println(passenger.getPassword());
				System.out.println(dto.getPassword());
				return null;	
			}
			dto.setPassword(dto.getNewPassword());
		}
		passenger.copy(dto);
		this.allPassengers.save(passenger);
		this.allPassengers.flush();
		return new UserReturnedDTO(passenger);
	}
	
	private boolean checkPasswordMatch(String password, String subbmitedPassword) {
		return password.equals(subbmitedPassword);
	}
	
	private Passenger getById(int passengerId) {
		Optional<Passenger> passenger = allPassengers.findById(passengerId);
		if (passenger.isEmpty()) {
			return null;
		}
		return passenger.get();
	}

}
