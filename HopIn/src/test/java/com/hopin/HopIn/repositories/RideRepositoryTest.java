package com.hopin.HopIn.repositories;

import static org.junit.Assert.assertNull;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import com.hopin.HopIn.entities.Driver;
import com.hopin.HopIn.entities.Passenger;
import com.hopin.HopIn.entities.Ride;

@DataJpaTest
@ActiveProfiles("test")
//@SpringBootTest(classes=HopInApplication.class)
@TestPropertySource(properties = "classpath:application-test.properties")
//@Sql("classpath:data-test.sql")
public class RideRepositoryTest extends AbstractTestNGSpringContextTests {
	
	@Autowired
	private RideRepository rideRepository;
	
	
	@Test
	public void shouldGetAllRidesBetweenDates() {
		LocalDateTime end = LocalDate.of(2023, 2, 7).atStartOfDay();
		LocalDateTime start = LocalDate.of(2023, 1, 6).atStartOfDay();
		
		List<Ride> ret = this.rideRepository.getAllRidesBetweenDates(start, end);

		assertTrue(ret.size() == 1);
		assertTrue(ret.get(0).getId() == 2);
	}
	
	@Test
	public void shouldGetZeroRidesForWrongRange() {
		LocalDateTime end = LocalDate.of(2022, 2, 7).atStartOfDay();
		LocalDateTime start = LocalDate.of(2022, 1, 6).atStartOfDay();
		
		List<Ride> ret = this.rideRepository.getAllRidesBetweenDates(start, end);
		assertTrue(ret.size() == 0);
	}


	@Test
	public void shouldGetAllScheduledRidesForTodayForPassenger() {
		LocalDateTime date = LocalDate.of(2023, 2, 6).atStartOfDay();
		int userId = 1;
		
		List<Ride> ret = this.rideRepository.getAllScheduledRideForTodayForPassenger(userId, date);
		
		assertTrue(ret.size() == 1);
		assertTrue(ret.get(0).getId() == 1);
	}
	
	@Test
	public void shouldReturnEmptyListForPassengerForWrongDate() {
		LocalDateTime date = LocalDate.of(2023, 2, 3).atStartOfDay();
		int userId = 1;
		
		List<Ride> ret = this.rideRepository.getAllScheduledRideForTodayForPassenger(userId, date);
		
		assertTrue(ret.size() == 0);
	}
	
	@Test
	public void shouldGetAllScheduledRidesForTodayForDriver() {
		LocalDateTime date = LocalDate.of(2023, 2, 6).atStartOfDay();
		int userId = 2;
		
		List<Ride> ret = this.rideRepository.getAllScheduledRideForTodayForDriver(userId, date);
		
		assertTrue(ret.size() == 1);
		assertTrue(ret.get(0).getId() == 1);
	}
	
	@Test
	public void shouldReturnEmptyListForDriverForWrongDate() {
		LocalDateTime date = LocalDate.of(2023, 2, 3).atStartOfDay();
		int userId = 2;
		
		List<Ride> ret = this.rideRepository.getAllScheduledRideForTodayForDriver(userId, date);
		
		assertTrue(ret.size() == 0);
	}
	
	
	@Test
	public void shouldGetAllPassenegerRidesBetweenDates() {
		LocalDateTime end = LocalDate.of(2023, 2, 7).atStartOfDay();
		LocalDateTime start = LocalDate.of(2023, 1, 6).atStartOfDay();
		int id = 1;
		
		List<Ride> ret = this.rideRepository.getAllPassengerRidesBetweenDates(id, start, end);
		
		assertTrue(ret.size() == 1);
		assertTrue(ret.get(0).getId() == 2);
		
		Passenger passenger = new Passenger();
		for(Passenger p : ret.get(0).getPassengers()) {
			passenger = p;
			
		}
		
		assertTrue(passenger.getId() == id);
	}
	
	@Test
	public void shouldGetZeroPassengerRidesForWrongRange() {
		LocalDateTime end = LocalDate.of(2022, 2, 7).atStartOfDay();
		LocalDateTime start = LocalDate.of(2022, 1, 6).atStartOfDay();
		int id = 1;
		
		List<Ride> ret = this.rideRepository.getAllPassengerRidesBetweenDates(id, start, end);
		assertTrue(ret.size() == 0);
	}
	
	@Test
	public void shouldGetAllDriverRidesBetweenDates() {
		LocalDateTime end = LocalDate.of(2023, 2, 1).atStartOfDay();
		LocalDateTime start = LocalDate.of(2023, 1, 6).atStartOfDay();
		int id = 2;
		
		List<Ride> ret = this.rideRepository.getAllDriverRidesBetweenDates(id, start, end);
		
		assertTrue(ret.size() == 1);
		assertTrue(ret.get(0).getId() == 2);
		
		Driver driver = ret.get(0).getDriver();
		assertTrue(driver.getId() == id);
	}
	
	@Test
	public void shouldGetZeroDriverRidesForWrongRange() {
		LocalDateTime end = LocalDate.of(2022, 2, 7).atStartOfDay();
		LocalDateTime start = LocalDate.of(2022, 1, 6).atStartOfDay();
		int id = 1;
		
		List<Ride> ret = this.rideRepository.getAllDriverRidesBetweenDates(id, start, end);
		assertTrue(ret.size() == 0);
	}
	
