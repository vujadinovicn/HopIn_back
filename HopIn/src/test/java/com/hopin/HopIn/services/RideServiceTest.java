package com.hopin.HopIn.services;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.web.server.ResponseStatusException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.hopin.HopIn.HopInApplication;
import com.hopin.HopIn.dtos.FavoriteRideDTO;
import com.hopin.HopIn.dtos.FavoriteRideReturnedDTO;
import com.hopin.HopIn.dtos.LocationNoIdDTO;
import com.hopin.HopIn.dtos.RideForReportDTO;
import com.hopin.HopIn.dtos.RideReturnedDTO;
import com.hopin.HopIn.dtos.RouteLocsDTO;
import com.hopin.HopIn.dtos.UnregisteredRideSuggestionDTO;
import com.hopin.HopIn.dtos.UserDTO;
import com.hopin.HopIn.dtos.UserInRideDTO;
import com.hopin.HopIn.dtos.UserReturnedDTO;
import com.hopin.HopIn.entities.Driver;
import com.hopin.HopIn.entities.FavoriteRide;
import com.hopin.HopIn.entities.Location;
import com.hopin.HopIn.entities.Passenger;
import com.hopin.HopIn.entities.Ride;
import com.hopin.HopIn.entities.Route;
import com.hopin.HopIn.entities.User;
import com.hopin.HopIn.entities.VehicleType;
import com.hopin.HopIn.enums.RideStatus;
import com.hopin.HopIn.enums.VehicleTypeName;
import com.hopin.HopIn.exceptions.FavoriteRideException;
import com.hopin.HopIn.exceptions.NoActivePassengerRideException;
import com.hopin.HopIn.exceptions.RideNotFoundException;
import com.hopin.HopIn.exceptions.UserNotFoundException;
import com.hopin.HopIn.repositories.FavoriteRideRepository;
import com.hopin.HopIn.repositories.PassengerRepository;
import com.hopin.HopIn.repositories.RideRepository;
import com.hopin.HopIn.repositories.VehicleTypeRepository;
import com.hopin.HopIn.services.interfaces.IPassengerService;
import com.hopin.HopIn.services.interfaces.IRideService;
import com.hopin.HopIn.services.interfaces.IUserService;
import com.hopin.HopIn.services.interfaces.IVehicleTypeService;

@ActiveProfiles("test")
@SpringBootTest(classes = HopInApplication.class)
@TestPropertySource(properties = "classpath:application-test.properties")
public class RideServiceTest extends AbstractTestNGSpringContextTests {

	private static final int NON_EXISTANT_RIDE_ID = 0;
	private static final double DISTANCE = 1;

	@Autowired
	private IRideService rideService;

	@MockBean
	private IPassengerService passengerService;

	@MockBean
	private IVehicleTypeService vehicleTypeService;

	@MockBean
	private IUserService userService;

	@MockBean
	private RideRepository allRides;

	@MockBean
	private FavoriteRideRepository allFavoriteRides;

	@MockBean
	private PassengerRepository allPassengers;

	@MockBean
	private VehicleTypeRepository allVehicleTypes;

	private Ride ride;
	private Passenger passenger;
	private FavoriteRide favoriteRoute;
	private VehicleType vehicleType;

	@BeforeMethod
	public void setup() {
		Driver driver = new Driver(1, "Pera", "Peric", "pera@gmail.com", "123", "Adresa", "4384294", null);
		passenger = new Passenger(2, "Lara", "Peric", "lara@gmail.com", "123", "Adresa", "4384294", null, null, null);

		Location departure = new Location(1, "Jirecekova 1", 45.32, 24.3);
		Location destination = new Location(1, "Promenada", 45.32, 24.3);

		Set<Passenger> passengers = new HashSet<>();
		passengers.add(passenger);

		vehicleType = new VehicleType(1, VehicleTypeName.CAR, 60);

		ride = new Ride();
		ride.setId(1);
		ride.setStatus(RideStatus.ACCEPTED);
		ride.setDepartureLocation(departure);
		ride.setDestinationLocation(destination);
		ride.setDriver(driver);
		ride.setPassengers(passengers);
		ride.setVehicleType(vehicleType);
	}

