package com.hopin.HopIn.services;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.hopin.HopIn.HopInApplication;
import com.hopin.HopIn.dtos.LocationDTO;
import com.hopin.HopIn.dtos.LocationNoIdDTO;
import com.hopin.HopIn.dtos.RideDTO;
import com.hopin.HopIn.dtos.RideReturnedDTO;
import com.hopin.HopIn.dtos.UserInRideDTO;
import com.hopin.HopIn.entities.Driver;
import com.hopin.HopIn.entities.Location;
import com.hopin.HopIn.entities.Passenger;
import com.hopin.HopIn.entities.Ride;
import com.hopin.HopIn.entities.Vehicle;
import com.hopin.HopIn.entities.VehicleType;
import com.hopin.HopIn.enums.RideStatus;
import com.hopin.HopIn.enums.Role;
import com.hopin.HopIn.enums.VehicleTypeName;
import com.hopin.HopIn.exceptions.NoActiveDriverException;
import com.hopin.HopIn.exceptions.NoAvailableDriversException;
import com.hopin.HopIn.exceptions.NoDriverWithAppropriateVehicleForRideException;
import com.hopin.HopIn.exceptions.NoRideAfterFiveHoursException;
import com.hopin.HopIn.exceptions.PassengerAlreadyInRideException;
import com.hopin.HopIn.exceptions.PassengerHasAlreadyPendingRide;
import com.hopin.HopIn.repositories.FavoriteRideRepository;
import com.hopin.HopIn.repositories.PanicRepository;
import com.hopin.HopIn.repositories.PassengerRepository;
import com.hopin.HopIn.repositories.RideRepository;
import com.hopin.HopIn.repositories.UserRepository;
import com.hopin.HopIn.repositories.VehicleTypeRepository;
import com.hopin.HopIn.services.interfaces.IDriverService;
import com.hopin.HopIn.services.interfaces.IPassengerService;
import com.hopin.HopIn.services.interfaces.IRideEstimationService;
import com.hopin.HopIn.services.interfaces.IRideService;
import com.hopin.HopIn.services.interfaces.IUserService;
import com.hopin.HopIn.services.interfaces.IVehicleTypeService;
import com.hopin.HopIn.services.interfaces.IWorkingHoursService;

@ActiveProfiles("test")
@SpringBootTest(classes = HopInApplication.class)
@TestPropertySource(properties = "classpath:application-test.properties")
public class RideOrderServiceTest extends AbstractTestNGSpringContextTests{

	@SpyBean
	private IRideService rideService;

	@MockBean
	private IPassengerService passengerService;
	
	@MockBean
	private IDriverService driverService;

	@MockBean
	private IVehicleTypeService vehicleTypeService;

	@MockBean
	private IUserService userService;
	
	@MockBean
	private IRideEstimationService rideEstimationService;
	
	@MockBean
	private IWorkingHoursService workingHoursService;

	@MockBean
	private RideRepository allRides;

	@MockBean
	private FavoriteRideRepository allFavoriteRides;

	@MockBean
	private PassengerRepository allPassengers;

	@MockBean
	private VehicleTypeRepository allVehicleTypes;

	@MockBean
	private UserRepository allUsers;

	@MockBean
	private PanicRepository allPanics;

	@MockBean
	private Authentication authentication;

	@MockBean
	private SecurityContext context;
	
	private static final String INVALID_EMAIL = "random email";
	private static final LocalDateTime FIVE_HOURS = LocalDateTime.now().plusHours(5).plusMinutes(1);
	private static final int PLUS_HOURS = 3;

	private Ride ride;
	private Ride rideNotChosen;
	private Ride rideNext;
	private Driver driver;
	private Vehicle driverVehicle;
	private Driver driverNotChosen;
	private Vehicle driverNotChosenVehicle;
	private Passenger passenger;
	private VehicleType vehicleType;
	private RideDTO rideDTO;
	private Location driverNotChosenVehicleLocation;
	private Location driverVehicleLocation;

	@BeforeMethod
	public void setup() {
		driver = new Driver(1, "Pera", "Peric", "pera@gmail.com", "123", "Adresa", "4384294", null);
		driverNotChosen = new Driver(3, "Mika", "Mikic", "mika@gmail.com", "123", "Adresa", "4384294", null);
		passenger = new Passenger(2, "Lara", "Peric", "lara@gmail.com", "123", "Adresa", "4384294", null, null, null);
		

		vehicleType = new VehicleType(1, VehicleTypeName.CAR, 60);
		driverNotChosenVehicleLocation = new Location(2, "Opa", 45.3912, 25.233);
		driverVehicleLocation = new Location(1, "Opa", 44.3212, 24.133);
		
		driverVehicle = new Vehicle(1, 1, "audi a6", "bp-030-hr", 5, true, true, vehicleType, driverVehicleLocation);
		driverNotChosenVehicle = new Vehicle(2, 1, "audi a6", "bp-030-hr", 5, true, true, vehicleType, driverNotChosenVehicleLocation);
		
		driver.setVehicle(driverVehicle);
		driverNotChosen.setVehicle(driverNotChosenVehicle);
		
		Location departure = new Location(3, "Jirecekova 1", 45.32, 24.3);
		Location destination = new Location(4, "Promenada", 45.32, 24.3);

		driver.setRole(Role.DRIVER);
		driverNotChosen.setRole(Role.DRIVER);
		passenger.setRole(Role.PASSENGER);

		Set<Passenger> passengers = new HashSet<>();
		passengers.add(passenger);

		ride = new Ride();
		ride.setId(1);
		ride.setStatus(RideStatus.ACCEPTED);
		ride.setDepartureLocation(driverVehicleLocation);
		ride.setDestinationLocation(driverNotChosenVehicleLocation);
		ride.setDriver(driver);
		ride.setPassengers(passengers);
		ride.setVehicleType(vehicleType);
		
		rideNotChosen = new Ride();
		rideNotChosen.setId(2);
		rideNotChosen.setStatus(RideStatus.ACCEPTED);
		rideNotChosen.setDepartureLocation(departure);
		rideNotChosen.setDestinationLocation(destination);
		rideNotChosen.setDriver(driver);
		rideNotChosen.setPassengers(passengers);
		rideNotChosen.setVehicleType(vehicleType);
		
		rideNext = new Ride();
		rideNext.setId(3);
		rideNext.setStatus(RideStatus.ACCEPTED);
		rideNext.setDepartureLocation(departure);
		rideNext.setDestinationLocation(destination);
		rideNext.setDriver(driver);
		rideNext.setPassengers(passengers);
		rideNext.setVehicleType(vehicleType);
		
		List<LocationDTO> locationsDTO = new ArrayList<LocationDTO>();
		locationsDTO.add(new LocationDTO(new LocationNoIdDTO(departure), new LocationNoIdDTO(destination)));
		List<UserInRideDTO> usersDTO = new ArrayList<UserInRideDTO>();
		rideDTO = new RideDTO(locationsDTO, usersDTO, VehicleTypeName.CAR, true, true);
		
		MockitoAnnotations.initMocks(this);
	}
	
