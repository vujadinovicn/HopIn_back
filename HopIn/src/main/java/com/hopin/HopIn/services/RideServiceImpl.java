package com.hopin.HopIn.services;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.hopin.HopIn.dtos.AllPanicRidesDTO;
import com.hopin.HopIn.dtos.AllPassengerRidesDTO;

import com.hopin.HopIn.dtos.FavoriteRideDTO;
import com.hopin.HopIn.dtos.FavoriteRideReturnedDTO;

import com.hopin.HopIn.dtos.LocationNoIdDTO;
import com.hopin.HopIn.dtos.PanicRideDTO;
import com.hopin.HopIn.dtos.ReasonDTO;
import com.hopin.HopIn.dtos.RideDTO;
import com.hopin.HopIn.dtos.RideForReportDTO;
import com.hopin.HopIn.dtos.RideOfferEstimationDTO;
import com.hopin.HopIn.dtos.RideReturnedDTO;
import com.hopin.HopIn.dtos.UnregisteredRideSuggestionDTO;
import com.hopin.HopIn.dtos.UserInRideDTO;
import com.hopin.HopIn.entities.Driver;

import com.hopin.HopIn.entities.FavoriteRide;

import com.hopin.HopIn.entities.Location;

import com.hopin.HopIn.entities.Panic;
import com.hopin.HopIn.entities.Passenger;
import com.hopin.HopIn.entities.RejectionNotice;
import com.hopin.HopIn.entities.Ride;
import com.hopin.HopIn.entities.User;
import com.hopin.HopIn.entities.VehicleType;
import com.hopin.HopIn.enums.RideStatus;
import com.hopin.HopIn.enums.Role;
import com.hopin.HopIn.enums.VehicleTypeName;
import com.hopin.HopIn.exceptions.FavoriteRideException;
import com.hopin.HopIn.exceptions.NoActiveDriverException;

import com.hopin.HopIn.repositories.FavoriteRideRepository;
import com.hopin.HopIn.repositories.PanicRepository;
import com.hopin.HopIn.repositories.PassengerRepository;

import com.hopin.HopIn.exceptions.NoActiveDriverRideException;
import com.hopin.HopIn.exceptions.NoActivePassengerRideException;
import com.hopin.HopIn.exceptions.NoAvailableDriversException;
import com.hopin.HopIn.exceptions.NoDriverWithAppropriateVehicleForRideException;
import com.hopin.HopIn.exceptions.NoRideAfterFiveHoursException;
import com.hopin.HopIn.exceptions.PassengerAlreadyInRideException;
import com.hopin.HopIn.exceptions.PassengerHasAlreadyPendingRide;
import com.hopin.HopIn.exceptions.RideNotFoundException;
import com.hopin.HopIn.exceptions.UserNotFoundException;
import com.hopin.HopIn.repositories.RideRepository;
import com.hopin.HopIn.repositories.UserRepository;
import com.hopin.HopIn.repositories.VehicleTypeRepository;
import com.hopin.HopIn.services.interfaces.IDriverService;
import com.hopin.HopIn.services.interfaces.IPassengerService;

import com.hopin.HopIn.services.interfaces.IRideService;
import com.hopin.HopIn.services.interfaces.IUserService;
import com.hopin.HopIn.services.interfaces.IVehicleTypeService;
import com.hopin.HopIn.util.TokenUtils;

import com.hopin.HopIn.services.interfaces.IRideEstimationService;

import jakarta.validation.constraints.Min;
import com.hopin.HopIn.services.interfaces.IWorkingHoursService;

@Service
public class RideServiceImpl implements IRideService {

	@Autowired
	private PanicRepository allPanics;

	@Autowired
	private RideRepository allRides;

	@Autowired
	private UserRepository allUsers;

	@Autowired
	private FavoriteRideRepository allFavoriteRides;

	@Autowired
	private VehicleTypeRepository allVehicleTypes;

	@Autowired
	private IDriverService driverService;

	@Autowired
	private IVehicleTypeService vehicleTypeService;

	@Autowired
	private IPassengerService passengerService;

	@Autowired
	private IUserService userService;

	@Autowired
	private IRideEstimationService rideEstimationService;

	@Autowired
	private IWorkingHoursService workingHoursService;