	@Test
	public void shouldStartRideForExistingRideWithStatusAccepted() {
		this.ride.setStatus(RideStatus.ACCEPTED);

		Mockito.when(allRides.findById(ride.getId())).thenReturn(Optional.of(ride));
		Mockito.when(allRides.save(ride)).thenReturn(ride);

		RideReturnedDTO ret = this.rideService.startRide(ride.getId());

		assertTrue(ret.getId() == ride.getId());
		assertTrue(ret.getStatus() == RideStatus.STARTED);
		assertTrue(ret.getStartTime() != null);
	}

	@Test(expectedExceptions = ResponseStatusException.class)
	public void shouldThrowExceptionForStartingRideWithNonExistantRideId() {
		Mockito.when(allRides.findById(NON_EXISTANT_RIDE_ID)).thenReturn(Optional.empty());

		try {
			this.rideService.startRide(NON_EXISTANT_RIDE_ID);
		} catch (ResponseStatusException e) {
			assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
			assertEquals(e.getReason(), "Ride does not exist!");
			throw e;
		}
	}

	@Test(expectedExceptions = ResponseStatusException.class)
	public void shouldThrowExceptionForStartingRideWithWrongStatus() {
		ride.setStatus(RideStatus.FINISHED);

		Mockito.when(allRides.findById(ride.getId())).thenReturn(Optional.of(ride));

		try {
			this.rideService.startRide(ride.getId());
		} catch (ResponseStatusException e) {
			assertEquals(e.getStatusCode(), HttpStatus.BAD_REQUEST);
			assertEquals(e.getReason(), "Cannot start a ride that is not in status ACCEPTED!");
			throw e;
		}
	}

	@Test
	public void shouldFinishRideForExistingRideWithStatusStarted() {
		this.ride.setStatus(RideStatus.STARTED);

		Mockito.when(allRides.findById(ride.getId())).thenReturn(Optional.of(ride));
		Mockito.when(allRides.save(ride)).thenReturn(ride);

		RideReturnedDTO ret = this.rideService.finishRide(ride.getId());

		assertTrue(ret.getId() == ride.getId());
		assertTrue(ret.getStatus() == RideStatus.FINISHED);
		assertTrue(ret.getEndTime() != null);
	}

	@Test(expectedExceptions = ResponseStatusException.class)
	public void shouldThrowExceptionForFinishingRideWithNonExistantRideId() {
		Mockito.when(allRides.findById(NON_EXISTANT_RIDE_ID)).thenReturn(Optional.empty());

		try {
			this.rideService.finishRide(NON_EXISTANT_RIDE_ID);
		} catch (ResponseStatusException e) {
			assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
			assertEquals(e.getReason(), "Ride does not exist!");
			throw e;
		}
	}

	@Test(expectedExceptions = ResponseStatusException.class)
	public void shouldThrowExceptionForFinishingRideWithWrongStatus() {
		ride.setStatus(RideStatus.FINISHED);

		Mockito.when(allRides.findById(ride.getId())).thenReturn(Optional.of(ride));

		try {
			this.rideService.finishRide(ride.getId());
		} catch (ResponseStatusException e) {
			assertEquals(e.getStatusCode(), HttpStatus.BAD_REQUEST);
			assertEquals(e.getReason(), "Cannot end a ride that is not in status STARTED!");
			throw e;
		}
	}

	@Test
	public void shouldStartRideToDepartureForValidRide() {
		ride.setScheduledTime(LocalDateTime.now());
		
		Mockito.when(allRides.findById(ride.getId())).thenReturn(Optional.of(ride));
		Mockito.when(allRides.save(ride)).thenReturn(ride);

		RideReturnedDTO ret = this.rideService.startRideToDeparture(ride.getId());

		verify(allRides).save(ride);
		verify(allRides).flush();

		assertEquals(ret.getScheduledTime(), null);
	}