	@Test(expectedExceptions = {NoActiveDriverException.class})
	public void shouldThrowNoActiveDriverException_ForEmptyActiveDriversList_Add() {
		List<Driver> driversForRide = new ArrayList<>();
		Mockito.when(driverService.getActiveDrivers()).thenReturn(driversForRide);
		this.rideService.add(rideDTO);
		verifyNoInteractions(this.rideService);
	}
	
	@Test(expectedExceptions = {NoDriverWithAppropriateVehicleForRideException.class})
	public void shouldThrowNoDriverWithAppropriateVehicleForRideException_ForEmptyDriversList_Add() {
		List<Driver> driversForRide = new ArrayList<>();
		driversForRide.add(driver);
		Mockito.when(driverService.getActiveDrivers()).thenReturn(driversForRide);
		List<Driver> driversWithAppropriateVehicle = new ArrayList<>();
		Mockito.when(driverService.getDriversWithAppropriateVehicleForRide(driversForRide, null)).thenReturn(driversWithAppropriateVehicle);
		this.rideService.add(rideDTO);
		verifyNoInteractions(this.rideService);
	}
	
	@Test(expectedExceptions = {NullPointerException.class})
	public void shouldThrowNullPointerException_ForNotExistingPassenger_Add() {
		List<Driver> driversForRide = new ArrayList<>();
		driversForRide.add(driver);
		Mockito.when(driverService.getActiveDrivers()).thenReturn(driversForRide);
		Mockito.when(driverService.getDriversWithAppropriateVehicleForRide(driversForRide, rideDTO)).thenReturn(driversForRide);

		Mockito.when(context.getAuthentication()).thenReturn(authentication);
		Mockito.when(authentication.getName()).thenReturn(INVALID_EMAIL);
		SecurityContextHolder.setContext(context);
		Mockito.when(allPassengers.findPassengerByEmail(INVALID_EMAIL)).thenReturn(Optional.empty());
		rideService.add(rideDTO);
	}
	
	@Test(expectedExceptions = {PassengerHasAlreadyPendingRide.class})
	public void shouldThrowPassengerHasAlreadyPendingRide_ForPassengerHavingPendingRide_Add() {
		List<Driver> driversForRide = new ArrayList<>();
		driversForRide.add(driver);
		Mockito.when(driverService.getActiveDrivers()).thenReturn(driversForRide);
		Mockito.when(driverService.getDriversWithAppropriateVehicleForRide(driversForRide, rideDTO)).thenReturn(driversForRide);

		Mockito.when(context.getAuthentication()).thenReturn(authentication);
		Mockito.when(authentication.getName()).thenReturn(passenger.getEmail());
		SecurityContextHolder.setContext(context);
		Mockito.when(allPassengers.findPassengerByEmail(passenger.getEmail())).thenReturn(Optional.of(passenger));
		doReturn(new RideReturnedDTO(ride)).when(rideService).getPendingRideForPassenger(passenger.getId());
		rideService.add(rideDTO);
		verifyNoInteractions(allRides);
	}
	
	@Test(expectedExceptions = {PassengerAlreadyInRideException.class})
	public void shouldThrowPassengerAlreadyInRideException_ForOneOfThePassengersInActiveRideAndScheduleTimeNull_Add() {
		rideDTO.setScheduledTime(null);
		
		List<Driver> driversForRide = new ArrayList<>();
		driversForRide.add(driver);
		Mockito.when(driverService.getActiveDrivers()).thenReturn(driversForRide);
		Mockito.when(driverService.getDriversWithAppropriateVehicleForRide(driversForRide, rideDTO)).thenReturn(driversForRide);

		Mockito.when(context.getAuthentication()).thenReturn(authentication);
		Mockito.when(authentication.getName()).thenReturn(passenger.getEmail());
		SecurityContextHolder.setContext(context);
		Mockito.when(allPassengers.findPassengerByEmail(passenger.getEmail())).thenReturn(Optional.of(passenger));
		
		doReturn(null).when(rideService).getPendingRideForPassenger(passenger.getId());
		doReturn(new RideReturnedDTO(ride)).when(rideService).getActiveRideForPassenger(passenger.getId());
		rideService.add(rideDTO);
		verifyNoInteractions(allRides);
	}
	
	@Test(expectedExceptions = {NoRideAfterFiveHoursException.class})
	public void shouldThrowNoRideAfterFiveHoursException_ForScheduledTimeAfterFiveHours_Add() {
		rideDTO.setScheduledTime(FIVE_HOURS);
		
		List<Driver> driversForRide = new ArrayList<>();
		driversForRide.add(driver);
		Mockito.when(driverService.getActiveDrivers()).thenReturn(driversForRide);
		Mockito.when(driverService.getDriversWithAppropriateVehicleForRide(driversForRide, rideDTO)).thenReturn(driversForRide);

		Mockito.when(context.getAuthentication()).thenReturn(authentication);
		Mockito.when(authentication.getName()).thenReturn(passenger.getEmail());
		SecurityContextHolder.setContext(context);
		Mockito.when(allPassengers.findPassengerByEmail(passenger.getEmail())).thenReturn(Optional.of(passenger));
		doReturn(null).when(rideService).getPendingRideForPassenger(passenger.getId());
		rideService.add(rideDTO);
		verifyNoInteractions(allRides);
	}
	
	
	@Test(expectedExceptions = {NoAvailableDriversException.class})
	public void shouldThrowNoAvailableDriversException_ForAllDriversHavingScheduledRides_Add() {
		rideDTO.setScheduledTime(LocalDateTime.now().plusHours(PLUS_HOURS));
		
		List<Driver> driversForRide = new ArrayList<>();
		driversForRide.add(driver);
		Mockito.when(driverService.getActiveDrivers()).thenReturn(driversForRide);
		Mockito.when(driverService.getDriversWithAppropriateVehicleForRide(driversForRide, rideDTO)).thenReturn(driversForRide);

		Mockito.when(context.getAuthentication()).thenReturn(authentication);
		Mockito.when(authentication.getName()).thenReturn(passenger.getEmail());
		SecurityContextHolder.setContext(context);
		Mockito.when(allPassengers.findPassengerByEmail(passenger.getEmail())).thenReturn(Optional.of(passenger));
		doReturn(null).when(rideService).getPendingRideForPassenger(passenger.getId());
		Mockito.when(allRides.getFirstUpcomingRideForDriver(Mockito.anyInt())).thenReturn(ride);
		rideService.add(rideDTO);
	}
	
