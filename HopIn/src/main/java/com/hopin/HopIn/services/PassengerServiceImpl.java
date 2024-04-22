package com.hopin.HopIn.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.hopin.HopIn.dtos.AllPassengerRidesDTO;
import com.hopin.HopIn.dtos.AllUsersDTO;
import com.hopin.HopIn.dtos.RideForReportDTO;
import com.hopin.HopIn.dtos.RouteDTO;
import com.hopin.HopIn.dtos.UserDTO;
import com.hopin.HopIn.dtos.UserDTOOld;
import com.hopin.HopIn.dtos.UserReturnedDTO;
import com.hopin.HopIn.entities.FavoriteRide;
import com.hopin.HopIn.entities.Passenger;
import com.hopin.HopIn.entities.Ride;
import com.hopin.HopIn.entities.Route;
import com.hopin.HopIn.entities.User;
import com.hopin.HopIn.enums.Role;
import com.hopin.HopIn.enums.SecureTokenType;

import com.hopin.HopIn.exceptions.EmailAlreadyInUseException;
import com.hopin.HopIn.exceptions.RideNotFoundException;
import com.hopin.HopIn.exceptions.UserNotFoundException;

import com.hopin.HopIn.exceptions.BadIdFormatException;

import com.hopin.HopIn.mail.IMailService;
import com.hopin.HopIn.repositories.LocationRepository;
import com.hopin.HopIn.repositories.PassengerRepository;
import com.hopin.HopIn.repositories.RideRepository;
import com.hopin.HopIn.repositories.RouteRepository;
import com.hopin.HopIn.services.interfaces.IPassengerService;
import com.hopin.HopIn.services.interfaces.IRideService;
import com.hopin.HopIn.services.interfaces.IUserService;
import com.hopin.HopIn.tokens.ISecureTokenService;
import com.hopin.HopIn.tokens.SecureToken;

@Service
public class PassengerServiceImpl implements IPassengerService {

	@Autowired
	private PassengerRepository allPassengers;
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private IMailService mailService;
	
	@Autowired
	private ISecureTokenService tokenService;
	
	@Autowired
	private RideRepository allRides;

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
	
//	@Override 
//	public Passenger getPassenger(int id) {
//		Optional<Passenger> found = allPassengers.findById(id);
//		if (found.isEmpty()) {
//			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
//		}
//		return found.get();
//	}
//	
	@Override
	public UserReturnedDTO getByEmail(String email) {
		Optional<Passenger> found = allPassengers.findPassengerByEmail(email);
		if (found.isEmpty() || found.get().isBlocked() || !found.get().isActivated()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
		}
		return new UserReturnedDTO(found.get());
	}