	@Test(expectedExceptions = ResponseStatusException.class)
	public void shouldThrowExceptionForStartingToDepartureRideWithNonExistantRideId() {
		Mockito.when(allRides.findById(NON_EXISTANT_RIDE_ID)).thenReturn(Optional.empty());

		try {
			this.rideService.startRideToDeparture(NON_EXISTANT_RIDE_ID);
		} catch (ResponseStatusException e) {
			assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
			assertEquals(e.getReason(), "Ride does not exist!");
			throw e;
		}
	}
	
	@Test(expectedExceptions = ResponseStatusException.class)
	public void shouldThrowExceptionForStartingToDepartureRideWithWrongStatus() {
		ride.setStatus(RideStatus.ACTIVE);
		
		Mockito.when(allRides.findById(ride.getId())).thenReturn(Optional.of(ride));

		try {
			this.rideService.startRideToDeparture(ride.getId());
		} catch (ResponseStatusException e) {
			assertEquals(e.getStatusCode(), HttpStatus.BAD_REQUEST);
			assertEquals(e.getReason(), "Cannot start ride to departure, status must be ACCEPTED!");
			throw e;
		}
	}
	
	@Test(expectedExceptions = ResponseStatusException.class)
	public void shouldThrowExceptionForStartingToDepartureRideWithNoScheduledTime() {
		ride.setStatus(RideStatus.ACCEPTED);
		ride.setScheduledTime(null);
		
		Mockito.when(allRides.findById(ride.getId())).thenReturn(Optional.of(ride));

		try {
			this.rideService.startRideToDeparture(ride.getId());
		} catch (ResponseStatusException e) {
			assertEquals(e.getStatusCode(), HttpStatus.BAD_REQUEST);
			assertEquals(e.getReason(), "Cannot start ride to departure, scheduled time must not be null!");
			throw e;
		}
	}

	@Test
	public void shouldGetActiveRideForPassenger() {
		int passengerId = 1;
		ride.setStatus(RideStatus.ACTIVE);

		Mockito.when(allRides.getActiveRideForPassenger(passengerId)).thenReturn(ride);
		Mockito.when(allPassengers.findById(passengerId)).thenReturn(Optional.of(passenger));
		
		RideReturnedDTO ret = this.rideService.getActiveRideForPassenger(passengerId);

		assertTrue(ret != null);
		assertEquals(ret.getId(), ride.getId());
	}

	@Test(expectedExceptions = NoActivePassengerRideException.class)
	public void shouldThrowExceptionForNoActiveRidesPassenger() {
		int passengerId = 1;
		
		Mockito.when(allPassengers.findById(passengerId)).thenReturn(Optional.of(passenger));
		Mockito.when(allRides.getActiveRideForPassenger(passengerId)).thenReturn(null);

		RideReturnedDTO ret = this.rideService.getActiveRideForPassenger(passengerId);
	}
	
	@Test(expectedExceptions = UserNotFoundException.class)
	public void shouldThrowExceptionForNonExistantPassengerId() {
		int passengerId = 0;
		
		Mockito.when(allPassengers.findById(passengerId)).thenReturn(Optional.empty());

		RideReturnedDTO ret = this.rideService.getActiveRideForPassenger(passengerId);
	
		verifyNoInteractions(this.allRides);
	}

	@Test
	public void shouldInsertFavoriteRideForValidInputs() {
		User currentUser = (User) ride.getPassengers().toArray()[0];
		List<UserInRideDTO> passengers = new ArrayList<>();
		passengers.add(new UserInRideDTO(currentUser));

		Passenger passenger = new Passenger(new UserDTO());
		passenger.setId(currentUser.getId());

		RouteLocsDTO location = new RouteLocsDTO(new LocationNoIdDTO(ride.getDepartureLocation()),
				new LocationNoIdDTO(ride.getDestinationLocation()));
		List<RouteLocsDTO> locations = new ArrayList<>();
		locations.add(location);

		FavoriteRideDTO dto = new FavoriteRideDTO("Home", locations, passengers, ride.getVehicleType().getName(), false,
				false);

		Mockito.when(this.userService.getCurrentUser()).thenReturn(currentUser);
		Mockito.when(this.passengerService.getPassenger(currentUser.getId())).thenReturn(passenger);
		Mockito.when(this.passengerService.getFavoriteRides(currentUser.getId())).thenReturn(new ArrayList<>());
		Mockito.when(this.passengerService.getById(currentUser.getId())).thenReturn(new UserReturnedDTO(currentUser));
		Mockito.when(this.allPassengers.findPassengerByEmail(currentUser.getEmail()))
				.thenReturn(Optional.of(passenger));
		Mockito.when(this.vehicleTypeService.getByName(ride.getVehicleType().getName()))
				.thenReturn(ride.getVehicleType());

		FavoriteRideReturnedDTO ret = this.rideService.insertFavoriteRide(dto);

		assertTrue(ret.getVehicleType() == dto.getVehicleType());
		assertTrue(ret.getFavoriteName() == dto.getFavoriteName());
		verify(this.allFavoriteRides).flush();
		verify(this.allPassengers).save(passenger);
	}

