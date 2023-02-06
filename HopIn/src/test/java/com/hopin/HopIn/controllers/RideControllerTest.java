package com.hopin.HopIn.controllers;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hopin.HopIn.dtos.CredentialsDTO;
import com.hopin.HopIn.dtos.PanicRideDTO;
import com.hopin.HopIn.dtos.ReasonDTO;
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
	private final static int DRIVER_ID = 2;
	private final static String PASSWORD_DRIVER = "123";
	
	private final static int DRIVER_WITH_NO_RIDE = 5;
	private final static int SCHEDULED_RIDE_ID = 4;
	
	private final static String USERNAME_ADMIN = "admin@gmail.com";
	private final static String PASSWORD_ADMIN = "123";
	
	private static String TOKEN_PASSENGER;
	private static String TOKEN_DRIVER;
	private static String TOKEN_ADMIN;
	
	private static int ACCEPTED_RIDE_ID = 1;
	private static int PENDING_RIDE_ID = 2;
	private static int INVALID_RIDE_ID = -1;
	private static int STARTED_RIDE_ID = 3;
	private static int NON_EXISTING_RIDE_ID = 169;
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
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<>(headers);
		
		return entity;
	}
	
	private HttpEntity<String> makeJwtHeaderWithRequestBody(String body, String token) {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(token);
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<>(body, headers);
		
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
	public void shouldStartRide() {
		ResponseEntity<RideReturnedDTO> res = restTemplate.withBasicAuth("driver@gmail.com", "123")
														  .exchange("/api/ride/" + ACCEPTED_RIDE_ID + "/start", HttpMethod.PUT, makeJwtHeader(TOKEN_DRIVER), RideReturnedDTO.class);
		
		RideReturnedDTO ride = res.getBody();
		
		assertEquals(HttpStatus.OK, res.getStatusCode());
		assertEquals(ride.getStatus(), RideStatus.STARTED);
		assertEquals(ride.getId(), ACCEPTED_RIDE_ID);
		assertTrue(ride.getStartTime() != null);
	}
	
	private String convertReasonDTOToJson(String r) {
		ReasonDTO reason = new ReasonDTO(r);
		ObjectMapper mapper = new ObjectMapper();
		mapper.findAndRegisterModules(); 
		String jsonString = null;
		
		try {
			jsonString = mapper.writeValueAsString(reason);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return jsonString;
	}
	@Test
	public void shouldThrowUnauthorizedExceptionWhenRejectingRide() {
		ResponseEntity<RideReturnedDTO> res = restTemplate.withBasicAuth("driver@gmail.com", "123")
				  .exchange("/api/ride/" + PENDING_RIDE_ID + "/cancel", HttpMethod.PUT, null, RideReturnedDTO.class);
		assertEquals(res.getStatusCode(), HttpStatus.UNAUTHORIZED);
	}
	
	@Test
	public void shouldThrowForbiddenExceptionWhenRejectingRideAsAdmin() {
		ResponseEntity<String> res = restTemplate.exchange("/api/ride/" + PENDING_RIDE_ID + "/cancel", HttpMethod.PUT, makeJwtHeader(TOKEN_ADMIN), String.class);
		assertEquals(res.getStatusCode(), HttpStatus.FORBIDDEN);
	}
	
	@Test
	public void shouldThrowJSONExceptionWhenRejectingRideWithInvalidRideId() {
		ResponseEntity<String> res = restTemplate.exchange("/api/ride/" + INVALID_RIDE_ID + "/cancel", HttpMethod.PUT, makeJwtHeader(TOKEN_DRIVER), String.class);
		assertNotEquals(res.getStatusCode(), HttpStatus.OK);
	}
	
	@Test
	public void shouldThrowJSONExceptionWhenRejectingRideWithInvalidReason() {
		ResponseEntity<String> res = restTemplate.withBasicAuth("driver@gmail.com", "123").exchange
				("/api/ride/" + PENDING_RIDE_ID + "/cancel", HttpMethod.PUT, makeJwtHeaderWithRequestBody(convertReasonDTOToJson("reason"), TOKEN_DRIVER), String.class);
		assertNotEquals(res.getStatusCode(), HttpStatus.OK);
	}
	
	@Test
	public void shouldThrowBadRequestExceptionWhenRejectingRideWithInvalidStatus() {
		ResponseEntity<ExceptionDTO> res = restTemplate.exchange("/api/ride/" + STARTED_RIDE_ID + "/cancel", HttpMethod.PUT, makeJwtHeaderWithRequestBody(convertReasonDTOToJson("reason"), TOKEN_DRIVER), ExceptionDTO.class);
		assertEquals(HttpStatus.BAD_REQUEST, res.getStatusCode());
		assertEquals(res.getBody().getMessage(), "Cannot cancel a ride that is not in status PENDING or ACCEPTED!");
	}
	
	@Test
	public void shouldThrowNotFoundExceptionWhenRejectingRideForNonExistingRide() {
		ResponseEntity<String> res = restTemplate.withBasicAuth("driver@gmail.com", "123")
				  .exchange("/api/ride/" + NON_EXISTING_RIDE_ID + "/cancel", HttpMethod.PUT, makeJwtHeaderWithRequestBody(convertReasonDTOToJson("reason"), TOKEN_DRIVER), String.class);
		assertEquals(HttpStatus.NOT_FOUND, res.getStatusCode());
		assertEquals(res.getBody(), "Ride does not exist!");
	}
	
	
	@Test
	public void shouldRejectRide() {
		ResponseEntity<RideReturnedDTO> res = restTemplate.withBasicAuth("driver@gmail.com", "123")
				  .exchange("/api/ride/" + PENDING_RIDE_ID + "/cancel", HttpMethod.PUT, makeJwtHeaderWithRequestBody(convertReasonDTOToJson("reason"), TOKEN_DRIVER), RideReturnedDTO.class);
		
		RideReturnedDTO ride = res.getBody();
		
		assertEquals(HttpStatus.OK, res.getStatusCode());
		assertEquals(ride.getStatus(), RideStatus.REJECTED);
		assertEquals(ride.getId(), PENDING_RIDE_ID);
		assertTrue(ride.getStartTime() == null);
	}
	
	@Test
	public void shouldThrowUnauthorizedExceptionWhenPanickingRide() {
		ResponseEntity<?> res = restTemplate.exchange("/api/ride/" + STARTED_RIDE_ID + "/panic", HttpMethod.PUT, null, RideReturnedDTO.class);
		assertEquals(res.getStatusCode(), HttpStatus.UNAUTHORIZED);
	}
	
	@Test
	public void shouldThrowForbiddenExceptionWhenPanickingRideAsAdmin() {
		ResponseEntity<?> res = restTemplate.exchange("/api/ride/" + PENDING_RIDE_ID + "/panic", HttpMethod.PUT, makeJwtHeader(TOKEN_ADMIN), String.class);
		assertEquals(res.getStatusCode(), HttpStatus.FORBIDDEN);
	}
	
	@Test
	public void shouldThrowJSONExceptionWhenPanickingRideWithInvalidRideId() {
		ResponseEntity<String> res = restTemplate.exchange("/api/ride/" + INVALID_RIDE_ID + "/panic", HttpMethod.PUT, makeJwtHeader(TOKEN_DRIVER), String.class);
		assertNotEquals(res.getStatusCode(), HttpStatus.OK);
	}
	
	@Test
	public void shouldThrowJSONExceptionWhenPanickingRideWithInvalidReason() {
		ResponseEntity<String> res = restTemplate.exchange("/api/ride/" + PENDING_RIDE_ID + "/panic", HttpMethod.PUT, makeJwtHeaderWithRequestBody(convertReasonDTOToJson(""), TOKEN_DRIVER), String.class);
		assertNotEquals(res.getStatusCode(), HttpStatus.OK);
	}
	
	@Test
	public void shouldThrowNotFoundExceptionWhenPanickingRideForNonExistingRide() {
		ResponseEntity<String> res = restTemplate.exchange("/api/ride/" + NON_EXISTING_RIDE_ID + "/panic", HttpMethod.PUT, makeJwtHeaderWithRequestBody(convertReasonDTOToJson("reason"), TOKEN_DRIVER), String.class);
		assertEquals(HttpStatus.NOT_FOUND, res.getStatusCode());
		assertEquals(res.getBody(), "Ride does not exist!");
	}
	
	
	@Test
	public void shouldPanicRide() {
		ResponseEntity<PanicRideDTO> res = restTemplate.exchange("/api/ride/" + STARTED_RIDE_ID + "/panic", HttpMethod.PUT, makeJwtHeaderWithRequestBody(convertReasonDTOToJson("reason"), TOKEN_DRIVER), PanicRideDTO.class);
		
		PanicRideDTO panic = res.getBody();
		
		assertEquals(HttpStatus.OK, res.getStatusCode());
		assertEquals(panic.getRide().getId(), STARTED_RIDE_ID);
		assertEquals(panic.getReason(), "reason");
	}
	
	@Test
	public void shouldThrowUnauthorizedExceptionWhenGettingActiveRideForDriver() {
		ResponseEntity<?> res = restTemplate.exchange("/api/ride/driver/" + DRIVER_ID + "/active", HttpMethod.GET, null, RideReturnedDTO.class);
		assertEquals(res.getStatusCode(), HttpStatus.UNAUTHORIZED);
	}
	
	@Test
	public void shouldThrowForbiddenExceptionWhenGettingActiveRideForDriverAsPassenger() {
		ResponseEntity<?> res = restTemplate.exchange("/api/ride/driver/" + DRIVER_ID + "/active", HttpMethod.GET, makeJwtHeader(TOKEN_PASSENGER), String.class);
		assertEquals(res.getStatusCode(), HttpStatus.FORBIDDEN);
	}
	
	@Test
	public void shouldThrowJSONExceptionWhenGettingActiveRideForDriverWithInvalidDriverId() {
		ResponseEntity<String> res = restTemplate.exchange("/api/ride/driver/" + INVALID_RIDE_ID + "/active", HttpMethod.GET, makeJwtHeader(TOKEN_DRIVER), String.class);
		assertNotEquals(res.getStatusCode(), HttpStatus.OK);
	}
	
	@Test
	public void shouldThrowNoActiveDriverRideExceptionWhenGettingActiveRideForDriver() {
		ResponseEntity<String> res = restTemplate.exchange("/api/ride/driver/" + DRIVER_WITH_NO_RIDE + "/active", HttpMethod.GET, makeJwtHeader(TOKEN_DRIVER), String.class);
		assertEquals(res.getStatusCode(), HttpStatus.NOT_FOUND);
		assertEquals("Active ride does not exist", res.getBody());
	}
	
	public void shouldGetActiveRideForDriver() {
		ResponseEntity<RideReturnedDTO> res = restTemplate.exchange("/api/ride/driver/" + DRIVER_ID + "/active", HttpMethod.GET, makeJwtHeader(TOKEN_DRIVER), RideReturnedDTO.class);
		
		RideReturnedDTO ride = res.getBody();
		
		assertEquals(HttpStatus.OK, res.getStatusCode());
		assertEquals(STARTED_RIDE_ID, ride.getId());
		assertEquals(RideStatus.STARTED, ride.getStatus());
		assertEquals(ride.getDriver().getId(), DRIVER_ID);
	}
	
	@Test
	public void shouldThrowUnauthorizedExceptionWhenGettingPendingRideForDriver() {
		ResponseEntity<?> res = restTemplate.exchange("/api/ride/driver/" + DRIVER_ID + "/pending", HttpMethod.GET, null, RideReturnedDTO.class);
		assertEquals(res.getStatusCode(), HttpStatus.UNAUTHORIZED);
	}
	
	@Test
	public void shouldThrowForbiddenExceptionWhenGettingPendingRideForDriverAsPassenger() {
		ResponseEntity<?> res = restTemplate.exchange("/api/ride/driver/" + DRIVER_ID + "/pending", HttpMethod.GET, makeJwtHeader(TOKEN_PASSENGER), String.class);
		assertEquals(res.getStatusCode(), HttpStatus.FORBIDDEN);
	}
	
	@Test
	public void shouldThrowJSONExceptionWhenGettingPendingRideForDriverWithInvalidDriverId() {
		ResponseEntity<String> res = restTemplate.exchange("/api/ride/driver/" + INVALID_RIDE_ID + "/pending", HttpMethod.GET, makeJwtHeader(TOKEN_DRIVER), String.class);
		assertNotEquals(res.getStatusCode(), HttpStatus.OK);
	}
	
	@Test
	public void shouldThrowNoActiveDriverRideExceptionWhenGettingPendingRideForDriver() {
		ResponseEntity<String> res = restTemplate.exchange("/api/ride/driver/" + DRIVER_WITH_NO_RIDE + "/pending", HttpMethod.GET, makeJwtHeader(TOKEN_DRIVER), String.class);
		assertEquals(res.getStatusCode(), HttpStatus.NOT_FOUND);
		assertEquals("Active ride does not exist", res.getBody());
	}
	@Test
	public void shouldGetPendingRideForDriver() {
		ResponseEntity<RideReturnedDTO> res = restTemplate.exchange("/api/ride/driver/" + DRIVER_ID + "/pending", HttpMethod.GET, makeJwtHeader(TOKEN_DRIVER), RideReturnedDTO.class);
		
		RideReturnedDTO ride = res.getBody();
		
		assertEquals(HttpStatus.OK, res.getStatusCode());
		assertEquals(PENDING_RIDE_ID, ride.getId());
		assertEquals(RideStatus.PENDING, ride.getStatus());
		assertEquals(ride.getDriver().getId(), DRIVER_ID);
	}
	
	@Test
	public void shouldThrowUnauthorizedExceptionWhenGetScheduledRidesForUser() {
		ResponseEntity<?> res = restTemplate.exchange("/api/ride/scheduled-rides/" + INVALID_RIDE_ID, HttpMethod.GET, null, RideReturnedDTO.class);
		assertEquals(res.getStatusCode(), HttpStatus.UNAUTHORIZED);
	}
	
	@Test
	public void shouldThrowForbiddenExceptionWhenGettingScheduledRidesForUserAsAdmin() {
		ResponseEntity<?> res = restTemplate.exchange("/api/ride/scheduled-rides/" + INVALID_RIDE_ID, HttpMethod.GET, makeJwtHeader(TOKEN_ADMIN), String.class);
		assertEquals(res.getStatusCode(), HttpStatus.FORBIDDEN);
	}
	
	@Test
	public void shouldThrowJSONExceptionWhenGettingScheduledRidesForUserWithInvalidUserId() {
		ResponseEntity<String> res = restTemplate.exchange("/api/ride/scheduled-rides/" + INVALID_RIDE_ID, HttpMethod.GET, makeJwtHeader(TOKEN_DRIVER), String.class);
		assertNotEquals(res.getStatusCode(), HttpStatus.OK);
	}
	
	@Test
	public void shouldThrowUserNotFoundExceptionWhenGettingScheduledRidesForUser() {
		ResponseEntity<String> res = restTemplate.exchange("/api/ride/scheduled-rides/" + NON_EXISTING_RIDE_ID, HttpMethod.GET, makeJwtHeader(TOKEN_DRIVER), String.class);
		assertEquals(res.getStatusCode(), HttpStatus.BAD_REQUEST);
		assertEquals("User not found", res.getBody());
	}
	
	@Test
	public void shouldGetScheduledRidesForUser() {
		ResponseEntity<ArrayList<RideReturnedDTO>> res = restTemplate.exchange("/api/ride/scheduled-rides/" + DRIVER_ID, HttpMethod.GET, makeJwtHeader(TOKEN_DRIVER), new ParameterizedTypeReference<ArrayList<RideReturnedDTO>>() {});
		
		List<RideReturnedDTO> rides = res.getBody();
		assertEquals(HttpStatus.OK, res.getStatusCode());
		assertTrue(rides.stream().allMatch(ride -> ride.getStatus() == RideStatus.ACCEPTED && ride.getScheduledTime() != null));
		assertTrue(rides.stream().filter(ride -> ride.getDriver().getId() == DRIVER_ID).findAny()
				.orElse(null) != null);
		assertTrue(rides.stream().filter(ride -> ride.getId() == SCHEDULED_RIDE_ID).findAny()
				.orElse(null) != null);
	}
	
	
	
	
}