	@Test 
	public void shouldGetAllPassengerRides() {
		int id = 1;
		
		List<Ride> ret = this.rideRepository.getAllPassengerRides(id);
		
		assertTrue(ret.size() > 0);
		for (Ride ride : ret) {
			int currentId = 0;
			for (Passenger passenger : ride.getPassengers()) {
				if (passenger.getId() == id) { currentId = passenger.getId(); }
			}
			assertTrue(id == currentId);
		}
	}
	
	
	@Test
	public void shouldGetAllPassengerRidesPaginated() {
		int id = 1;
		Pageable pageable = PageRequest.of(0, 5);
		
		List<Ride> ret = this.rideRepository.getAllPassengerRidesPaginated(id, pageable);
		
		assertTrue(ret.size() <= 5);
		for (Ride ride : ret) {
			int currentId = 0;
			for (Passenger passenger : ride.getPassengers()) {
				if (passenger.getId() == id) { currentId = passenger.getId(); }
			}
			assertTrue(id == currentId);
		}
	}
	
	@Test
	public void shouldGetAllUserRidesPaginatedForPassengerId() {
		int passengerId = 1;
		Pageable pageable = PageRequest.of(0, 5);

		List<Ride> passengerRides = this.rideRepository.getAllUserRides(passengerId, pageable);
		
		assertEquals(passengerRides.size(), 3);
		
		for (Ride ride : passengerRides) {
			int currentId = 0;
			for (Passenger passenger : ride.getPassengers()) {
				if (passenger.getId() == passengerId) { currentId = passenger.getId(); }
			}
			assertTrue(passengerId == currentId);
		}
	}
	
	@Test
	public void shouldGetAllUserRidesPaginatedForDriverId() {
		int driverId = 2;
		Pageable pageable = PageRequest.of(0, 5);

		List<Ride> driverRides = this.rideRepository.getAllUserRides(driverId, pageable);

		assertEquals(driverRides.size(), 2);
		
		for(Ride ride : driverRides) {
			assertTrue(driverId == ride.getDriver().getId());
		}
	}
	
	@Test
	public void shouldReturnNullForDriverThatHaNosActiveRideSInDb() {
		int driverId = 2;
		
		Ride ride = this.rideRepository.getActiveRideForDriver(driverId);
		
		assertTrue(ride == null);
	}
	
	@Test
	public void shouldGetActiveRideForDriverThatHasActiveRideInDb() {
		int driverId = 3;
		
		Ride ride = this.rideRepository.getActiveRideForDriver(driverId);
		
		assertTrue(ride != null);
		assertTrue(ride.getId() == 3);
	}
	
	@Test
	public void shouldGetScheduledRidesForDriverThatHasRideWithStatus1AndScheduledTimeInDb() {
		int driverId = 2;
		
		List<Ride> ret = this.rideRepository.getScheduledRidesForDriver(driverId);
		
		assertTrue(ret.size() == 1);
		assertTrue(ret.get(0).getId() == 1);
	}
	
	@Test
	public void shouldGetFirstUpcomingRideForDriver() {
		int driverId = 2;
		
		Ride ride = this.rideRepository.getFirstUpcomingRideForDriver(driverId);
		
		assertTrue(ride.getId() == 1);
	}
	
	@Test
	public void shouldGetPendingRideForPassenger() {
		int id = 1;
		
		Ride ride = this.rideRepository.getPendingRideForPassenger(id);
		
		System.out.println(ride);
		
		assertTrue(ride != null);
		
		Passenger passenger = new Passenger();
		for(Passenger p : ride.getPassengers()) {
			if (p.getId() == id) { passenger = p; }
		}
		
		assertTrue(passenger.getId() == id);
		
	}
	
	@Test
	public void shouldGetNullPassengerPandingRides() {
		int id = 2;
		
		Ride ride = this.rideRepository.getPendingRideForPassenger(id);
		
		assertNull(ride);
		
	}

	@Test
	public void shouldReturnEmptyListForDriverThatHasNoScheduledRides() {
		int driverId = 4;
		
		List<Ride> ret = this.rideRepository.getScheduledRidesForDriver(driverId);
		
		assertTrue(ret.size() == 0);
	}
	
	@Test
	public void shouldGetScheduledRidesForPassengerThatHasRideWithStatus1AndScheduledTimeInDb() {
		int passengerId = 1;
		
		List<Ride> ret = this.rideRepository.getScheduledRidesForPassenger(passengerId);
		
		assertTrue(ret.size() == 1);
		assertTrue(ret.get(0).getId() == 1);
	}
	
	@Test
	public void shouldReturnEmptyListForPassengerThatHasNoScheduledRides() {
		int passengerId = 2;
		
		List<Ride> ret = this.rideRepository.getScheduledRidesForPassenger(passengerId);
		
		assertTrue(ret.size() == 0);
	}
	
	@Test
	public void shouldReturnAllAcceptedRides() {
		List<Ride> ret = this.rideRepository.getAllAcceptedRides();
		
		assertTrue(ret.size() == 1);
		assertTrue(ret.get(0).getId() == 1);
	}
	
	@Test
	public void shouldGetPendingRideForDriver() {
		int driverId = 3;
		
		Ride ret = this.rideRepository.getPendingRideForDriver(driverId);
		
		assertTrue(ret != null);
		assertTrue(ret.getId() == 4);
	}
	
	@Test
	public void shouldReturnNullForDriverWithNoPendingRides() {
		int driverId = 1;
		
		Ride ret = this.rideRepository.getPendingRideForDriver(driverId);
		
		assertTrue(ret == null);
	}
	
	@Test
	public void shouldGetActiveRideForPassengerThatHasActiveRideInDb() {
		int passengerId = 3;
		
		Ride ride = this.rideRepository.getActiveRideForPassenger(passengerId);
		
		assertTrue(ride != null);
		assertTrue(ride.getId() == 3);
	}
	
	@Test
	public void shouldReturnNullForPassengerThatHaNosActiveRidesInDb() {
		int passengerId = 1;
		
		Ride ride = this.rideRepository.getActiveRideForPassenger(passengerId);
		
		assertTrue(ride == null);
	}
	
}