	@Test(expectedExceptions = { FavoriteRideException.class })
	public void shouldThrowExceptionForInsertingFavoriteRideForPassengerWithMoreThen10() {
		User currentUser = (User) ride.getPassengers().toArray()[0];
		List<FavoriteRide> rides = Stream.generate(FavoriteRide::new).limit(10)
				.collect(Collectors.toCollection(ArrayList::new));

		Mockito.when(this.userService.getCurrentUser()).thenReturn(currentUser);
		Mockito.when(this.passengerService.getFavoriteRides(currentUser.getId())).thenReturn(rides);

		FavoriteRideReturnedDTO ret = this.rideService
				.insertFavoriteRide(new FavoriteRideDTO(null, null, null, null, false, false));

		verify(this.passengerService, never()).getPassenger(currentUser.getId());
	}

	@Test(expectedExceptions = { ResponseStatusException.class })
	public void shouldThrowExceptionForInsertingFavoriteRideWhereCurrentUserIsNotInRide() {
		User currentUser = (User) ride.getPassengers().toArray()[0];
		List<UserInRideDTO> passengers = new ArrayList<>();

		User passenger = new User();
		passenger.setId(0);
		passengers.add(new UserInRideDTO(passenger));

		RouteLocsDTO location = new RouteLocsDTO(new LocationNoIdDTO(ride.getDepartureLocation()),
				new LocationNoIdDTO(ride.getDestinationLocation()));
		List<RouteLocsDTO> locations = new ArrayList<>();
		locations.add(location);

		FavoriteRideDTO dto = new FavoriteRideDTO("Home", locations, passengers, ride.getVehicleType().getName(), false,
				false);

		Mockito.when(this.userService.getCurrentUser()).thenReturn(currentUser);
		Mockito.when(this.passengerService.getFavoriteRides(currentUser.getId())).thenReturn(new ArrayList<>());

		try {
			FavoriteRideReturnedDTO ret = this.rideService.insertFavoriteRide(dto);
		} catch (ResponseStatusException ex) {
			assertEquals(ex.getStatusCode(), HttpStatus.BAD_REQUEST);
			assertEquals(ex.getReason(), "Logged in user is not in the ride!");
			throw ex;
		}
	}

	@Test
	public void shouldGetAllRidesBewteenDates() {
		String from = "2022/12/25";
		String to = "2023/02/07";

		List<Ride> rides = new ArrayList<>();
		rides.add(ride);

		Mockito.when(this.allRides.getAllRidesBetweenDates(Mockito.any(LocalDateTime.class),
				Mockito.any(LocalDateTime.class))).thenReturn(rides);

		List<RideForReportDTO> ret = this.rideService.getAllRidesBetweenDates(from, to);

		assertEquals(ret.size(), 1);
		assertEquals(ret.get(0).getStartTime(), ride.getStartTime());
		verify(this.allRides).getAllRidesBetweenDates(Mockito.any(LocalDateTime.class),
				Mockito.any(LocalDateTime.class));
	}