	@Test(expectedExceptions = {PassengerAlreadyInRideException.class})
	public void shouldThrowPassengerAlreadyInRideException_ForOneOfThePassengersAlreadyInRideAndScheduleTimeNotNull_Add() {
		rideDTO.setScheduledTime(LocalDateTime.now().plusHours(PLUS_HOURS));
		
		List<Driver> driversForRide = new ArrayList<>();
		driversForRide.add(driver);
		Mockito.when(driverService.getActiveDrivers()).thenReturn(driversForRide);
		Mockito.when(driverService.getDriversWithAppropriateVehicleForRide(driversForRide, rideDTO)).thenReturn(driversForRide);

		Mockito.when(context.getAuthentication()).thenReturn(authentication);
		Mockito.when(authentication.getName()).thenReturn(passenger.getEmail());
		SecurityContextHolder.setContext(context);
		Mockito.when(allPassengers.findPassengerByEmail(passenger.getEmail())).thenReturn(Optional.of(passenger));
		doReturn(null).when(rideService).getPendingRideForPassenger(passenger.getId());
		Mockito.when(allRides.getFirstUpcomingRideForDriver(Mockito.anyInt())).thenReturn(null);
		
		List<Ride> rides = new ArrayList<>();
		ride.setScheduledTime(LocalDateTime.now().plusHours(PLUS_HOURS-1));
		ride.setEstimatedTimeInMinutes(120);
		rides.add(ride);
		
		Mockito.when(allRides.getAllScheduledRideForTodayForPassenger(passenger.getId(), LocalDate.now().atStartOfDay().plusDays(1))).thenReturn(rides);
		rideService.add(rideDTO);
	}
	
	@Test(expectedExceptions = {NoAvailableDriversException.class})
	public void shouldThrowNoAvailableDriversException_ForAllDriversWorkingHoursForTodayOverLimitAndScheduledTimeNotNull_Add() {
		rideDTO.setScheduledTime(LocalDateTime.now().plusHours(PLUS_HOURS));
		
		List<Driver> driversForRide = new ArrayList<>();
		driversForRide.add(driver);
		Mockito.when(driverService.getActiveDrivers()).thenReturn(driversForRide);
		Mockito.when(driverService.getDriversWithAppropriateVehicleForRide(driversForRide, rideDTO)).thenReturn(driversForRide);

		Mockito.when(context.getAuthentication()).thenReturn(authentication);
		Mockito.when(authentication.getName()).thenReturn(passenger.getEmail());
		SecurityContextHolder.setContext(context);
		Mockito.when(allPassengers.findPassengerByEmail(passenger.getEmail())).thenReturn(Optional.of(passenger));
		doReturn(null).when(rideService).getPendingRideForPassenger(passenger.getId());
		Mockito.when(allRides.getFirstUpcomingRideForDriver(Mockito.anyInt())).thenReturn(null);
		
		List<Ride> passengerScheduledRides = new ArrayList<>();
		LocalDateTime endOfDay = LocalDate.now().atStartOfDay().plusDays(1);
		Mockito.when(allRides.getAllScheduledRideForTodayForPassenger(passenger.getId(), endOfDay)).thenReturn(passengerScheduledRides);
		
		int newRideDuration = 10;
		Mockito.when(rideEstimationService.getEstimatedTime(rideDTO.getDepartureLocation(), rideDTO.getDestinationLocation())).thenReturn(newRideDuration);
		Mockito.when(workingHoursService.getWorkedHoursForTodayWithNewRide(driver.getId(), newRideDuration)).thenReturn((double) 8);
		Mockito.when(allRides.getAllScheduledRideForTodayForDriver(driver.getId(), endOfDay)).thenReturn(null);
		
		rideService.add(rideDTO);
	}
	
	@Test(expectedExceptions = {NoAvailableDriversException.class})
	public void shouldThrowNoAvailableDriversException_ForAllDriversWithoutActiveRideScheduledRidesForTodayOverLimit_Add() {
		rideDTO.setScheduledTime(LocalDateTime.now().plusHours(PLUS_HOURS));
		
		List<Driver> driversForRide = new ArrayList<>();
		driversForRide.add(driver);
		Mockito.when(driverService.getActiveDrivers()).thenReturn(driversForRide);
		Mockito.when(driverService.getDriversWithAppropriateVehicleForRide(driversForRide, rideDTO)).thenReturn(driversForRide);

		Mockito.when(context.getAuthentication()).thenReturn(authentication);
		Mockito.when(authentication.getName()).thenReturn(passenger.getEmail());
		SecurityContextHolder.setContext(context);
		Mockito.when(allPassengers.findPassengerByEmail(passenger.getEmail())).thenReturn(Optional.of(passenger));
		doReturn(null).when(rideService).getPendingRideForPassenger(passenger.getId());
		Mockito.when(allRides.getFirstUpcomingRideForDriver(Mockito.anyInt())).thenReturn(null);
		
		List<Ride> passengerScheduledRides = new ArrayList<>();
		LocalDateTime endOfDay = LocalDate.now().atStartOfDay().plusDays(1);
		Mockito.when(allRides.getAllScheduledRideForTodayForPassenger(passenger.getId(), endOfDay)).thenReturn(passengerScheduledRides);
		
		int newRideDuration = 10;
		Mockito.when(rideEstimationService.getEstimatedTime(rideDTO.getDepartureLocation(), rideDTO.getDestinationLocation())).thenReturn(newRideDuration);
		Mockito.when(workingHoursService.getWorkedHoursForTodayWithNewRide(driver.getId(), newRideDuration)).thenReturn((double) 1);
		
		List<Ride> driverScheduledRides = new ArrayList<>();
		rideNext.setEstimatedTimeInMinutes(420);
		driverScheduledRides.add(rideNext);
		
		Mockito.when(allRides.getAllScheduledRideForTodayForDriver(driver.getId(), endOfDay)).thenReturn(driverScheduledRides);
		
		rideService.add(rideDTO);
	}
	
