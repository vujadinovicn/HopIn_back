package com.hopin.HopIn.services;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

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
import com.hopin.HopIn.dtos.RideReturnedDTO;
import com.hopin.HopIn.entities.Driver;
import com.hopin.HopIn.entities.FavoriteRide;
import com.hopin.HopIn.entities.Location;
import com.hopin.HopIn.entities.Passenger;
import com.hopin.HopIn.entities.Ride;
import com.hopin.HopIn.entities.VehicleType;
import com.hopin.HopIn.enums.RideStatus;
import com.hopin.HopIn.enums.VehicleTypeName;
import com.hopin.HopIn.exceptions.FavoriteRideException;
import com.hopin.HopIn.exceptions.RideNotFoundException;
import com.hopin.HopIn.repositories.FavoriteRideRepository;
import com.hopin.HopIn.repositories.RideRepository;
import com.hopin.HopIn.services.interfaces.IPassengerService;
import com.hopin.HopIn.services.interfaces.IRideService;
import com.hopin.HopIn.services.interfaces.IUserService;

@ActiveProfiles("test")
@SpringBootTest(classes=HopInApplication.class)
@TestPropertySource(properties = "classpath:application-test.properties")
public class RideServiceTest extends AbstractTestNGSpringContextTests {

	@Autowired
	private IRideService rideService;
	
	@MockBean
	private RideRepository allRides;
	
	@MockBean 
	private FavoriteRideRepository allFavouriteRides;
	
	@MockBean
	private IPassengerService passengerService;
	
	@MockBean 
	private IUserService userService;
	
	
	private Ride ride;
	private Passenger passenger;
	private FavoriteRide favoriteRoute;
	
	private static final int NON_EXISTANT_RIDE_ID = 123;
	
	@BeforeMethod
	public void setup() {
		Driver driver = new Driver(1, "Pera", "Peric", "pera@gmail.com", "123", "Adresa", "4384294", null);
		passenger = new Passenger(2, "Lara", "Peric", "lara@gmail.com", "123", "Adresa", "4384294", null, null, null);
		
		Location departure = new Location(1, "Jirecekova 1" , 45.32, 24.3);
		Location destination = new Location(1, "Promenada" , 45.32, 24.3);
		
		Set<Passenger> passengers = new HashSet<>();
		passengers.add(passenger);
		
		VehicleType type = new VehicleType(1, VehicleTypeName.CAR, 60);
		
		ride = new Ride();
		ride.setId(1);
		ride.setStatus(RideStatus.ACCEPTED);
		ride.setDepartureLocation(departure);
		ride.setDestinationLocation(destination);
		ride.setDriver(driver);
		ride.setPassengers(passengers);
		ride.setVehicleType(type);
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
	
	
	@Test
	public void shouldGetRideForExistingId() {
		Mockito.when(allRides.findById(ride.getId())).thenReturn(Optional.of(ride));
		
		RideReturnedDTO ret = this.rideService.getRide(ride.getId());
		
		assertTrue(ret.getId() == ride.getId());
	}
	
	@Test(expectedExceptions = {RideNotFoundException.class})
	public void shouldThrowExceptionForNonExistingRideId() {
		RideReturnedDTO ride = this.rideService.getRide(NON_EXISTANT_RIDE_ID);
	}	
	
	
	@Test
	public void shouldDeleteFavoriteRideForExistingId() {
		FavoriteRide favRide = new FavoriteRide();
		favRide.setId(1);
		passenger.setFavouriteRides(new HashSet<FavoriteRide>());
		passenger.getFavouriteRides().add(favRide);
		Mockito.when(allFavouriteRides.findById(favRide.getId())).thenReturn(Optional.of(favRide));
		doNothing().when(allFavouriteRides).delete(favRide);
		Mockito.when(passengerService.getPassenger(passenger.getId())).thenReturn(passenger);
		Mockito.when(userService.getCurrentUser()).thenReturn(passenger);

		this.rideService.deleteFavoriteRide(ride.getId());
		assertTrue(passenger.getFavouriteRides().size() == 0);
		verify(allFavouriteRides, times(1)).flush();
	}
	
	@Test(expectedExceptions = {FavoriteRideException.class})
	public void shouldThrowExceptionForNonExistingFavoriteRideId() {
		FavoriteRide favRide = new FavoriteRide();
		favRide.setId(1);
		passenger.setFavouriteRides(new HashSet<FavoriteRide>());
		passenger.getFavouriteRides().add(favRide);
		
		Mockito.when(passengerService.getPassenger(passenger.getId())).thenReturn(passenger);
		Mockito.when(userService.getCurrentUser()).thenReturn(passenger);
		Mockito.when(allFavouriteRides.findById(anyInt())).thenReturn(Optional.empty());
		
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
	
	@Test(expectedExceptions = {ResponseStatusException.class})
	public void shouldThrowExceptionForAcceptingRideWithNonExistingId() {
		try {
			Mockito.when(allRides.findById(anyInt())).thenReturn(Optional.empty());
			this.rideService.acceptRide(0);
		} catch (ResponseStatusException ex) {
			assertEquals(ex.getStatusCode(), HttpStatus.NOT_FOUND);
			assertEquals(ex.getReason(), "Ride does not exist!");
		}
	}
	
	@Test(expectedExceptions = {ResponseStatusException.class})
	public void shouldThrowExceptionForAcceptingRideWithBadStatus() {
		this.ride.setStatus(RideStatus.FINISHED);		
		Mockito.when(allRides.findById(ride.getId())).thenReturn(Optional.of(ride));

		try {
			this.rideService.acceptRide(ride.getId());
		} catch (ResponseStatusException ex) {
			assertEquals(ex.getStatusCode(), HttpStatus.BAD_REQUEST);
			assertEquals(ex.getReason(), "Cannot accept a ride that is not in status PENDING!");
		}
	}
	
	
	
	
	
	
}