	@Test
	public void shouldReturnEmptyListWhenNoRidesInDates() {
		String from = "2022/12/25";
		String to = "2023/02/07";

		List<Ride> rides = new ArrayList<>();

		Mockito.when(this.allRides.getAllRidesBetweenDates(Mockito.any(LocalDateTime.class),
				Mockito.any(LocalDateTime.class))).thenReturn(rides);

		List<RideForReportDTO> ret = this.rideService.getAllRidesBetweenDates(from, to);

		assertEquals(ret.size(), 0);
		verify(this.allRides).getAllRidesBetweenDates(Mockito.any(LocalDateTime.class),
				Mockito.any(LocalDateTime.class));
	}

	@Test(expectedExceptions = { ResponseStatusException.class })
	public void shouldThrowExceptionForGettingRidesBetweenInvalidFomrattedDates() {
		String from = "2.12.2022.";
		String to = "3.12.2022.";

		try {
			this.rideService.getAllRidesBetweenDates(from, to);
		} catch (ResponseStatusException ex) {
			assertEquals(ex.getStatusCode(), HttpStatus.BAD_REQUEST);
			assertEquals(ex.getReason(), "Wrong date format! Use yyyy/MM/dd.");
			throw ex;
		}
	}

	@Test
	public void shouldGetRideForExistingId() {
		Mockito.when(allRides.findById(ride.getId())).thenReturn(Optional.of(ride));

		RideReturnedDTO ret = this.rideService.getRide(ride.getId());

		assertTrue(ret.getId() == ride.getId());
	}

	@Test(expectedExceptions = { RideNotFoundException.class })
	public void shouldThrowExceptionForNonExistingRideId() {
		RideReturnedDTO ride = this.rideService.getRide(NON_EXISTANT_RIDE_ID);
	}

	@Test
	public void shouldDeleteFavoriteRideForExistingId() {
		FavoriteRide favRide = new FavoriteRide();
		favRide.setId(1);
		passenger.setFavouriteRides(new HashSet<FavoriteRide>());
		passenger.getFavouriteRides().add(favRide);
		Mockito.when(allFavoriteRides.findById(favRide.getId())).thenReturn(Optional.of(favRide));
		doNothing().when(allFavoriteRides).delete(favRide);
		Mockito.when(passengerService.getPassenger(passenger.getId())).thenReturn(passenger);
		Mockito.when(userService.getCurrentUser()).thenReturn(passenger);

		this.rideService.deleteFavoriteRide(ride.getId());
		assertTrue(passenger.getFavouriteRides().size() == 0);
		verify(allFavoriteRides, times(1)).flush();
	}

	@Test(expectedExceptions = { FavoriteRideException.class })
	public void shouldThrowExceptionForNonExistingFavoriteRideId() {
		FavoriteRide favRide = new FavoriteRide();
		favRide.setId(1);
		passenger.setFavouriteRides(new HashSet<FavoriteRide>());
		passenger.getFavouriteRides().add(favRide);

		Mockito.when(passengerService.getPassenger(passenger.getId())).thenReturn(passenger);
		Mockito.when(userService.getCurrentUser()).thenReturn(passenger);
		Mockito.when(allFavoriteRides.findById(anyInt())).thenReturn(Optional.empty());

		this.rideService.deleteFavoriteRide(ride.getId());
		assertTrue(passenger.getFavouriteRides().size() == 1);
	}

	@Test
	public void shouldAcceptRide() {
		ride.setStatus(RideStatus.PENDING);
		Mockito.when(allRides.findById(ride.getId())).thenReturn(Optional.of(ride));
		Mockito.when(allRides.save(ride)).thenReturn(ride);

		RideReturnedDTO ret = this.rideService.acceptRide(ride.getId());

		assertTrue(ret.getId() == ride.getId());
		assertTrue(ret.getStatus() == RideStatus.ACCEPTED);
	}

	@Test(expectedExceptions = { ResponseStatusException.class })
	public void shouldThrowExceptionForAcceptingRideWithNonExistingId() {
		try {
			Mockito.when(allRides.findById(anyInt())).thenReturn(Optional.empty());
			this.rideService.acceptRide(0);
		} catch (ResponseStatusException ex) {
			assertEquals(ex.getStatusCode(), HttpStatus.NOT_FOUND);
			assertEquals(ex.getReason(), "Ride does not exist!");
			throw ex;
		}
	}