	@Test
	public void shouldCreateRideAndFindDriverWithLessWorkingHours_ForMoreDrivers_Add() {
		rideDTO.setScheduledTime(LocalDateTime.now().plusHours(PLUS_HOURS));
		
		List<Driver> driversForRide = new ArrayList<>();
		driversForRide.add(driver);
		driversForRide.add(driverNotChosen);
		
		Mockito.when(driverService.getActiveDrivers()).thenReturn(driversForRide);
		Mockito.when(driverService.getDriversWithAppropriateVehicleForRide(driversForRide, rideDTO)).thenReturn(driversForRide);

		Mockito.when(context.getAuthentication()).thenReturn(authentication);
		Mockito.when(authentication.getName()).thenReturn(passenger.getEmail());
		SecurityContextHolder.setContext(context);
		Mockito.when(allPassengers.findPassengerByEmail(passenger.getEmail())).thenReturn(Optional.of(passenger));
		doReturn(null).when(rideService).getPendingRideForPassenger(passenger.getId());
		Mockito.when(allRides.getFirstUpcomingRideForDriver(Mockito.anyInt())).thenReturn(null);
		
		List<Ride> passengerScheduledRides = new ArrayList<>();
		LocalDateTime endOfDay = LocalDate.now().atStartOfDay().plusDays(1);
		Mockito.when(allRides.getAllScheduledRideForTodayForPassenger(passenger.getId(), endOfDay)).thenReturn(passengerScheduledRides);
		
		int newRideDuration = 10;
		double distance = 10.0;
		int lessTime = 6;
		int moreTime = 7;
		Mockito.when(rideEstimationService.getEstimatedTime(rideDTO.getDepartureLocation(), rideDTO.getDestinationLocation())).thenReturn(newRideDuration);
		Mockito.when(rideEstimationService.getEstimatedDistance(rideDTO.getDepartureLocation(), rideDTO.getDestinationLocation())).thenReturn(distance);
		
		Mockito.when(workingHoursService.getWorkedHoursForTodayWithNewRide(driver.getId(), newRideDuration)).thenReturn((double) lessTime);
		Mockito.when(allRides.getAllScheduledRideForTodayForDriver(driver.getId(), endOfDay)).thenReturn(null);
		
		Mockito.when(workingHoursService.getWorkedHoursForTodayWithNewRide(driverNotChosen.getId(), newRideDuration)).thenReturn((double) moreTime);
		Mockito.when(allRides.getAllScheduledRideForTodayForDriver(driverNotChosen.getId(), endOfDay)).thenReturn(null);
		
		int pricePerKm = 60;
		Mockito.when(vehicleTypeService.getByName(rideDTO.getVehicleType())).thenReturn(new VehicleType(1, rideDTO.getVehicleType(), pricePerKm));
		Mockito.when(passengerService.getPassenger(passenger.getId())).thenReturn(passenger);
		RideReturnedDTO res = rideService.add(rideDTO);
		
		assertTrue(res.getDriver().getId() == driver.getId());
		assertEquals(newRideDuration, res.getEstimatedTimeInMinutes());
		assertEquals(distance, res.getDistance());
		assertEquals(rideDTO.getScheduledTime(), res.getScheduledTime());
		assertEquals(res.getStartTime(), null);
		assertEquals(RideStatus.PENDING, res.getStatus());
	}
	
	@Test
	public void shouldCreateRide_ForOneDriver_Add() {
		rideDTO.setScheduledTime(LocalDateTime.now().plusHours(PLUS_HOURS));
		
		List<Driver> driversForRide = new ArrayList<>();
		driversForRide.add(driver);
		
		Mockito.when(driverService.getActiveDrivers()).thenReturn(driversForRide);
		Mockito.when(driverService.getDriversWithAppropriateVehicleForRide(driversForRide, rideDTO)).thenReturn(driversForRide);

		Mockito.when(context.getAuthentication()).thenReturn(authentication);
		Mockito.when(authentication.getName()).thenReturn(passenger.getEmail());
		SecurityContextHolder.setContext(context);
		Mockito.when(allPassengers.findPassengerByEmail(passenger.getEmail())).thenReturn(Optional.of(passenger));
		doReturn(null).when(rideService).getPendingRideForPassenger(passenger.getId());
		Mockito.when(allRides.getFirstUpcomingRideForDriver(Mockito.anyInt())).thenReturn(null);
		
		List<Ride> passengerScheduledRides = new ArrayList<>();
		LocalDateTime endOfDay = LocalDate.now().atStartOfDay().plusDays(1);
		Mockito.when(allRides.getAllScheduledRideForTodayForPassenger(passenger.getId(), endOfDay)).thenReturn(passengerScheduledRides);
		
		int newRideDuration = 10;
		double distance = 10.0;
		int time = 6;
		Mockito.when(rideEstimationService.getEstimatedTime(rideDTO.getDepartureLocation(), rideDTO.getDestinationLocation())).thenReturn(newRideDuration);
		Mockito.when(rideEstimationService.getEstimatedDistance(rideDTO.getDepartureLocation(), rideDTO.getDestinationLocation())).thenReturn(distance);
		
		Mockito.when(workingHoursService.getWorkedHoursForTodayWithNewRide(driver.getId(), newRideDuration)).thenReturn((double) time);
		Mockito.when(allRides.getAllScheduledRideForTodayForDriver(driver.getId(), endOfDay)).thenReturn(null);
		
		int pricePerKm = 60;
		Mockito.when(vehicleTypeService.getByName(rideDTO.getVehicleType())).thenReturn(new VehicleType(1, rideDTO.getVehicleType(), pricePerKm));
		Mockito.when(passengerService.getPassenger(passenger.getId())).thenReturn(passenger);
		RideReturnedDTO res = rideService.add(rideDTO);
		
		assertTrue(res.getDriver().getId() == driver.getId());
		assertEquals(newRideDuration, res.getEstimatedTimeInMinutes());
		assertEquals(distance, res.getDistance());
		assertEquals(rideDTO.getScheduledTime(), res.getScheduledTime());
		assertEquals(RideStatus.PENDING, res.getStatus());
	}
	
	@Test(expectedExceptions = {NoAvailableDriversException.class})
	public void shouldThrowNoAvailableDriversException_ForAllDriversWithNoActiveRideOverlapingNextRide_Add() {
		rideDTO.setScheduledTime(null);
		
		List<Driver> driversForRide = new ArrayList<>();
		driversForRide.add(driver);
		driversForRide.add(driverNotChosen);
		
		Mockito.when(driverService.getActiveDrivers()).thenReturn(driversForRide);
		Mockito.when(driverService.getDriversWithAppropriateVehicleForRide(driversForRide, rideDTO)).thenReturn(driversForRide);
		
		Mockito.when(context.getAuthentication()).thenReturn(authentication);
		Mockito.when(authentication.getName()).thenReturn(passenger.getEmail());
		SecurityContextHolder.setContext(context);
		Mockito.when(allPassengers.findPassengerByEmail(passenger.getEmail())).thenReturn(Optional.of(passenger));
		
		doReturn(null).when(rideService).getPendingRideForPassenger(passenger.getId());
		doReturn(null).when(rideService).getActiveRideForPassenger(passenger.getId());
		doReturn(null).when(rideService).getActiveRideForDriver(Mockito.anyInt());
		
		int newRideDuration = 10;
		Mockito.when(rideEstimationService.getEstimatedTime(rideDTO.getDepartureLocation(), rideDTO.getDestinationLocation())).thenReturn(newRideDuration);
		
		int lessTime = 6;
		int moreTime = 10;
		Mockito.when(rideEstimationService.getEstimatedTimeForVehicleLocation(rideDTO.getDepartureLocation(), driver.getVehicle())).thenReturn(lessTime);
		Mockito.when(rideEstimationService.getEstimatedTimeForVehicleLocation(rideDTO.getDepartureLocation(), driverNotChosen.getVehicle())).thenReturn(moreTime);
		Mockito.when(allRides.getFirstUpcomingRideForDriver(Mockito.anyInt())).thenReturn(ride);
		ride.setScheduledTime(LocalDateTime.now().plusMinutes(newRideDuration));
		
		rideService.add(rideDTO);
		
	
	}
	
