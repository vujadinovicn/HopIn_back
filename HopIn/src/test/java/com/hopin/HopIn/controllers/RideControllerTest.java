package com.hopin.HopIn.controllers;


import static org.junit.Assert.assertTrue;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

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

import com.hopin.HopIn.dtos.CredentialsDTO;
import com.hopin.HopIn.dtos.RideReturnedDTO;
import com.hopin.HopIn.dtos.TokenDTO;
import com.hopin.HopIn.dtos.UnregisteredRideSuggestionDTO;
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
    private static int SCHEDULED_RIDE_ID = 4;
    private static int RIDE_TO_CANCEL_ID = 5;


    private static int NON_EXISTANT_RIDE_ID = 0;
    private static int INVALID_RIDE_ID = -1;

    private static int PASSENGER_ID = 1;
    private static int PASSENGER_NO_RIDES = 3;
    private static int PASSENGER_ONLY_STARTED_RIDE = 5;
    
    private static int FAVORITE_RIDE_ID = 1;
    
	private static double DISTANCE = 1.0;
	private static double CAR_VEHICLE_PRICE = 60;
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
	
	
	 /*GET_RIDE*/
	@Test
	public void shouldReturnUnathorised_ForNoToken_GetRide() {
        ResponseEntity<String> res = restTemplate.withBasicAuth("driver@gmail.com", "123")
                  .exchange("/api/ride/" + ACCEPTED_RIDE_ID, HttpMethod.GET, null, String.class);

        assertEquals(HttpStatus.UNAUTHORIZED, res.getStatusCode());
    }
	
	@Test
	public void shouldThrowForbiddenExceptionWhenGettingRideAsPassenger() {
		ResponseEntity<String> res = restTemplate.exchange("/api/ride/" + ACCEPTED_RIDE_ID, HttpMethod.GET, makeJwtHeader(TOKEN_PASSENGER), String.class);
		assertEquals(res.getStatusCode(), HttpStatus.FORBIDDEN);
	}
	
	@Test
	public void shouldThrowJSONExceptionWhenGettingRideWithInvalidRideId() {
		ResponseEntity<String> res = restTemplate.exchange("/api/ride/" + INVALID_RIDE_ID, HttpMethod.GET, makeJwtHeader(TOKEN_DRIVER), String.class);
		assertNotEquals(res.getStatusCode(), HttpStatus.OK);
	}
	
	@Test
	public void shouldThrowRideNotFoundExceptionWhenGettingRideForNonExistingRide() {
		ResponseEntity<String> res = restTemplate.withBasicAuth("driver@gmail.com", "123")
				  .exchange("/api/ride/" + NON_EXISTANT_RIDE_ID, HttpMethod.GET, makeJwtHeader(TOKEN_DRIVER), String.class);
		assertEquals(HttpStatus.NOT_FOUND, res.getStatusCode());
		assertEquals(res.getBody(), "Ride does not exist");
	}
	
	@Test
	public void shouldGetRideDetails() {
		ResponseEntity<RideReturnedDTO> res = restTemplate.withBasicAuth("driver@gmail.com", "123")
				  .exchange("/api/ride/" + ACCEPTED_RIDE_ID, HttpMethod.GET, makeJwtHeader(TOKEN_DRIVER), RideReturnedDTO.class);

		RideReturnedDTO ride = res.getBody();
		
		assertEquals(HttpStatus.OK, res.getStatusCode());
		assertEquals(ride.getId(), ACCEPTED_RIDE_ID);
	}
	
	
	/*DELETE_FAVORITES*/
	@Test
	public void shouldReturnUnathorised_ForNoToken_DeleteFavoriteRide() {
        ResponseEntity<String> res = restTemplate.exchange("/api/ride/favorites/" + FAVORITE_RIDE_ID, HttpMethod.DELETE, null, String.class);

        assertEquals(HttpStatus.UNAUTHORIZED, res.getStatusCode());
    }
	
	@Test
	public void shouldThrowForbiddenExceptionWhenDeletingFavoriteRideAsAdmin() {
		ResponseEntity<String> res = restTemplate.exchange("/api/ride/favorites/" + FAVORITE_RIDE_ID, HttpMethod.DELETE, makeJwtHeader(TOKEN_ADMIN), String.class);
		assertEquals(res.getStatusCode(), HttpStatus.FORBIDDEN);
	}
	
	@Test
	public void shouldThrowJSONExceptionWhenDeletingFavoriteRideWithInvalidFavoriteRideId() {
		ResponseEntity<String> res = restTemplate.exchange("/api/ride/favorites" + INVALID_RIDE_ID, HttpMethod.DELETE, makeJwtHeader(TOKEN_PASSENGER), String.class);
		assertNotEquals(res.getStatusCode(), HttpStatus.NO_CONTENT);
	}
	
	@Test
	public void shouldThrowFavoriteRideNotFoundExceptionWhenDeletingFavoriteRideForNonExistingFavoriteRide() {
		ResponseEntity<String> res = restTemplate.exchange("/api/ride/favorites/" + NON_EXISTANT_RIDE_ID, HttpMethod.DELETE, makeJwtHeader(TOKEN_PASSENGER), String.class);
		assertEquals(HttpStatus.NOT_FOUND, res.getStatusCode());
		assertEquals(res.getBody(), "Favorite location does not exist!");
	}
	
	@Test
	public void shouldDeleteFavoriteRide() {
		ResponseEntity<String> res = restTemplate.exchange("/api/ride/favorites/" + FAVORITE_RIDE_ID, HttpMethod.DELETE, makeJwtHeader(TOKEN_PASSENGER), String.class);
		assertEquals(res.getStatusCode(), HttpStatus.NO_CONTENT);
	}
	
	
	/*ACCEPT_RIDE*/
	@Test
	public void shouldReturnUnathorised_ForNoToken_AcceptRide() {
        ResponseEntity<String> res = restTemplate.exchange("/api/ride/" + PENDING_RIDE_ID + "/accept", HttpMethod.PUT, null, String.class);

        assertEquals(HttpStatus.UNAUTHORIZED, res.getStatusCode());
    }
	
	@Test
	public void shouldThrowForbiddenExceptionWhenAcceptingRideRideAsAdmin() {
		ResponseEntity<String> res = restTemplate.exchange("/api/ride/" + PENDING_RIDE_ID + "/accept", HttpMethod.PUT, makeJwtHeader(TOKEN_ADMIN), String.class);
		assertEquals(res.getStatusCode(), HttpStatus.FORBIDDEN);
	}
	
	@Test
	public void shouldThrowJSONExceptionWhenAcceptingRideWithInvalidRideId() {
		ResponseEntity<String> res = restTemplate.exchange("/api/ride/" + INVALID_RIDE_ID + "/accept", HttpMethod.PUT, makeJwtHeader(TOKEN_DRIVER), String.class);
		assertNotEquals(res.getStatusCode(), HttpStatus.OK);
	}
	
	@Test
	public void shouldThrowBadRequestExceptionWhenAcceptingRideWithInvalidStatus() {
		ResponseEntity<ExceptionDTO> res = restTemplate.exchange("/api/ride/" + ACCEPTED_RIDE_ID + "/accept", HttpMethod.PUT, makeJwtHeader(TOKEN_DRIVER), ExceptionDTO.class);
		assertEquals(HttpStatus.BAD_REQUEST, res.getStatusCode());
		assertEquals(res.getBody().getMessage(), "Cannot accept a ride that is not in status PENDING!");
	}
	
	@Test
	public void shouldThrowNotFoundExceptionWhenAcceptingRideWithNonExistingRide() {
		ResponseEntity<String> res = restTemplate.exchange("/api/ride/" + NON_EXISTANT_RIDE_ID + "/accept", HttpMethod.PUT, makeJwtHeader(TOKEN_DRIVER), String.class);
		assertEquals(HttpStatus.NOT_FOUND, res.getStatusCode());
		assertEquals(res.getBody(), "Ride does not exist!");
	}
	
	@Test
	public void shouldAcceptRide() {
		ResponseEntity<RideReturnedDTO> res = restTemplate.exchange("/api/ride/" + PENDING_RIDE_ID + "/accept", HttpMethod.PUT, makeJwtHeader(TOKEN_DRIVER), RideReturnedDTO.class);
		
		RideReturnedDTO ride = res.getBody();
		
		assertEquals(HttpStatus.OK, res.getStatusCode());
		assertEquals(ride.getStatus(), RideStatus.ACCEPTED);
		assertEquals(ride.getId(), PENDING_RIDE_ID);
	}
	
	
	/*CANCEL_RIDE*/
	@Test
	public void shouldReturnUnathorised_ForNoToken_CancelRide() {
        ResponseEntity<String> res = restTemplate.exchange("/api/ride/" + RIDE_TO_CANCEL_ID + "/withdraw", HttpMethod.PUT, null, String.class);

        assertEquals(HttpStatus.UNAUTHORIZED, res.getStatusCode());
    }
	
	@Test
	public void shouldThrowForbiddenExceptionWhenCancelingRideAsAdmin() {
		ResponseEntity<String> res = restTemplate.exchange("/api/ride/" + RIDE_TO_CANCEL_ID + "/withdraw", HttpMethod.PUT, makeJwtHeader(TOKEN_ADMIN), String.class);
		assertEquals(res.getStatusCode(), HttpStatus.FORBIDDEN);
	}
	
	@Test
	public void shouldThrowJSONExceptionWhenCancelingRideWithInvalidRideId() {
		ResponseEntity<String> res = restTemplate.exchange("/api/ride/" + INVALID_RIDE_ID + "/withdraw", HttpMethod.PUT, makeJwtHeader(TOKEN_PASSENGER), String.class);
		assertNotEquals(res.getStatusCode(), HttpStatus.OK);
	}
	
	@Test
	public void shouldThrowBadRequestExceptionWhenCancelingRideWithInvalidStatus() {
		ResponseEntity<ExceptionDTO> res = restTemplate.exchange("/api/ride/" + SCHEDULED_RIDE_ID + "/withdraw", HttpMethod.PUT, makeJwtHeader(TOKEN_PASSENGER), ExceptionDTO.class);
		assertEquals(res.getStatusCode(), HttpStatus.BAD_REQUEST);
		assertEquals(res.getBody().getMessage(), "Cannot cancel a ride that is not in status PENDING or STARTED!");
	}
	
	@Test
	public void shouldThrowNotFoundExceptionWhenCancelingRideWithNonExistingRide() {
		ResponseEntity<String> res = restTemplate.exchange("/api/ride/" + NON_EXISTANT_RIDE_ID + "/withdraw", HttpMethod.PUT, makeJwtHeader(TOKEN_PASSENGER), String.class);
		assertEquals(HttpStatus.NOT_FOUND, res.getStatusCode());
		assertEquals(res.getBody(), "Ride does not exist!");
	}
	
	@Test
	public void shouldCancelRide() {
		ResponseEntity<RideReturnedDTO> res = restTemplate.exchange("/api/ride/" + RIDE_TO_CANCEL_ID + "/withdraw", HttpMethod.PUT, makeJwtHeader(TOKEN_PASSENGER), RideReturnedDTO.class);
		
		RideReturnedDTO ride = res.getBody();
		
		assertEquals(HttpStatus.OK, res.getStatusCode());
		assertEquals(ride.getStatus(), RideStatus.CANCELED);
		assertEquals(ride.getId(), RIDE_TO_CANCEL_ID);
	}
	
	
	/*PRICE*/
	public void shouldReturnUnathorised_ForNoToken_GetRidePrice() {
        ResponseEntity<Double> res = restTemplate.exchange("/api/ride/price", HttpMethod.POST, null, Double.class);

        assertEquals(res.getStatusCode(), HttpStatus.UNAUTHORIZED);
    }
	
	@Test
	public void shouldThrowJSONExceptionWhenGettingRidePriceWithInvalidDto() {
		UnregisteredRideSuggestionDTO dto = new UnregisteredRideSuggestionDTO(null, DISTANCE);
		HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(TOKEN_PASSENGER);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<UnregisteredRideSuggestionDTO> entity = new HttpEntity<>(dto, headers);

        ResponseEntity<Double> res = restTemplate.exchange("/api/ride/price", HttpMethod.POST, entity, Double.class);

        assertNotEquals(res.getStatusCode(), HttpStatus.OK);
	}
	
	@Test
	public void shouldGetRideSugestionPrice() {
		UnregisteredRideSuggestionDTO dto = new UnregisteredRideSuggestionDTO("CAR", DISTANCE);
		HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(TOKEN_PASSENGER);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<UnregisteredRideSuggestionDTO> entity = new HttpEntity<>(dto, headers);

        ResponseEntity<Double> res = restTemplate.exchange("/api/ride/price", HttpMethod.POST, entity, Double.class);

        assertEquals(res.getStatusCode(), HttpStatus.OK);
        assertEquals(res.getBody(), DISTANCE*CAR_VEHICLE_PRICE);

    }
	
	
	
	
	
	
	
	
	
}
