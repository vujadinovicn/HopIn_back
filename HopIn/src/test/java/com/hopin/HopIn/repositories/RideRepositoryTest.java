package com.hopin.HopIn.repositories;

import static org.testng.Assert.assertTrue;

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
//@TestPropertySource(locations="classpath:application-test.properties")
@Sql("classpath:test-data.sql")
public class RideRepositoryTest {
	
	@Autowired
	private RideRepository rideRepository;

	@Test
	public void shouldGetAllScheduledRidesForTodayForPassenger() {
		LocalDateTime date = LocalDateTime.of(2023, 2, 6, 7, 50);
		int userId = 1;
		
		List<Ride> ret = this.rideRepository.getAllScheduledRideForTodayForPassenger(userId, date);
		assertTrue(ret.size() == 1);
		assertTrue(ret.get(0).getId() == 1);
	}

}