	private Map<Integer, Ride> allRidess = new HashMap<Integer, Ride>();
	private Set<PanicRideDTO> allPanicRides = new HashSet<PanicRideDTO>();
	private int currId = 0;

	@Autowired
	private PassengerRepository allPassengers;	

	@Override
	public List<RideForReportDTO> getAllRidesBetweenDates(String from, String to) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		
		List<Ride> rides = new ArrayList<>();
		LocalDateTime fromDate;
		LocalDateTime toDate;
		try {
			fromDate = LocalDate.parse(from, formatter).atStartOfDay();
			toDate = LocalDate.parse(to, formatter).atStartOfDay();
			
			if (toDate.isBefore(fromDate)) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "End of range date must be after start of range date!");
			}
			
			rides = allRides.getAllRidesBetweenDates(fromDate, toDate);
		} catch (DateTimeParseException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong date format! Use yyyy/MM/dd.");
		}
		
		List<RideForReportDTO> res = new ArrayList<RideForReportDTO>();
		for (Ride ride : rides) {
			res.add(new RideForReportDTO(ride));
		}
		return res;
	}

	@Override
	public FavoriteRideReturnedDTO insertFavoriteRide(FavoriteRideDTO dto) {
		User user = userService.getCurrentUser();
		
		System.out.println(user.getId());
		System.out.println(passengerService.getFavoriteRides(user.getId()).size());
		
		if (passengerService.getFavoriteRides(user.getId()).size() >= 10) {
			throw new FavoriteRideException();
		}
		
		Set<Passenger> passengers = new HashSet<Passenger>();
		
		Boolean currentUserIsIn = false;
		for (UserInRideDTO currUser : dto.getPassengers()) {
			passengers.add(this.passengerService.getPassenger(currUser.getId()));
			if (user.getId() == currUser.getId()) {
				currentUserIsIn = true;
			}
		}
		
		if (!currentUserIsIn) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Logged in user is not in the ride!");
		}

		FavoriteRide favoriteRide = new FavoriteRide(dto, passengers,
				this.vehicleTypeService.getByName(dto.getVehicleType()));
		this.allFavoriteRides.save(favoriteRide);
		this.allFavoriteRides.flush();
		this.passengerService.addFavoriteRide(user.getId(), favoriteRide);
		this.allPassengers.save(allPassengers.findPassengerByEmail(user.getEmail()).orElse(null));
		this.allPassengers.flush();

		return new FavoriteRideReturnedDTO(favoriteRide);
	}

	@Override
	public List<FavoriteRideReturnedDTO> getFavoriteRides() {
		List<FavoriteRide> favoriteRides = this.passengerService.getFavoriteRides(userService.getCurrentUser().getId());
		List<FavoriteRideReturnedDTO> retRides = new ArrayList<FavoriteRideReturnedDTO>();
		System.out.println("rides");
		for (FavoriteRide ride : favoriteRides) {
			retRides.add(new FavoriteRideReturnedDTO(ride));
		}
		System.out.println(retRides.size());
		return retRides;
	}

	@Override
	public void deleteFavoriteRide(int id) {
		Optional<FavoriteRide> found = allFavoriteRides.findById(id);
		Passenger passenger = passengerService.getPassenger(userService.getCurrentUser().getId());

		if (found.isEmpty()) {
			throw new FavoriteRideException();
		}

		passenger.getFavouriteRides().remove(found.get());
		allFavoriteRides.delete(found.get());
		allFavoriteRides.flush();
	}

	@Override
	public RideReturnedDTO add(RideDTO dto) {
		Driver driverForRide = new Driver();

		List<Driver> driversForRide = new ArrayList<Driver>();

		driversForRide = this.driverService.getActiveDrivers();
		if (driversForRide.size() == 0) {
			throw new NoActiveDriverException();
		}

		driversForRide = this.driverService.getDriversWithAppropriateVehicleForRide(driversForRide, dto);
		if (driversForRide.size() == 0) {
			throw new NoDriverWithAppropriateVehicleForRideException();
		}

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Passenger pass = allPassengers.findPassengerByEmail(authentication.getName()).orElse(null);
		dto.getPassengers().add(new UserInRideDTO(pass));
		//System.out.println(dto.getPassengers().get(0).getId());
		if (this.getPendingRideForPassenger(dto.getPassengers().get(0).getId()) != null) {
			throw new PassengerHasAlreadyPendingRide();
		}

		if (dto.getScheduledTime() == null) {

			dto.getPassengers().forEach((UserInRideDTO passenger) -> {
				try {
					if (this.getActiveRideForPassenger(passenger.getId()) != null) {
						throw new PassengerAlreadyInRideException();
					}
				} catch (NoActivePassengerRideException e) {
					e.printStackTrace();
				}
			});

			int newRideDuration = this.rideEstimationService.getEstimatedTime(dto.getDepartureLocation(),
					dto.getDestinationLocation());

			List<Driver> driversWithNoActiveRide = this.getAllDriversWithNoActiveRide(driversForRide);
			List<Driver> driversWithActiveRide = new ArrayList<Driver>(driversForRide);
			driversWithActiveRide.removeAll(driversWithNoActiveRide);
			//System.out.println(driversForRide);
			boolean availabilityOfDrivers = driversWithNoActiveRide.size() != 0 ? true : false;

			if (availabilityOfDrivers) {
				driverForRide = this.getBestDriver(dto, driversWithNoActiveRide, newRideDuration,
						availabilityOfDrivers);
			} else {
				driverForRide = this.getBestDriver(dto, driversWithActiveRide, newRideDuration, availabilityOfDrivers);
			}

		} else {
			if (dto.getScheduledTime().minusHours(5).isAfter(LocalDateTime.now()))
				throw new NoRideAfterFiveHoursException();

			List<Driver> driversWithNoUpcomingRide = new ArrayList<Driver>();
			for (Driver driver : driversForRide) {
				if (this.allRides.getFirstUpcomingRideForDriver(currId) == null)
					driversWithNoUpcomingRide.add(driver);
			}

			if (driversWithNoUpcomingRide.size() == 0)
				throw new NoAvailableDriversException();

			dto.getPassengers().forEach((UserInRideDTO passenger) -> {
				LocalDateTime startOfToday = LocalDate.now().atStartOfDay();
				LocalDateTime end = startOfToday.plusDays(1);

				List<Ride> passengersRides = this.allRides.getAllScheduledRideForTodayForPassenger(passenger.getId(), end);
				for (Ride ride : passengersRides) {
					if (ride.getScheduledTime().plusMinutes(ride.getEstimatedTimeInMinutes()).isAfter(
							dto.getScheduledTime()) && ride.getScheduledTime().isBefore(dto.getScheduledTime()))
						throw new PassengerAlreadyInRideException();
				}
			});

			int newRideDuration = this.rideEstimationService.getEstimatedTime(dto.getDepartureLocation(),
					dto.getDestinationLocation());

			int bestTime = Integer.MAX_VALUE;
			for (Driver driver : driversWithNoUpcomingRide) {
				System.out.println(newRideDuration);
				double workingHoursWithNewRide = this.workingHoursService
						.getWorkedHoursForTodayWithNewRide(driver.getId(), newRideDuration);
				double workingHoursOfScheduledRides = this.getWorkingHoursOfAllScheduledRideForDay(driver.getId());
				double totalWorkingHours = workingHoursOfScheduledRides + workingHoursWithNewRide;
				System.out.println(totalWorkingHours);
				if (totalWorkingHours < 8 && totalWorkingHours < bestTime) {
					bestTime = (int) totalWorkingHours;
					driverForRide = driver;
				}
			}
		}

		if (driverForRide.getId() == 0)
			throw new NoAvailableDriversException();

		Ride wantedRide = this.createWantedRide(dto, driverForRide);
		this.allRides.save(wantedRide);
		this.allRides.flush();

		RideReturnedDTO res = new RideReturnedDTO(wantedRide);
		res.setDistanceFormatted(dto.getDistanceFormatted());
		res.setDurationFormatted(dto.getDurationFormatted());

		return res;
	}

	private Driver getBestDriver(RideDTO rideDTO, List<Driver> drivers, int newRideDuration, boolean availability) {
		Driver foundDriver = new Driver();
		int bestTime = Integer.MAX_VALUE;

		for (Driver driver : drivers) {
			LocalDateTime startOfDrivingToDeparture = LocalDateTime.now();
			int timeForNewRideDepartureArrival = 0;

			if (availability) {
				timeForNewRideDepartureArrival = this.rideEstimationService
						.getEstimatedTimeForVehicleLocation(rideDTO.getDepartureLocation(), driver.getVehicle());
				//System.out.println(rideDTO.getDepartureLocation());
				//System.out.println(driver.getVehicleLocation());
				//System.out.println("Hashcode :       "+driver.getVehicleLocation().hashCode());
				System.out.println(timeForNewRideDepartureArrival);
			}
			else {
				RideReturnedDTO currentRide = this.getActiveRideForDriver(driver.getId());
				
				if (currentRide.getStartTime() == null) {
					startOfDrivingToDeparture = LocalDateTime.now();
					startOfDrivingToDeparture.plusMinutes(currentRide.getEstimatedTimeInMinutes()-1);
				} else {
					startOfDrivingToDeparture = currentRide.getStartTime()
							.plusMinutes(currentRide.getEstimatedTimeInMinutes());
				}
				
				timeForNewRideDepartureArrival = this.rideEstimationService.getEstimatedTime(
						rideDTO.getDepartureLocation(), currentRide.getLocations().get(0).getDestination());
				System.out.println(timeForNewRideDepartureArrival);
			}

			Ride nextRide = this.allRides.getFirstUpcomingRideForDriver(driver.getId());

			int timeFromStartOfNewToStartOfNext = timeForNewRideDepartureArrival + newRideDuration;

			if (nextRide != null) {

				if (startOfDrivingToDeparture.plusMinutes(timeFromStartOfNewToStartOfNext)
						.isAfter(nextRide.getScheduledTime())) {
					System.out.println("ne moze ovaj driver" + driver.getId());
					continue;

				}
			}
			int totalTimeForDepartureArrival = timeForNewRideDepartureArrival;
			if (!availability)
				totalTimeForDepartureArrival += this.getMinutesUntilEndOfCurrentRideForDriver(driver);
			
			double workingHoursWithNewRide = this.workingHoursService.getWorkedHoursForTodayWithNewRide(driver.getId(),
					timeFromStartOfNewToStartOfNext);
			double workingHorusOfScheduledRides = this.getWorkingHoursOfAllScheduledRideForDay(driver.getId());
			double totalWorkingHours = workingHorusOfScheduledRides + workingHoursWithNewRide;
			
			if (!availability)
				totalWorkingHours += this.getMinutesUntilEndOfCurrentRideForDriver(driver) / 60;

			if (totalWorkingHours < 8 && totalTimeForDepartureArrival < bestTime) {
				foundDriver = driver;
				bestTime = (int) (totalTimeForDepartureArrival);
			}

		}
		return foundDriver;
	}

	private Ride createWantedRide(RideDTO rideDTO, Driver driver) {
		Ride ride = new Ride();

		ride.setStartTime(null);
		ride.setEndTime(null);

		ride.setScheduledTime(rideDTO.getScheduledTime());

		ride.setPetTransport(rideDTO.isPetTransport());
		ride.setBabyTransport(rideDTO.isBabyTransport());
		ride.setPanic(false);

		ride.setStatus(RideStatus.PENDING);

		for (UserInRideDTO passenger : rideDTO.getPassengers()) {
			ride.getPassengers().add(this.passengerService.getPassenger(passenger.getId()));
		}
		ride.setDriver(driver);

		ride.setReviews(null);
		VehicleType vehicleType = this.vehicleTypeService.getByName(rideDTO.getVehicleType());

		ride.setVehicleType(vehicleType);
		ride.setDepartureLocation(new Location(rideDTO.getDepartureLocation()));
		ride.setDestinationLocation(new Location(rideDTO.getDestinationLocation()));
		ride.setRejectionNotice(null);

		int estimatedTimeInMinutes = this.rideEstimationService.getEstimatedTime(rideDTO.getDepartureLocation(),
				rideDTO.getDestinationLocation());
		double distance = this.rideEstimationService.getEstimatedDistance(rideDTO.getDepartureLocation(),
				rideDTO.getDestinationLocation());

		ride.setDistance(distance);
		ride.setTotalDistance(distance);
		ride.setTotalCost(calculatePrice(distance, rideDTO.getVehicleType().toString()));
		ride.setEstimatedTimeInMinutes(estimatedTimeInMinutes);

		return ride;
	}

	private double calculatePrice(double distance, String vehicleTypeName) {
		VehicleType vehicleType = this.vehicleTypeService
				.getByName(VehicleTypeName.valueOf(VehicleTypeName.class, vehicleTypeName));
		return vehicleType.getPricePerKm() * distance;
	}

	private double getMinutesUntilEndOfCurrentRideForDriver(Driver driver) {
		RideReturnedDTO currentRide = this.getActiveRideForDriver(driver.getId());
		LocalDateTime now = LocalDateTime.now();
		return ChronoUnit.MINUTES.between(now,
				currentRide.getStartTime().plusMinutes(currentRide.getEstimatedTimeInMinutes()));
	}

	private double getWorkingHoursOfAllScheduledRideForDay(int driverId) {
		LocalDateTime startOfToday = LocalDate.now().atStartOfDay();
		LocalDateTime end = startOfToday.plusDays(1);
		List<Ride> scheduledRides = this.allRides.getAllScheduledRideForTodayForDriver(driverId, end);
		int minutes = 0;
		if (scheduledRides != null) {
			for (Ride ride : scheduledRides) {
				minutes += ride.getEstimatedTimeInMinutes();
			}
		}

		DecimalFormat df = new DecimalFormat("#.##");
		return Double.valueOf(df.format(minutes / 60));
	}

	@Override
	public RideReturnedDTO getActiveRideForPassenger(int id) {
		Passenger passenger = this.allPassengers.findById(id).orElse(null);
		
		if (passenger == null)
			throw new UserNotFoundException();
		
		Ride activeRide = this.allRides.getActiveRideForPassenger(id);
		if (activeRide == null)
			throw new NoActivePassengerRideException();
		return new RideReturnedDTO(activeRide);
	}
	
	@Override
	public RideReturnedDTO getPendingRideForPassenger(int id) {
		Passenger passenger = this.allPassengers.findById(id).orElse(null);
		System.out.println(id);
		if (passenger == null)
			throw new UserNotFoundException();
		
		Ride pendingRide = this.allRides.getPendingRideForPassenger(id);
		if (pendingRide != null) {
			return new RideReturnedDTO(pendingRide);
		}
		return null;
	}

	@Override
	public RideReturnedDTO getPendingRideForDriver(int id) {
		Ride pendingRide = this.allRides.getPendingRideForDriver(id);
		if (pendingRide == null)
			throw new NoActiveDriverRideException();
		return new RideReturnedDTO(pendingRide);
	}

	@Override
	public RideReturnedDTO getActiveRideForDriver(int id) {
		Ride activeRide = this.allRides.getActiveRideForDriver(id);
		if (activeRide == null)
			throw new NoActiveDriverRideException();
		return new RideReturnedDTO(activeRide);
	}

	private List<Driver> getAllDriversWithNoActiveRide(List<Driver> drivers) {
		List<Driver> availableDrivers = new ArrayList<Driver>();
		for (Driver driver : drivers) {
			try {
				if (this.getActiveRideForDriver(driver.getId()) == null)
					availableDrivers.add(driver);
			} catch (NoActiveDriverRideException e) {
				availableDrivers.add(driver);
				e.printStackTrace();
			}
		}

		return availableDrivers;
	}

	@Override
	public RideReturnedDTO getRide(int id) {
		Ride ride = this.allRides.findById(id).orElse(null);
		if (ride == null)
			throw new RideNotFoundException();
		return new RideReturnedDTO(ride);
	}

	private Ride getRideIfExists(int id) {
		Optional<Ride> ret = this.allRides.findById(id);
		if (ret.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ride does not exist!");
		}
		return ret.get();
	}

	private Ride changeRideStatus(Ride ride, RideStatus status) {
		ride.setStatus(status);
		Ride savedRide = this.allRides.save(ride);
		this.allRides.flush();

		return savedRide;
	}

	@Override
	public RideReturnedDTO cancelRide(int id) {
		Ride ride = this.getRideIfExists(id);

		if (ride.getStatus() != RideStatus.PENDING && ride.getStatus() != RideStatus.STARTED) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Cannot cancel a ride that is not in status PENDING or STARTED!");
		}

		Ride savedRide = this.changeRideStatus(ride, RideStatus.CANCELED);

		return new RideReturnedDTO(savedRide);
	}

	@Override
	public PanicRideDTO panicRide(int id, ReasonDTO reason) {
		Ride ride = this.allRides.findById(id).orElse(null);

		if (ride == null) {
			return null;
		}

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = allUsers.findByEmail(authentication.getName()).orElse(null);

		Panic panic = new Panic(LocalDateTime.now(), reason.getReason(), user, ride);
		this.allPanics.save(panic);
		this.allPanics.flush();

		return new PanicRideDTO(panic);
	}

	@Override
	public RideReturnedDTO startRide(int id) {
		Ride ride = this.getRideIfExists(id);

		if (ride.getStatus() != RideStatus.ACCEPTED) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Cannot start a ride that is not in status ACCEPTED!");
		}

		ride.setStartTime(LocalDateTime.now());
		Ride savedRide = this.changeRideStatus(ride, RideStatus.STARTED);

		return new RideReturnedDTO(savedRide);
	}

	@Override
	public RideReturnedDTO acceptRide(int id) {
		Ride ride = this.getRideIfExists(id);

		if (ride.getStatus() != RideStatus.PENDING) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Cannot accept a ride that is not in status PENDING!");
		}

		Ride savedRide = this.changeRideStatus(ride, RideStatus.ACCEPTED);

		return new RideReturnedDTO(savedRide);
	}

	@Override
	public RideReturnedDTO finishRide(int id) {
		Ride ride = this.getRideIfExists(id);

		if (ride.getStatus() != RideStatus.STARTED) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Cannot end a ride that is not in status STARTED!");
		}

		ride.setEndTime(LocalDateTime.now());
		Ride savedRide = this.changeRideStatus(ride, RideStatus.FINISHED);

		return new RideReturnedDTO(savedRide);
	}

	@Override
	public RideReturnedDTO rejectRide(int id, ReasonDTO reason) {
		Ride ride = this.getRideIfExists(id);

		if (ride.getStatus() != RideStatus.PENDING && ride.getStatus() != RideStatus.ACCEPTED) {
			// MOZE LI DA SE PROMENI PORUKA OVDE, NEMA RAZLIKE IZMEDJU CANCEL PUTNIKA I
			// REJECT VOZACA OVAKO, BUDE ZBUNJUJUCE?
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Cannot cancel a ride that is not in status PENDING or ACCEPTED!");
		}

		ride.setRejectionNotice(new RejectionNotice(reason.getReason()));
		Ride savedRide = this.changeRideStatus(ride, RideStatus.REJECTED);

		return new RideReturnedDTO(savedRide);
	}

	@Override
	public Double getRideSugestionPrice(UnregisteredRideSuggestionDTO dto) {
		VehicleType vehicleType = this.allVehicleTypes
				.getByName(VehicleTypeName.valueOf(VehicleTypeName.class, dto.getVehicleTypeName()));
		return vehicleType.getPricePerKm() * dto.getDistance();
	}

	@Override
	public List<Ride> getAllAcceptedRides() {
		return this.allRides.getAllAcceptedRides();
	}

	@Override
	public RideReturnedDTO startRideToDeparture(int id) {
		Ride ride = this.getRideIfExists(id);
		
		if (ride.getStatus() != RideStatus.ACCEPTED) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Cannot start ride to departure, status must be ACCEPTED!");
		}
		
		if (ride.getScheduledTime() == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Cannot start ride to departure, scheduled time must not be null!");
		}
		
		ride.setScheduledTime(null);
		Ride savedRide = this.allRides.save(ride);
		this.allRides.flush();
		return new RideReturnedDTO(savedRide);
	}

	@Override
	public List<RideReturnedDTO> getScheduledRidesForUser(int userId) {
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Optional<User> userOptional = allUsers.findById(userId);
		if (userOptional.isEmpty())
			throw new UserNotFoundException();
		User user = userOptional.get();
		List<Ride> rides;
		if (user.getRole() == Role.DRIVER) {
			rides = this.allRides.getScheduledRidesForDriver(userId);
		} else {
			rides = this.allRides.getScheduledRidesForPassenger(userId);
		}

		List<RideReturnedDTO> res = new ArrayList<RideReturnedDTO>();
		for (Ride ride : rides) {
			res.add(new RideReturnedDTO(ride));
		}
		return res;
	}
}
