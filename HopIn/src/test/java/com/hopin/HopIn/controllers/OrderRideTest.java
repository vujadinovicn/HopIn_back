package com.hopin.HopIn.controllers;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testng.annotations.BeforeMethod;

import com.hopin.HopIn.HopInApplication;
import com.hopin.HopIn.dtos.CredentialsDTO;
import com.hopin.HopIn.dtos.RideReturnedDTO;
import com.hopin.HopIn.dtos.TokenDTO;
import com.hopin.HopIn.enums.RideStatus;

import jakarta.validation.constraints.AssertTrue;

//@DirtiesContext(classMode= DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = HopInApplication.class)
//@TestPropertySource(properties = "classpath:application-test.properties")
//@Sql("classpath:data-test-controller.sql")
public class OrderRideTest{
	
	private final static String USERNAME_PASSENGER = "mika@gmail.com";
	private final static String PASSWORD_PASSENGER = "123";
	
	private final static String USERNAME_DRIVER = "driver@gmail.com";
	private final static int DRIVER_ID = 2;
	private final static String PASSWORD_DRIVER = "123";
	
	private final static int DRIVER_WITH_NO_RIDE = 5;
	
	private final static String USERNAME_ADMIN = "admin@gmail.com";
	private final static String PASSWORD_ADMIN = "123";

    private static String TOKEN_PASSENGER;
    private static String TOKEN_DRIVER;
    private static String TOKEN_ADMIN;

    private final static int ACCEPTED_RIDE_ID = 1;
    private final static int PENDING_RIDE_ID = 2;
    private final static int STARTED_RIDE_ID = 3;
    private final static int SCHEDULED_RIDE_ID = 4;
    private final static int RIDE_TO_CANCEL_ID = 5;


    private final static int NON_EXISTANT_RIDE_ID = 0;
    private final static int INVALID_RIDE_ID = -1;

    private final static int PASSENGER_ID = 1;
    private final static int PASSENGER_NO_RIDES = 3;
    private final static int PASSENGER_ONLY_STARTED_RIDE = 6;
    
    private final static int FAVORITE_RIDE_ID = 1;
    
	private final static double DISTANCE = 1.0;
	private final static double CAR_VEHICLE_PRICE = 60;
	
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
	
	@Test
	@Sql(scripts = "classpath:data-test-controller2")
	public void shouldStartRide() {
		ResponseEntity<RideReturnedDTO> res = restTemplate.withBasicAuth("driver@gmail.com", "123")
														  .exchange("/api/ride/" + ACCEPTED_RIDE_ID + "/start", HttpMethod.PUT, makeJwtHeader(TOKEN_DRIVER), RideReturnedDTO.class);
		
		RideReturnedDTO ride = res.getBody();
		
		AssertTrue
	}
}
