package com.hopin.HopIn.controllers;


import static org.junit.Assert.assertTrue;
import static org.testng.Assert.assertEquals;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.hopin.HopIn.dtos.CredentialsDTO;
import com.hopin.HopIn.dtos.RideReturnedDTO;
import com.hopin.HopIn.dtos.TokenDTO;
import com.hopin.HopIn.enums.RideStatus;
import com.hopin.HopIn.validations.ExceptionDTO;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@TestPropertySource(properties = "classpath:application-test.properties")
//@Sql("classpath:data-test-controller.sql")
public class RideControllerTest extends AbstractTestNGSpringContextTests {
	
	private final static String USERNAME_PASSENGER = "mika@gmail.com";
	private final static String PASSWORD_PASSENGER = "123";
	
	private final static String USERNAME_DRIVER = "driver@gmail.com";
	private final static String PASSWORD_DRIVER = "123";
	
	private final static String USERNAME_ADMIN = "admin@gmail.com";
	private final static String PASSWORD_ADMIN = "123";
	
	private static String TOKEN_PASSENGER;
	private static String TOKEN_DRIVER;
	private static String TOKEN_ADMIN;
	
	private static int ACCEPTED_RIDE_ID = 1;
	private static int PENDING_RIDE_ID = 2;
    private static int STARTED_RIDE_ID = 3;
    
	private static int NON_EXISTANT_RIDE_ID = 0;
	private static int INVALID_RIDE_ID = -1;

	@Autowired
    private TestRestTemplate restTemplate;
	
	@BeforeMethod
	public void setup() {
		ResponseEntity<TokenDTO> tokenResPassenger = restTemplate.postForEntity("/api/user/login", new CredentialsDTO(USERNAME_PASSENGER, PASSWORD_PASSENGER), TokenDTO.class);
		TOKEN_PASSENGER = tokenResPassenger.getBody().getAccessToken();
		
		ResponseEntity<TokenDTO> tokenResDriver = restTemplate.postForEntity("/api/user/login", new CredentialsDTO(USERNAME_DRIVER, PASSWORD_DRIVER), TokenDTO.class);
		TOKEN_DRIVER= tokenResDriver.getBody().getAccessToken();
		
		ResponseEntity<TokenDTO> tokenResAdmin = restTemplate.postForEntity("/api/user/login", new CredentialsDTO(USERNAME_ADMIN, PASSWORD_ADMIN), TokenDTO.class);
		TOKEN_ADMIN = tokenResAdmin.getBody().getAccessToken();
	}
	
	private HttpEntity<String> makeJwtHeader(String token) {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(token);
		HttpEntity<String> entity = new HttpEntity<>(headers);
		
		return entity;
	}
	
	/*
	 * Pravimo potrebne podatke u data-test-controller.sql, PAZI DA NE ZEZNES OSTALE F-JE
	 * 
	 * Testiramo:
	 * 	1. UNAUTHORIZED
	 * 	2. FORBIDDEN
	 * 	3. LOSI PARAMETRI - los id, los dto
	 * 	4. SVE GRESKE KOJE VRACAJU
	 * 	5. HAPPY PATH
	 * 
	 * */
	
	@Test
	public void shouldReturnUnathorised_ForNoToken_StartRide() {
		ResponseEntity<String> res = restTemplate.exchange("/api/ride/" + ACCEPTED_RIDE_ID + "/start", HttpMethod.PUT, null, String.class);

		assertEquals(HttpStatus.UNAUTHORIZED, res.getStatusCode());
	}
	
	@Test
	public void shouldReturnForbidden_ForWrongRole_StartRide() {
		ResponseEntity<String> res = restTemplate.exchange("/api/ride/" + ACCEPTED_RIDE_ID + "/start", HttpMethod.PUT, makeJwtHeader(TOKEN_PASSENGER), String.class);

		assertEquals(HttpStatus.FORBIDDEN, res.getStatusCode());
	}
	
	@Test
	public void shouldReturnNotFound_ForNonExistantRide_StartRide() {
		ResponseEntity<String> res = restTemplate.exchange("/api/ride/" + NON_EXISTANT_RIDE_ID + "/start", HttpMethod.PUT, makeJwtHeader(TOKEN_DRIVER), String.class);

		assertEquals(HttpStatus.NOT_FOUND, res.getStatusCode());
		assertEquals("Ride does not exist!", res.getBody());
	}
	