	@Test(expectedExceptions = { ResponseStatusException.class })
	public void shouldThrowExceptionForAcceptingRideWithBadStatus() {
		this.ride.setStatus(RideStatus.FINISHED);
		Mockito.when(allRides.findById(ride.getId())).thenReturn(Optional.of(ride));

		try {
			this.rideService.acceptRide(ride.getId());
		} catch (ResponseStatusException ex) {
			assertEquals(ex.getStatusCode(), HttpStatus.BAD_REQUEST);
			assertEquals(ex.getReason(), "Cannot accept a ride that is not in status PENDING!");
			throw ex;
		}
	}

	@Test
	public void shouldCancelRide() {
		ride.setStatus(RideStatus.PENDING);
		Mockito.when(allRides.findById(ride.getId())).thenReturn(Optional.of(ride));
		Mockito.when(allRides.save(ride)).thenReturn(ride);

		RideReturnedDTO ret = this.rideService.cancelRide(ride.getId());

		assertTrue(ret.getId() == ride.getId());
		assertTrue(ret.getStatus() == RideStatus.CANCELED);
	}

	@Test(expectedExceptions = { ResponseStatusException.class })
	public void shouldThrowExceptionForCancelingRideWithNonExistingId() {
		try {
			Mockito.when(allRides.findById(anyInt())).thenReturn(Optional.empty());
			this.rideService.cancelRide(0);
		} catch (ResponseStatusException ex) {
			assertEquals(ex.getStatusCode(), HttpStatus.NOT_FOUND);
			assertEquals(ex.getReason(), "Ride does not exist!");
			throw ex;
		}
	}

	@Test(expectedExceptions = { ResponseStatusException.class })
	public void shouldThrowExceptionForCancelingRideWithBadStatus() {
		this.ride.setStatus(RideStatus.FINISHED);
		Mockito.when(allRides.findById(ride.getId())).thenReturn(Optional.of(ride));

		try {
			this.rideService.cancelRide(ride.getId());
		} catch (ResponseStatusException ex) {
			assertEquals(ex.getStatusCode(), HttpStatus.BAD_REQUEST);
			assertEquals(ex.getReason(), "Cannot cancel a ride that is not in status PENDING or STARTED!");
			throw ex;
		}
	}

	@Test
	public void shouldGetRideSugestionPrice() {
		UnregisteredRideSuggestionDTO dto = new UnregisteredRideSuggestionDTO(vehicleType.getName().toString(),
				DISTANCE);
		Mockito.when(allVehicleTypes.getByName(vehicleType.getName())).thenReturn(vehicleType);
		Double ret = this.rideService.getRideSugestionPrice(dto);
		assertEquals(ret, vehicleType.getPricePerKm() * DISTANCE);
	}

	@Test(expectedExceptions = { NullPointerException.class })
	public void shouldThrowExceptionWhenTryingToGetPriceButVehicleIsNull() {
		UnregisteredRideSuggestionDTO dto = new UnregisteredRideSuggestionDTO(null, 1.0);
		Double ret = this.rideService.getRideSugestionPrice(dto);
	}

	@Test
	public void shouldGetFavoriteRides() {
		Set<Route> routes = new HashSet<Route>();
		routes.add(new Route(1, ride.getDepartureLocation(), ride.getDestinationLocation(), 10.0));
		FavoriteRide favRide = new FavoriteRide(1, "", routes, ride.getPassengers(), vehicleType, true, true);
		List<FavoriteRide> favRides = new ArrayList<FavoriteRide>();
		favRides.add(favRide);

		Mockito.when(passengerService.getFavoriteRides(passenger.getId())).thenReturn(favRides);
		Mockito.when(userService.getCurrentUser()).thenReturn(passenger);

		List<FavoriteRideReturnedDTO> ret = this.rideService.getFavoriteRides();
		assertEquals(favRides.size(), ret.size());
		assertEquals(ret.get(0).getId(), favRide.getId());
	}

}