	@Test(expectedExceptions = {NoAvailableDriversException.class})
	public void shouldThrowNoAvailableDriversException_ForAllDriversWithActiveRideWorkingHoursForTodayOverLimit_Add() {
		rideDTO.setScheduledTime(null);
		
		List<Driver> driversForRide = new ArrayList<>();
		driversForRide.add(driver);
		driversForRide.add(driverNotChosen);
		
		Mockito.when(driverService.getActiveDrivers()).thenReturn(driversForRide);
		Mockito.when(driverService.getDriversWithAppropriateVehicleForRide(driversForRide, rideDTO)).thenReturn(driversForRide);

		Mockito.when(context.getAuthentication()).thenReturn(authentication);
		Mockito.when(authentication.getName()).thenReturn(passenger.getEmail());
		SecurityContextHolder.setContext(context);
		Mockito.when(allPassengers.findPassengerByEmail(passenger.getEmail())).thenReturn(Optional.of(passenger));
		
		doReturn(null).when(rideService).getPendingRideForPassenger(passenger.getId());
		doReturn(null).when(rideService).getActiveRideForPassenger(passenger.getId());
		doReturn(null).when(rideService).getActiveRideForDriver(Mockito.anyInt());
		
		int newRideDuration = 10;
		Mockito.when(rideEstimationService.getEstimatedTime(rideDTO.getDepartureLocation(), rideDTO.getDestinationLocation())).thenReturn(newRideDuration);
		
		int lessTime = 6;
		int moreTime = 10;
		Mockito.when(rideEstimationService.getEstimatedTimeForVehicleLocation(rideDTO.getDepartureLocation(), driver.getVehicle())).thenReturn(lessTime);
		Mockito.when(rideEstimationService.getEstimatedTimeForVehicleLocation(rideDTO.getDepartureLocation(), driverNotChosen.getVehicle())).thenReturn(moreTime);
		Mockito.when(allRides.getFirstUpcomingRideForDriver(Mockito.anyInt())).thenReturn(null);
		
		LocalDateTime endOfDay = LocalDate.now().atStartOfDay().plusDays(1);
		Mockito.when(workingHoursService.getWorkedHoursForTodayWithNewRide(driver.getId(), newRideDuration+lessTime)).thenReturn((double) 8);
		Mockito.when(workingHoursService.getWorkedHoursForTodayWithNewRide(driverNotChosen.getId(), newRideDuration+moreTime)).thenReturn((double) 8);
		Mockito.when(allRides.getAllScheduledRideForTodayForDriver(driver.getId(), endOfDay)).thenReturn(null);
		Mockito.when(allRides.getAllScheduledRideForTodayForDriver(driverNotChosen.getId(), endOfDay)).thenReturn(null);
		
		rideService.add(rideDTO);
	}
	
	@Test
	public void shouldCreateRide_ForAllDriversWithNoActiveRideAndScheduledTimeNull_Add() {
		rideDTO.setScheduledTime(null);
		
		List<Driver> driversForRide = new ArrayList<>();
		driversForRide.add(driver);
		driversForRide.add(driverNotChosen);
		
		Mockito.when(driverService.getActiveDrivers()).thenReturn(driversForRide);
		Mockito.when(driverService.getDriversWithAppropriateVehicleForRide(driversForRide, rideDTO)).thenReturn(driversForRide);

		Mockito.when(context.getAuthentication()).thenReturn(authentication);
		Mockito.when(authentication.getName()).thenReturn(passenger.getEmail());
		SecurityContextHolder.setContext(context);
		Mockito.when(allPassengers.findPassengerByEmail(passenger.getEmail())).thenReturn(Optional.of(passenger));
		
		doReturn(null).when(rideService).getPendingRideForPassenger(passenger.getId());
		doReturn(null).when(rideService).getActiveRideForPassenger(passenger.getId());
		doReturn(null).when(rideService).getActiveRideForDriver(Mockito.anyInt());
		
		int newRideDuration = 10;
		Mockito.when(rideEstimationService.getEstimatedTime(rideDTO.getDepartureLocation(), rideDTO.getDestinationLocation())).thenReturn(newRideDuration);
		
		int lessTime = 6;
		int moreTime = 10;
		Mockito.when(rideEstimationService.getEstimatedTimeForVehicleLocation(rideDTO.getDepartureLocation(), driver.getVehicle())).thenReturn(lessTime);
		Mockito.when(rideEstimationService.getEstimatedTimeForVehicleLocation(rideDTO.getDepartureLocation(), driverNotChosen.getVehicle())).thenReturn(moreTime);
		Mockito.when(allRides.getFirstUpcomingRideForDriver(Mockito.anyInt())).thenReturn(null);
		
		LocalDateTime endOfDay = LocalDate.now().atStartOfDay().plusDays(1);
		Mockito.when(workingHoursService.getWorkedHoursForTodayWithNewRide(driver.getId(), newRideDuration+lessTime)).thenReturn((double) 6);
		Mockito.when(workingHoursService.getWorkedHoursForTodayWithNewRide(driverNotChosen.getId(), newRideDuration+moreTime)).thenReturn((double) 6);
		Mockito.when(allRides.getAllScheduledRideForTodayForDriver(driver.getId(), endOfDay)).thenReturn(null);
		Mockito.when(allRides.getAllScheduledRideForTodayForDriver(driverNotChosen.getId(), endOfDay)).thenReturn(null);
		
		double distance = 10.0;
		Mockito.when(rideEstimationService.getEstimatedTime(rideDTO.getDepartureLocation(), rideDTO.getDestinationLocation())).thenReturn(newRideDuration);
		Mockito.when(rideEstimationService.getEstimatedDistance(rideDTO.getDepartureLocation(), rideDTO.getDestinationLocation())).thenReturn(distance);
		
		
		int pricePerKm = 60;
		Mockito.when(vehicleTypeService.getByName(rideDTO.getVehicleType())).thenReturn(new VehicleType(1, rideDTO.getVehicleType(), pricePerKm));
		Mockito.when(passengerService.getPassenger(passenger.getId())).thenReturn(passenger);
		RideReturnedDTO res = rideService.add(rideDTO);
		
		
		assertTrue(res.getDriver().getId() == driver.getId());
		assertEquals(newRideDuration, res.getEstimatedTimeInMinutes());
		assertEquals(distance, res.getDistance());
		assertEquals(rideDTO.getScheduledTime(), null);
		assertEquals(res.getStartTime(), null);
		assertEquals(RideStatus.PENDING, res.getStatus());
	}
	
