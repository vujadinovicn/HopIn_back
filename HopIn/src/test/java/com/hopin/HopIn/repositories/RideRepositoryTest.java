package com.hopin.HopIn.repositories;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import com.hopin.HopIn.HopInApplication;
import com.hopin.HopIn.entities.Ride;
import com.hopin.HopIn.repositories.RideRepository;

@DataJpaTest
@ActiveProfiles("test")
//@SpringBootTest(classes=HopInApplication.class)
@TestPropertySource(properties = "classpath:application-test.properties")
//@Sql("classpath:data-test.sql")
public class RideRepositoryTest extends AbstractTestNGSpringContextTests {
	
	@Autowired
	private RideRepository rideRepository;
	
	@Test
	public void shouldGetAllPassengerRides() {
		int id = 1;
		
		List<Ride> ret = this.rideRepository.getAllPassengerRides(id);
		
		System.out.println(ret);
		assertEquals(ret.size(), 2);
	}
	
	@Test
	public void shouldGetAllRidesBetweenDates() {
		LocalDateTime start = LocalDateTime.of(2023, 2, 7, 7, 50, 1);
		LocalDateTime end = LocalDateTime.of(2023, 1, 6, 7, 50, 1);
		
		System.out.println(start);
		System.out.println(end);
		
		List<Ride> ret = this.rideRepository.getAllRidesBetweenDates(start, end);

		assertTrue(ret.size() == 1);
	}

	@Test
	public void shouldGetAllScheduledRidesForTodayForPassenger() {
		LocalDateTime date = LocalDate.of(2023, 2, 6).atStartOfDay();
		int userId = 1;
		
//		List<Ride> ret = this.rideRepository.getAllScheduledRideForTodayForPassenger(userId, date);
		
		
//		System.out.println(ret);
		System.out.println(this.rideRepository.findAll());
		
//		assertTrue(ret.size() == 1);
//		assertTrue(ret.get(0).getId() == 1);
	}

}