	@Test
	public void shouldReturBadRequest_ForWrongRideStatus_StartRide() {
		ResponseEntity<ExceptionDTO> res = restTemplate.exchange("/api/ride/" + STARTED_RIDE_ID + "/start", HttpMethod.PUT, makeJwtHeader(TOKEN_DRIVER), ExceptionDTO.class);
		
		assertEquals(HttpStatus.BAD_REQUEST, res.getStatusCode());
		assertEquals("Cannot start a ride that is not in status ACCEPTED!", res.getBody().getMessage());
	}
	
	@Test
	public void shouldReturBadRequest_ForInvalidRideId_StartRide() {
		ResponseEntity<ExceptionDTO> res = restTemplate.exchange("/api/ride/" + INVALID_RIDE_ID + "/start", HttpMethod.PUT, makeJwtHeader(TOKEN_DRIVER), ExceptionDTO.class);
		
		assertEquals(HttpStatus.BAD_REQUEST, res.getStatusCode());
		assertEquals("Field id must be greater than 0.", res.getBody().getMessage());
	}
	
	@Test
	public void shouldStartRide() {
		ResponseEntity<RideReturnedDTO> res = restTemplate.exchange("/api/ride/" + ACCEPTED_RIDE_ID + "/start", HttpMethod.PUT, makeJwtHeader(TOKEN_DRIVER), RideReturnedDTO.class);
		
		RideReturnedDTO ride = res.getBody();
		
		assertEquals(HttpStatus.OK, res.getStatusCode());
		assertEquals(ride.getStatus(), RideStatus.STARTED);
		assertEquals(ride.getId(), ACCEPTED_RIDE_ID);
		assertTrue(ride.getStartTime() != null);
	}
	
	@Test
	public void shouldReturnUnathorised_ForNoToken_EndRide() {
		ResponseEntity<String> res = restTemplate.exchange("/api/ride/" + STARTED_RIDE_ID + "/end", HttpMethod.PUT, null, String.class);

		assertEquals(HttpStatus.UNAUTHORIZED, res.getStatusCode());
	}
	
	@Test
	public void shouldReturnForbidden_ForWrongRole_EndRide() {
		ResponseEntity<String> res = restTemplate.exchange("/api/ride/" + STARTED_RIDE_ID + "/end", HttpMethod.PUT, makeJwtHeader(TOKEN_PASSENGER), String.class);

		assertEquals(HttpStatus.FORBIDDEN, res.getStatusCode());
	}
	
	@Test
	public void shouldReturnNotFound_ForNonExistantRide_EndRide() {
		ResponseEntity<String> res = restTemplate.exchange("/api/ride/" + NON_EXISTANT_RIDE_ID + "/end", HttpMethod.PUT, makeJwtHeader(TOKEN_DRIVER), String.class);

		assertEquals(HttpStatus.NOT_FOUND, res.getStatusCode());
		assertEquals("Ride does not exist!", res.getBody());
	}
	
	@Test
	public void shouldReturBadRequest_ForWrongRideStatus_EndRide() {
		ResponseEntity<ExceptionDTO> res = restTemplate.exchange("/api/ride/" + ACCEPTED_RIDE_ID + "/end", HttpMethod.PUT, makeJwtHeader(TOKEN_DRIVER), ExceptionDTO.class);
		
		assertEquals(HttpStatus.BAD_REQUEST, res.getStatusCode());
		assertEquals("Cannot end a ride that is not in status STARTED!", res.getBody().getMessage());
	}
	
	@Test
	public void shouldReturBadRequest_ForInvalidRideId_EndRide() {
		ResponseEntity<ExceptionDTO> res = restTemplate.exchange("/api/ride/" + INVALID_RIDE_ID + "/end", HttpMethod.PUT, makeJwtHeader(TOKEN_DRIVER), ExceptionDTO.class);
		
		assertEquals(HttpStatus.BAD_REQUEST, res.getStatusCode());
		assertEquals("Field id must be greater than 0.", res.getBody().getMessage());
	}
	
	@Test
	public void shouldEndRide() {
		ResponseEntity<RideReturnedDTO> res = restTemplate.exchange("/api/ride/" + STARTED_RIDE_ID + "/end", HttpMethod.PUT, makeJwtHeader(TOKEN_DRIVER), RideReturnedDTO.class);
		
		RideReturnedDTO ride = res.getBody();
		
		assertEquals(HttpStatus.OK, res.getStatusCode());
		assertEquals(ride.getStatus(), RideStatus.FINISHED);
		assertEquals(ride.getId(), STARTED_RIDE_ID);
		assertTrue(ride.getEndTime() != null);
	}
}