	@Test(expectedExceptions = {NoAvailableDriversException.class})
	public void shouldThrowNoAvailableDriversExceptios_ForAllDriversOverlapingNextRideAndScheduledTimeNotNull_Add() {
		rideDTO.setScheduledTime(null);
		
		List<Driver> driversForRide = new ArrayList<>();
		driversForRide.add(driver);
		driversForRide.add(driverNotChosen);
		
		Mockito.when(driverService.getActiveDrivers()).thenReturn(driversForRide);
		Mockito.when(driverService.getDriversWithAppropriateVehicleForRide(driversForRide, rideDTO)).thenReturn(driversForRide);

		Mockito.when(context.getAuthentication()).thenReturn(authentication);
		Mockito.when(authentication.getName()).thenReturn(passenger.getEmail());
		SecurityContextHolder.setContext(context);
		Mockito.when(allPassengers.findPassengerByEmail(passenger.getEmail())).thenReturn(Optional.of(passenger));
		
		doReturn(null).when(rideService).getPendingRideForPassenger(passenger.getId());
		doReturn(null).when(rideService).getActiveRideForPassenger(passenger.getId());
		
		int timeForNewRideDepartureArrivalForFirstRide = 4;
		ride.setEstimatedTimeInMinutes(8);
		ride.setStartTime(LocalDateTime.now().minusMinutes(4));
		
		RideReturnedDTO firstDriverRideDTO = new RideReturnedDTO();
		firstDriverRideDTO.setStartTime(ride.getStartTime());
		firstDriverRideDTO.setEstimatedTimeInMinutes(ride.getEstimatedTimeInMinutes());
		firstDriverRideDTO.setLocations(new ArrayList<LocationDTO>());
		firstDriverRideDTO.getLocations().add(new LocationDTO(new LocationNoIdDTO(ride.getDepartureLocation()),
				new LocationNoIdDTO(ride.getDestinationLocation())));
		doReturn(firstDriverRideDTO).when(rideService).getActiveRideForDriver(driver.getId());
		
		int timeForNewRideDepartureArrivalForSecondRide = 6;
		rideNotChosen.setEstimatedTimeInMinutes(10);
		rideNotChosen.setStartTime(LocalDateTime.now().minusMinutes(5));
		
		RideReturnedDTO secondDriverRideDTO = new RideReturnedDTO();
		secondDriverRideDTO.setStartTime(rideNotChosen.getStartTime());
		secondDriverRideDTO.setEstimatedTimeInMinutes(rideNotChosen.getEstimatedTimeInMinutes());
		secondDriverRideDTO.setLocations(new ArrayList<LocationDTO>());
		secondDriverRideDTO.getLocations().add(new LocationDTO(new LocationNoIdDTO(rideNotChosen.getDepartureLocation()),
				new LocationNoIdDTO(rideNotChosen.getDestinationLocation())));
		doReturn(secondDriverRideDTO).when(rideService).getActiveRideForDriver(driverNotChosen.getId());
		
		int newRideDuration = 10;
		Mockito.when(rideEstimationService.getEstimatedTime(rideDTO.getDepartureLocation(), rideDTO.getDestinationLocation())).thenReturn(newRideDuration);
		Mockito.when(rideEstimationService.getEstimatedTime(rideDTO.getDepartureLocation(), firstDriverRideDTO.getLocations().get(0).getDestination())).thenReturn(timeForNewRideDepartureArrivalForFirstRide);
		Mockito.when(rideEstimationService.getEstimatedTime(rideDTO.getDepartureLocation(), secondDriverRideDTO.getLocations().get(0).getDestination())).thenReturn(timeForNewRideDepartureArrivalForSecondRide);
		
		int lessTime = 6;
		int moreTime = 10;
		
		Mockito.when(allRides.getFirstUpcomingRideForDriver(Mockito.anyInt())).thenReturn(rideNext);
		rideNext.setScheduledTime(LocalDateTime.now().plusMinutes(newRideDuration));
		rideService.add(rideDTO);
	
	}
	
	@Test
	public void shouldCreateRideForFirstDriver_ForAllDriversWithActiveRideAndFirstDriverCloser_Add() {
		rideDTO.setScheduledTime(null);
		
		List<Driver> driversForRide = new ArrayList<>();
		driversForRide.add(driver);
		driversForRide.add(driverNotChosen);
		
		Mockito.when(driverService.getActiveDrivers()).thenReturn(driversForRide);
		Mockito.when(driverService.getDriversWithAppropriateVehicleForRide(driversForRide, rideDTO)).thenReturn(driversForRide);

		Mockito.when(context.getAuthentication()).thenReturn(authentication);
		Mockito.when(authentication.getName()).thenReturn(passenger.getEmail());
		SecurityContextHolder.setContext(context);
		Mockito.when(allPassengers.findPassengerByEmail(passenger.getEmail())).thenReturn(Optional.of(passenger));
		
		doReturn(null).when(rideService).getPendingRideForPassenger(passenger.getId());
		doReturn(null).when(rideService).getActiveRideForPassenger(passenger.getId());
		
		int timeForNewRideDepartureArrivalForFirstRide = 5;
		ride.setEstimatedTimeInMinutes(8);
		ride.setStartTime(LocalDateTime.now().minusMinutes(4));
		
		RideReturnedDTO firstDriverRideDTO = new RideReturnedDTO();
		firstDriverRideDTO.setStartTime(ride.getStartTime());
		firstDriverRideDTO.setEstimatedTimeInMinutes(ride.getEstimatedTimeInMinutes());
		firstDriverRideDTO.setLocations(new ArrayList<LocationDTO>());
		firstDriverRideDTO.getLocations().add(new LocationDTO(new LocationNoIdDTO(ride.getDepartureLocation()),
				new LocationNoIdDTO(ride.getDestinationLocation())));
		doReturn(firstDriverRideDTO).when(rideService).getActiveRideForDriver(driver.getId());
		
		int timeForNewRideDepartureArrivalForSecondRide = 5;
		rideNotChosen.setEstimatedTimeInMinutes(10);
		rideNotChosen.setStartTime(LocalDateTime.now().minusMinutes(5));
		
		RideReturnedDTO secondDriverRideDTO = new RideReturnedDTO();
		secondDriverRideDTO.setStartTime(rideNotChosen.getStartTime());
		secondDriverRideDTO.setEstimatedTimeInMinutes(rideNotChosen.getEstimatedTimeInMinutes());
		secondDriverRideDTO.setLocations(new ArrayList<LocationDTO>());
		secondDriverRideDTO.getLocations().add(new LocationDTO(new LocationNoIdDTO(rideNotChosen.getDepartureLocation()),
				new LocationNoIdDTO(rideNotChosen.getDestinationLocation())));
		doReturn(secondDriverRideDTO).when(rideService).getActiveRideForDriver(driverNotChosen.getId());
		
		int newRideDuration = 10;
		Mockito.when(rideEstimationService.getEstimatedTime(rideDTO.getDepartureLocation(), rideDTO.getDestinationLocation())).thenReturn(newRideDuration);
		Mockito.when(rideEstimationService.getEstimatedTime(rideDTO.getDepartureLocation(), firstDriverRideDTO.getLocations().get(0).getDestination())).thenReturn(timeForNewRideDepartureArrivalForFirstRide);
		Mockito.when(rideEstimationService.getEstimatedTime(rideDTO.getDepartureLocation(), secondDriverRideDTO.getLocations().get(0).getDestination())).thenReturn(timeForNewRideDepartureArrivalForSecondRide);
		
		Mockito.when(allRides.getFirstUpcomingRideForDriver(Mockito.anyInt())).thenReturn(null);
		
		LocalDateTime endOfDay = LocalDate.now().atStartOfDay().plusDays(1);
		Mockito.when(workingHoursService.getWorkedHoursForTodayWithNewRide(driver.getId(), newRideDuration)).thenReturn((double) 6);
		Mockito.when(workingHoursService.getWorkedHoursForTodayWithNewRide(driverNotChosen.getId(), newRideDuration)).thenReturn((double) 6);
		Mockito.when(allRides.getAllScheduledRideForTodayForDriver(driver.getId(), endOfDay)).thenReturn(null);
		Mockito.when(allRides.getAllScheduledRideForTodayForDriver(driverNotChosen.getId(), endOfDay)).thenReturn(null);
		
		double distance = 10.0;
		Mockito.when(rideEstimationService.getEstimatedTime(rideDTO.getDepartureLocation(), rideDTO.getDestinationLocation())).thenReturn(newRideDuration);
		Mockito.when(rideEstimationService.getEstimatedDistance(rideDTO.getDepartureLocation(), rideDTO.getDestinationLocation())).thenReturn(distance);
		
		
		int pricePerKm = 60;
		Mockito.when(vehicleTypeService.getByName(rideDTO.getVehicleType())).thenReturn(new VehicleType(1, rideDTO.getVehicleType(), pricePerKm));
		Mockito.when(passengerService.getPassenger(passenger.getId())).thenReturn(passenger);
		RideReturnedDTO res = rideService.add(rideDTO);
		
		
		assertTrue(res.getDriver().getId() == driver.getId());
		assertEquals(newRideDuration, res.getEstimatedTimeInMinutes());
		assertEquals(distance, res.getDistance());
		assertEquals(rideDTO.getScheduledTime(), null);
		assertEquals(res.getStartTime(), null);
		assertEquals(RideStatus.PENDING, res.getStatus());
	
	}
	
