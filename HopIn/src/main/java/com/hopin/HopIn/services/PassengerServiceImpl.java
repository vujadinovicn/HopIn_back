package com.hopin.HopIn.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
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
import com.hopin.HopIn.enums.Role;
import com.hopin.HopIn.mail.IMailService;
import com.hopin.HopIn.repositories.LocationRepository;
import com.hopin.HopIn.repositories.PassengerRepository;
import com.hopin.HopIn.repositories.RouteRepository;
import com.hopin.HopIn.services.interfaces.IPassengerService;
import com.hopin.HopIn.services.interfaces.IUserService;

import net.bytebuddy.utility.RandomString;

@Service
public class PassengerServiceImpl implements IPassengerService {

	@Autowired
	private PassengerRepository allPassengers;
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private IMailService mailService;

	@Autowired
	RouteRepository allRoutes;

	@Autowired
	LocationRepository allLocations;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

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
		if (!Pattern.matches("^([0-9a-zA-Z]{6,}$)", dto.getPassword())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid password format!");
		}
		
		if (this.userService.userAlreadyExists(dto.getEmail())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User with that email already exists!");
		}
		
		Passenger passenger = new Passenger(dto);
		passenger.setPassword(passwordEncoder.encode(dto.getPassword()));
		passenger.setRole(Role.PASSENGER);
		
		setVerificationCodeAndExp(passenger);
		
		passenger = allPassengers.save(passenger);
		allPassengers.flush();
		
		this.mailService.sendVerificationMail(passenger);
		
		return new UserReturnedDTO(passenger);
	}

	private void setVerificationCodeAndExp(Passenger passenger) {
		String verficationCode = RandomString.make(64);
		passenger.setVerificationCode(verficationCode);
		passenger.setVerificationExp(Date.from(Instant.now().plus(2, ChronoUnit.HOURS)).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
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

	@Override
	public Boolean verifyRegistration(String verificationCode) {
		Passenger passenger = this.allPassengers.findPassengerByVerificationCode(verificationCode).orElse(null);
		
		if (passenger == null || passenger.isActivated() || passenger.getVerificationExp().isBefore(LocalDateTime.now())) {
			return false;
		}
		
		passenger.setActivated(true);
		this.allPassengers.save(passenger);
		this.allPassengers.flush();
		return true;
	}

	@Override
	public Boolean resendVerificationMail(String verificationCode) {
		Passenger passenger = this.allPassengers.findPassengerByVerificationCode(verificationCode).orElse(null);
		
		if (passenger == null || passenger.isActivated() || passenger.getVerificationExp().isAfter(LocalDateTime.now()))
			return false;
		
		setVerificationCodeAndExp(passenger);
		this.allPassengers.save(passenger);
		this.allPassengers.flush();
		
		this.mailService.sendVerificationMail(passenger);
		return true;
		
	}

}