	@Override
	public UserReturnedDTO insert(UserDTO dto) {
		if (this.userService.userAlreadyExists(dto.getEmail())) {
			return null;
		}
		
		Passenger passenger = new Passenger(dto);
		passenger.setPassword(passwordEncoder.encode(dto.getPassword()));
		passenger.setRole(Role.PASSENGER);
		
		passenger = allPassengers.save(passenger);
		allPassengers.flush();
		
		SecureToken token = tokenService.createToken(passenger, SecureTokenType.REGISTRATION);
		
		this.mailService.sendVerificationMail(passenger, token.getToken());
		
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
	public List<FavoriteRide>  getFavoriteRides(int id) {
		return this.allPassengers.findAllFavoriteRidesById(id);
	}
	
	@Override
	public Passenger getPassenger(int id) {
		return this.allPassengers.findById(id).orElse(null);
	}

	@Override
	public boolean Activate(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public UserReturnedDTO update(int id, UserDTO dto) {
		Optional<Passenger> passenger = this.allPassengers.findById(id);
		if (passenger.isEmpty()) {
			throw new UserNotFoundException();
		}
//		if (this.userService.userAlreadyExists(dto.getEmail())) {
//			throw new EmailAlreadyInUseException();
//		}
//		if (dto.getNewPassword() != "" && dto.getNewPassword() != null) {
//			if (!this.checkPasswordMatch(passenger.getPassword(), dto.getPassword())) {
//				return null;
//			}
//			dto.setPassword(dto.getNewPassword());
//		}
		this.changeDtoEmailAndPasswordToPrevious(dto, passenger);
		passenger.get().copy(dto);
		this.allPassengers.save(passenger.get());
		this.allPassengers.flush();
		return new UserReturnedDTO(passenger.get());
	}

	private void changeDtoEmailAndPasswordToPrevious(UserDTO dto, Optional<Passenger> passenger) {
		dto.setPassword(passenger.get().getPassword());
		dto.setEmail(passenger.get().getEmail());
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

	public boolean returnFavouriteRoute(int passwordId, int routeId) {
		Passenger passenger = this.allPassengers.findById(passwordId).get();
		Optional<Route> route = this.allRoutes.findById(routeId);
		if (passenger == null || route.isEmpty()) {
			return false;
		}
		passenger.getFavouriteRoutes().add(route.get());
		this.allPassengers.save(passenger);
		this.allPassengers.flush();
		return true;
	}
	
	@Override
	public Route addFavouriteRoute(int passengerId, RouteDTO dto) {
		Passenger passenger = this.allPassengers.findById(passengerId).get();
		if (passenger == null) {
			return null;
		}
		Route route = new Route(dto);
		for (Route r: this.allRoutes.findAll()) {
			if (route.getDeparture().getLongitude() == r.getDeparture().getLongitude() && route.getDeparture().getLatitude() == r.getDeparture().getLatitude() &&
					route.getDestination().getLongitude() == r.getDestination().getLongitude() && route.getDestination().getLatitude() == r.getDestination().getLatitude()) {
				route = r;
			}
		}
		this.allRoutes.save(route);
		passenger.getFavouriteRoutes().add(route);
		this.allPassengers.save(passenger);
		this.allPassengers.flush();
		return route;
	}
	
	@Override
	public boolean addFavoriteRide(int passengerId, FavoriteRide ride) {
		Passenger passenger = this.allPassengers.findById(passengerId).get();
		if (passenger == null) {
			throw new BadIdFormatException();
		}
		passenger.getFavouriteRides().add(ride);
		this.allPassengers.save(passenger);
		this.allPassengers.flush();
		return true;
	}

	@Override
	public Boolean verifyRegistration(String verificationCode) {
		SecureToken token = this.tokenService.findByToken(verificationCode);
		
		if (token == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Activation with entered id does not exist!");
		}

		if (!this.tokenService.isValid(token) || token.isExpired() || token.getType() != SecureTokenType.REGISTRATION) {
			return false;
		}

		this.userService.activateUser(token.getUser());
		this.tokenService.markAsUsed(token);
		
		return true;
	}

	@Override
	public Boolean resendVerificationMail(String verificationCode) {
		SecureToken token = this.tokenService.findByToken(verificationCode);

		if (!this.tokenService.isValid(token)) {
			return false;
		}
		
		SecureToken newToken = tokenService.createToken(token.getUser(), SecureTokenType.REGISTRATION);
		Passenger passenger = this.allPassengers.findById(token.getUser().getId()).orElse(null);
		this.mailService.sendVerificationMail(passenger, newToken.getToken());

		return true;
		
	}

	@Override
	public Boolean isFavouriteRoute(int rideId) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Passenger passenger = allPassengers.findPassengerByEmail(authentication.getName()).orElse(null);
		
		if (passenger == null) {
			throw new UserNotFoundException();
		}
		
		Ride ride = this.allRides.findById(rideId).orElse(null);
		
		if (ride == null) {
			throw new RideNotFoundException();
		}
		
		List<RouteDTO> favRoutes = this.getFavouriteRoutes(passenger.getId());
		
		for (RouteDTO fav: favRoutes) {
			if (fav.getDeparture().getLatitude() == ride.getDepartureLocation().getLatitude() &&
					fav.getDeparture().getLongitude() == ride.getDepartureLocation().getLongitude() &&
					fav.getDestination().getLatitude() == ride.getDestinationLocation().getLatitude() &&
					fav.getDestination().getLongitude() == ride.getDestinationLocation().getLongitude()) {
				System.out.println(rideId + " true");
				return true;
			}
		}
		System.out.println(rideId + " false");
		return false;
	}

	@Override
	public AllPassengerRidesDTO getAllPassengerRidesPaginated(int id, int page, int size, String sort, String from,
			String to) {
		Pageable pageable = PageRequest.of(page, size);

		Optional<Passenger> passenger = this.allPassengers.findById(id);
		if (passenger.isEmpty()) {
			throw new UserNotFoundException();
		}

		List<Ride> rides = this.allRides.getAllPassengerRidesPaginated(id, pageable);
		return new AllPassengerRidesDTO(rides);
	}
	
	@Override
	public AllPassengerRidesDTO getAllPassengerRides(int id) {
		Optional<Passenger> passenger = this.allPassengers.findById(id);
		if (passenger.isEmpty()) {
			throw new UserNotFoundException();
		}

		List<Ride> rides = this.allRides.getAllPassengerRides(id);
		return new AllPassengerRidesDTO(rides);
	}

	@Override
	public List<RideForReportDTO> getAllPassengerRidesBetweenDates(int id, String from, String to) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		LocalDateTime start = LocalDate.parse(from, formatter).atStartOfDay();
		LocalDateTime end = LocalDate.parse(to, formatter).atStartOfDay().plusDays(1);
		List<Ride> rides = allRides.getAllPassengerRidesBetweenDates(id, start, end);
		List<RideForReportDTO> res = new ArrayList<RideForReportDTO>();
		for (Ride ride : rides) {
			res.add(new RideForReportDTO(ride));
		}
		return res;
	}

}