	@Test
	public void shouldCreateRideForFirstDriver_ForAllDriversWithActiveRideAndForFirstDriverMoreTimeToEndOfCurrentRideButCloserToDeparture_Add() {
		rideDTO.setScheduledTime(null);
		
		List<Driver> driversForRide = new ArrayList<>();
		driversForRide.add(driver);
		driversForRide.add(driverNotChosen);
		
		Mockito.when(driverService.getActiveDrivers()).thenReturn(driversForRide);
		Mockito.when(driverService.getDriversWithAppropriateVehicleForRide(driversForRide, rideDTO)).thenReturn(driversForRide);

		Mockito.when(context.getAuthentication()).thenReturn(authentication);
		Mockito.when(authentication.getName()).thenReturn(passenger.getEmail());
		SecurityContextHolder.setContext(context);
		Mockito.when(allPassengers.findPassengerByEmail(passenger.getEmail())).thenReturn(Optional.of(passenger));
		
		doReturn(null).when(rideService).getPendingRideForPassenger(passenger.getId());
		doReturn(null).when(rideService).getActiveRideForPassenger(passenger.getId());
		
		int timeForNewRideDepartureArrivalForFirstRide = 3;
		ride.setEstimatedTimeInMinutes(12);
		ride.setStartTime(LocalDateTime.now().minusMinutes(4));
		
		RideReturnedDTO firstDriverRideDTO = new RideReturnedDTO();
		firstDriverRideDTO.setStartTime(ride.getStartTime());
		firstDriverRideDTO.setEstimatedTimeInMinutes(ride.getEstimatedTimeInMinutes());
		firstDriverRideDTO.setLocations(new ArrayList<LocationDTO>());
		firstDriverRideDTO.getLocations().add(new LocationDTO(new LocationNoIdDTO(ride.getDepartureLocation()),
				new LocationNoIdDTO(ride.getDestinationLocation())));
		doReturn(firstDriverRideDTO).when(rideService).getActiveRideForDriver(driver.getId());
		
		int timeForNewRideDepartureArrivalForSecondRide = 10;
		rideNotChosen.setEstimatedTimeInMinutes(10);
		rideNotChosen.setStartTime(LocalDateTime.now().minusMinutes(5));
		
		RideReturnedDTO secondDriverRideDTO = new RideReturnedDTO();
		secondDriverRideDTO.setStartTime(rideNotChosen.getStartTime());
		secondDriverRideDTO.setEstimatedTimeInMinutes(rideNotChosen.getEstimatedTimeInMinutes());
		secondDriverRideDTO.setLocations(new ArrayList<LocationDTO>());
		secondDriverRideDTO.getLocations().add(new LocationDTO(new LocationNoIdDTO(rideNotChosen.getDepartureLocation()),
				new LocationNoIdDTO(rideNotChosen.getDestinationLocation())));
		doReturn(secondDriverRideDTO).when(rideService).getActiveRideForDriver(driverNotChosen.getId());
		
		int newRideDuration = 10;
		Mockito.when(rideEstimationService.getEstimatedTime(rideDTO.getDepartureLocation(), rideDTO.getDestinationLocation())).thenReturn(newRideDuration);
		Mockito.when(rideEstimationService.getEstimatedTime(rideDTO.getDepartureLocation(), firstDriverRideDTO.getLocations().get(0).getDestination())).thenReturn(timeForNewRideDepartureArrivalForFirstRide);
		Mockito.when(rideEstimationService.getEstimatedTime(rideDTO.getDepartureLocation(), secondDriverRideDTO.getLocations().get(0).getDestination())).thenReturn(timeForNewRideDepartureArrivalForSecondRide);
		
		Mockito.when(allRides.getFirstUpcomingRideForDriver(Mockito.anyInt())).thenReturn(null);
		
		LocalDateTime endOfDay = LocalDate.now().atStartOfDay().plusDays(1);
		Mockito.when(workingHoursService.getWorkedHoursForTodayWithNewRide(driver.getId(), newRideDuration)).thenReturn((double) 6);
		Mockito.when(workingHoursService.getWorkedHoursForTodayWithNewRide(driverNotChosen.getId(), newRideDuration)).thenReturn((double) 6);
		Mockito.when(allRides.getAllScheduledRideForTodayForDriver(driver.getId(), endOfDay)).thenReturn(null);
		Mockito.when(allRides.getAllScheduledRideForTodayForDriver(driverNotChosen.getId(), endOfDay)).thenReturn(null);
		
		double distance = 10.0;
		Mockito.when(rideEstimationService.getEstimatedTime(rideDTO.getDepartureLocation(), rideDTO.getDestinationLocation())).thenReturn(newRideDuration);
		Mockito.when(rideEstimationService.getEstimatedDistance(rideDTO.getDepartureLocation(), rideDTO.getDestinationLocation())).thenReturn(distance);
		
		
		int pricePerKm = 60;
		Mockito.when(vehicleTypeService.getByName(rideDTO.getVehicleType())).thenReturn(new VehicleType(1, rideDTO.getVehicleType(), pricePerKm));
		Mockito.when(passengerService.getPassenger(passenger.getId())).thenReturn(passenger);
		RideReturnedDTO res = rideService.add(rideDTO);
		
		
		assertTrue(res.getDriver().getId() == driver.getId());
		assertEquals(newRideDuration, res.getEstimatedTimeInMinutes());
		assertEquals(distance, res.getDistance());
		assertEquals(rideDTO.getScheduledTime(), null);
		assertEquals(res.getStartTime(), null);
		assertEquals(RideStatus.PENDING, res.getStatus());
	}
	
