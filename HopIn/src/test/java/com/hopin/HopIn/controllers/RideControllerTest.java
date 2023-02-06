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
	public void shouldStartRide() {
		ResponseEntity<RideReturnedDTO> res = restTemplate.withBasicAuth("driver@gmail.com", "123")
														  .exchange("/api/ride/" + ACCEPTED_RIDE_ID + "/start", HttpMethod.PUT, makeJwtHeader(TOKEN_DRIVER), RideReturnedDTO.class);
		
		RideReturnedDTO ride = res.getBody();
		
		assertEquals(HttpStatus.OK, res.getStatusCode());
		assertEquals(ride.getStatus(), RideStatus.STARTED);
		assertEquals(ride.getId(), ACCEPTED_RIDE_ID);
		assertTrue(ride.getStartTime() != null);
	}
}
