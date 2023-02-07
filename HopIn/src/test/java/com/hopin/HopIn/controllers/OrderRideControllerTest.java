package com.hopin.HopIn.controllers;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
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
import com.hopin.HopIn.dtos.LocationDTO;
import com.hopin.HopIn.dtos.LocationNoIdDTO;
import com.hopin.HopIn.dtos.ReasonDTO;
import com.hopin.HopIn.dtos.RideDTO;
import com.hopin.HopIn.dtos.TokenDTO;
import com.hopin.HopIn.dtos.UserInRideDTO;
import com.hopin.HopIn.entities.Location;
import com.hopin.HopIn.enums.VehicleTypeName;
import com.hopin.HopIn.validations.ExceptionDTO;

//@DirtiesContext(classMode= DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
//@TestPropertySource(properties = "classpath:application-test.properties")
//@Sql("classpath:data-test-controller.sql")
public class OrderRideControllerTest extends AbstractTestNGSpringContextTests {

	private final static String USERNAME_PASSENGER = "mika@gmail.com";
	private final static String PASSWORD_PASSENGER = "123";
	
	private final static String USERNAME_DRIVER = "driver@gmail.com";
	private final static String PASSWORD_DRIVER = "123";
	
	private final static int DRIVER_WITH_NO_RIDE = 5;
	
	private final static String USERNAME_ADMIN = "admin@gmail.com";
	private final static String PASSWORD_ADMIN = "123";
	
	private static String TOKEN_PASSENGER;
	private static String TOKEN_DRIVER;
	private static String TOKEN_ADMIN;
	
	private static int ACCEPTED_RIDE_ID = 1;
	private static int PENDING_RIDE_ID = 2;
    private static int STARTED_RIDE_ID = 3;
    private static int SCHEDULED_RIDE_ID = 4;
    private final static int RIDE_TO_CANCEL_ID = 5;
    private static final int FINISHED_RIDE_ID = 0;
    
	private static int NON_EXISTANT_RIDE_ID = 0;
	private static int INVALID_RIDE_ID = -1;
	
	private static int PASSENGER_ID = 1;
	private static int PASSENGER_NO_RIDES = 3;
	private static int PASSENGER_ONLY_STARTED_RIDE = 6;
    
	private final static int DRIVER_ID = 2;
    
    private final static int FAVORITE_RIDE_ID = 1;
    
	private final static double DISTANCE = 1.0;
	private final static double CAR_VEHICLE_PRICE = 60;
	
	private static RideDTO rideDTO;
	
	
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
		
		Location departure = new Location(3, "Jirecekova 1", 45.32, 24.3);
		Location destination = new Location(4, "Promenada", 45.32, 24.3);
		List<LocationDTO> locationsDTO = new ArrayList<LocationDTO>();
		locationsDTO.add(new LocationDTO(new LocationNoIdDTO(departure), new LocationNoIdDTO(destination)));
		List<UserInRideDTO> usersDTO = new ArrayList<UserInRideDTO>();
		rideDTO = new RideDTO(locationsDTO, usersDTO, VehicleTypeName.CAR, true, true);
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
	
	private String convertRideDTOToJson(RideDTO rideDTO) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.findAndRegisterModules(); 
		String jsonString = null;
		
		try {
			jsonString = mapper.writeValueAsString(rideDTO);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return jsonString;
	}
	
	@Test
	public void shouldThrowUnathorisedException_ForNoToken_Add() {
		ResponseEntity<String> res = restTemplate.exchange("/api/ride", HttpMethod.POST, null, String.class);
		assertEquals(HttpStatus.UNAUTHORIZED, res.getStatusCode());
	}
	
	@Test
	public void shouldThrowForbiddenException_ForWrongRole_Add() {
		ResponseEntity<String> res = restTemplate.exchange("/api/ride", HttpMethod.POST, makeJwtHeader(TOKEN_DRIVER), String.class);
		assertEquals(HttpStatus.FORBIDDEN, res.getStatusCode());
	}
	
	@Test
	public void shouldThrowJSONException_ForInvalidRideDTO_Add() {
		rideDTO.setBabyTransport(null);
		ResponseEntity<String> res = restTemplate.exchange("/api/ride", HttpMethod.POST, makeJwtHeaderWithRequestBody(convertRideDTOToJson(rideDTO), TOKEN_DRIVER), String.class);
		assertNotEquals(res.getStatusCode(), HttpStatus.OK);
	}
	
	@Test
	public void shouldThrowJSONException_ForNullRideDTO_Add() {
		ResponseEntity<String> res = restTemplate.exchange("/api/ride", HttpMethod.POST, makeJwtHeader(TOKEN_PASSENGER), String.class);
		assertEquals(res.getStatusCode(), HttpStatus.BAD_REQUEST);
	}
	
}
