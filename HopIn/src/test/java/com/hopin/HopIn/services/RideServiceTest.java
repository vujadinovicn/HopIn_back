package com.hopin.HopIn.services;

import static org.testng.Assert.assertTrue;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.hopin.HopIn.HopInApplication;
import com.hopin.HopIn.dtos.RideReturnedDTO;
import com.hopin.HopIn.entities.Driver;
import com.hopin.HopIn.entities.Location;
import com.hopin.HopIn.entities.Passenger;
import com.hopin.HopIn.entities.Ride;
import com.hopin.HopIn.entities.VehicleType;
import com.hopin.HopIn.enums.RideStatus;
import com.hopin.HopIn.enums.VehicleTypeName;
import com.hopin.HopIn.repositories.RideRepository;
import com.hopin.HopIn.services.interfaces.IRideService;

@ActiveProfiles("test")
@SpringBootTest(classes=HopInApplication.class)
@TestPropertySource(properties = "classpath:application-test.properties")
public class RideServiceTest extends AbstractTestNGSpringContextTests {

	@Autowired
	private IRideService rideService;
	
	@MockBean
	private RideRepository allRides;
	
	private Ride ride;
	
	@BeforeMethod
	public void setup() {
		Driver driver = new Driver(1, "Pera", "Peric", "pera@gmail.com", "123", "Adresa", "4384294", null);
		Passenger passenger = new Passenger(2, "Lara", "Peric", "lara@gmail.com", "123", "Adresa", "4384294", null, null, null);
		
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
	
}
