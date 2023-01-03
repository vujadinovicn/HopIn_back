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
import com.hopin.HopIn.dtos.UserDTOOld;
import com.hopin.HopIn.dtos.UserReturnedDTO;
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

	private Map<Integer, User> allPassengerss = new HashMap<Integer, User>();
	private int currId = 0;

	@Override
	public AllUsersDTO getAll() {
		return new AllUsersDTO(this.allPassengers.findAll());
	}

	@Override
	public UserReturnedDTO getById(int id) {
		Optional<Passenger> found = allPassengers.findById(id);
		if (found.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
		}
		return new UserReturnedDTO(found.get());
	}

	@Override
	public UserReturnedDTO insert(UserDTO dto) {
		Passenger passenger = new Passenger(dto);
		allPassengers.save(passenger);
		allPassengers.flush();
		return new UserReturnedDTO(passenger);
	}

	@Override
	public List<RouteDTO> getFavouriteRoutes(int id) {
		List<Route> routes = allPassengers.findAllRoutesById(id);
		List<RouteDTO> res = new ArrayList<RouteDTO>();
		for (Route route : routes) {
			res.add(new RouteDTO(route));
		}
		return res;
	}

	@Override
	public UserReturnedDTO getPassenger(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean Activate(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public UserReturnedDTO update(int id, UserDTOOld dto) {
		Passenger passenger = this.allPassengers.findById(id).get();
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

	public boolean removeFavouriteRoute(int passwordId, int routeId) {
		Passenger passenger = this.allPassengers.findById(passwordId).get();
		if (passenger == null) {
			return false;
		}

		for (Route route : passenger.getFavouriteRoutes()) {
			if (route.getId() == routeId) {
				passenger.getFavouriteRoutes().remove(route);
				this.allPassengers.save(passenger);
				this.allPassengers.flush();
				return true;
			}
		}

		return false;

	}

	public boolean addFavouriteRoute(int passwordId, int routeId) {
		Passenger passenger = this.allPassengers.findById(passwordId).get();
		Optional<Route> route = this.allRoutes.findById(routeId);
		if (passenger == null || route == null) {
			return false;
		}
		passenger.getFavouriteRoutes().add(route.get());
		this.allPassengers.save(passenger);
		this.allPassengers.flush();
		return true;
	}

}