	@Test
	public void shouldCreateRideForOtherDriver_ForAllDriversWithActiveRideAndFirstDriverWorkingHoursOverLimit_A() {
		rideDTO.setScheduledTime(null);
		
		List<Driver> driversForRide = new ArrayList<>();
		driversForRide.add(driver);
		driversForRide.add(driverNotChosen);
		
		Mockito.when(driverService.getActiveDrivers()).thenReturn(driversForRide);
		Mockito.when(driverService.getDriversWithAppropriateVehicleForRide(driversForRide, rideDTO)).thenReturn(driversForRide);

		Mockito.when(context.getAuthentication()).thenReturn(authentication);
		Mockito.when(authentication.getName()).thenReturn(passenger.getEmail());
		SecurityContextHolder.setContext(context);
		Mockito.when(allPassengers.findPassengerByEmail(passenger.getEmail())).thenReturn(Optional.of(passenger));
		
		doReturn(null).when(rideService).getPendingRideForPassenger(passenger.getId());
		doReturn(null).when(rideService).getActiveRideForPassenger(passenger.getId());
		
		int timeForNewRideDepartureArrivalForFirstRide = 3;
		ride.setEstimatedTimeInMinutes(12);
		ride.setStartTime(LocalDateTime.now().minusMinutes(4));
		
		RideReturnedDTO firstDriverRideDTO = new RideReturnedDTO();
		firstDriverRideDTO.setStartTime(ride.getStartTime());
		firstDriverRideDTO.setEstimatedTimeInMinutes(ride.getEstimatedTimeInMinutes());
		firstDriverRideDTO.setLocations(new ArrayList<LocationDTO>());
		firstDriverRideDTO.getLocations().add(new LocationDTO(new LocationNoIdDTO(ride.getDepartureLocation()),
				new LocationNoIdDTO(ride.getDestinationLocation())));
		doReturn(firstDriverRideDTO).when(rideService).getActiveRideForDriver(driver.getId());
		
		int timeForNewRideDepartureArrivalForSecondRide = 10;
		rideNotChosen.setEstimatedTimeInMinutes(10);
		rideNotChosen.setStartTime(LocalDateTime.now().minusMinutes(5));
		
		RideReturnedDTO secondDriverRideDTO = new RideReturnedDTO();
		secondDriverRideDTO.setStartTime(rideNotChosen.getStartTime());
		secondDriverRideDTO.setEstimatedTimeInMinutes(rideNotChosen.getEstimatedTimeInMinutes());
		secondDriverRideDTO.setLocations(new ArrayList<LocationDTO>());
		secondDriverRideDTO.getLocations().add(new LocationDTO(new LocationNoIdDTO(rideNotChosen.getDepartureLocation()),
				new LocationNoIdDTO(rideNotChosen.getDestinationLocation())));
		doReturn(secondDriverRideDTO).when(rideService).getActiveRideForDriver(driverNotChosen.getId());
		
		int newRideDuration = 10;
		Mockito.when(rideEstimationService.getEstimatedTime(rideDTO.getDepartureLocation(), rideDTO.getDestinationLocation())).thenReturn(newRideDuration);
		Mockito.when(rideEstimationService.getEstimatedTime(rideDTO.getDepartureLocation(), firstDriverRideDTO.getLocations().get(0).getDestination())).thenReturn(timeForNewRideDepartureArrivalForFirstRide);
		Mockito.when(rideEstimationService.getEstimatedTime(rideDTO.getDepartureLocation(), secondDriverRideDTO.getLocations().get(0).getDestination())).thenReturn(timeForNewRideDepartureArrivalForSecondRide);
		
		Mockito.when(allRides.getFirstUpcomingRideForDriver(Mockito.anyInt())).thenReturn(null);
		
		LocalDateTime endOfDay = LocalDate.now().atStartOfDay().plusDays(1);
		Mockito.when(workingHoursService.getWorkedHoursForTodayWithNewRide(driver.getId(), newRideDuration+timeForNewRideDepartureArrivalForFirstRide)).thenReturn((double) 8);
		Mockito.when(workingHoursService.getWorkedHoursForTodayWithNewRide(driverNotChosen.getId(), newRideDuration+timeForNewRideDepartureArrivalForSecondRide)).thenReturn((double) 6);
		Mockito.when(allRides.getAllScheduledRideForTodayForDriver(driver.getId(), endOfDay)).thenReturn(null);
		Mockito.when(allRides.getAllScheduledRideForTodayForDriver(driverNotChosen.getId(), endOfDay)).thenReturn(null);
		
		double distance = 10.0;
		Mockito.when(rideEstimationService.getEstimatedTime(rideDTO.getDepartureLocation(), rideDTO.getDestinationLocation())).thenReturn(newRideDuration);
		Mockito.when(rideEstimationService.getEstimatedDistance(rideDTO.getDepartureLocation(), rideDTO.getDestinationLocation())).thenReturn(distance);
		
		
		int pricePerKm = 60;
		Mockito.when(vehicleTypeService.getByName(rideDTO.getVehicleType())).thenReturn(new VehicleType(1, rideDTO.getVehicleType(), pricePerKm));
		Mockito.when(passengerService.getPassenger(passenger.getId())).thenReturn(passenger);
		RideReturnedDTO res = rideService.add(rideDTO);
		
		
		assertTrue(res.getDriver().getId() == driverNotChosen.getId());
		assertEquals(newRideDuration, res.getEstimatedTimeInMinutes());
		assertEquals(distance, res.getDistance());
		assertEquals(rideDTO.getScheduledTime(), null);
		assertEquals(res.getStartTime(), null);
		assertEquals(RideStatus.PENDING, res.getStatus());
	
	}
	
	

}
