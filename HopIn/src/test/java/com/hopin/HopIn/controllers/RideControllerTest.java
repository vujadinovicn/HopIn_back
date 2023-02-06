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
import org.springframework.web.util.UriComponentsBuilder;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hopin.HopIn.dtos.CredentialsDTO;
import com.hopin.HopIn.dtos.PanicRideDTO;
import com.hopin.HopIn.dtos.ReasonDTO;
import com.hopin.HopIn.dtos.RideReturnedDTO;
import com.hopin.HopIn.dtos.TokenDTO;
import com.hopin.HopIn.dtos.UnregisteredRideSuggestionDTO;
import com.hopin.HopIn.enums.RideStatus;
import com.hopin.HopIn.validations.ExceptionDTO;

import jakarta.transaction.Transactional;

//@DirtiesContext(classMode= DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@TestPropertySource(properties = "classpath:application-test.properties")
//@Sql("classpath:data-test-controller.sql")
public class RideControllerTest extends AbstractTestNGSpringContextTests {

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
    
	private static int NON_EXISTANT_RIDE_ID = 0;
	private static int INVALID_RIDE_ID = -1;
	
	private static int PASSENGER_ID = 1;
	private static int PASSENGER_NO_RIDES = 3;
	private static int PASSENGER_ONLY_STARTED_RIDE = 6;
    
	private final static int DRIVER_ID = 2;
    
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
	
	@Test
	public void shouldReturnUnathorised_ForNoToken_StartRideToDeparture() {
		ResponseEntity<String> res = restTemplate.exchange("/api/ride/driver-took-off/" + SCHEDULED_RIDE_ID, HttpMethod.POST, null, String.class);

		assertEquals(HttpStatus.UNAUTHORIZED, res.getStatusCode());
	}
	
	@Test
	public void shouldReturnForbidden_ForWrongRole_StartRideToDeparture() {
		ResponseEntity<String> res = restTemplate.exchange("/api/ride/driver-took-off/" + SCHEDULED_RIDE_ID, HttpMethod.POST, makeJwtHeader(TOKEN_ADMIN), String.class);

		assertEquals(HttpStatus.FORBIDDEN, res.getStatusCode());
	}
	
	@Test
	public void shouldReturnNotFound_ForNonExistantRide_StartRideToDeparture() {
		ResponseEntity<String> res = restTemplate.exchange("/api/ride/driver-took-off/" + NON_EXISTANT_RIDE_ID, HttpMethod.POST, makeJwtHeader(TOKEN_DRIVER), String.class);

		assertEquals(HttpStatus.NOT_FOUND, res.getStatusCode());
		assertEquals("Ride does not exist!", res.getBody());
	}
	
	@Test
	public void shouldReturBadRequest_ForWrongRideStatus_StartRideToDeparture() {
		ResponseEntity<ExceptionDTO> res = restTemplate.exchange("/api/ride/driver-took-off/" + STARTED_RIDE_ID, HttpMethod.POST, makeJwtHeader(TOKEN_DRIVER), ExceptionDTO.class);
		
		assertEquals(HttpStatus.BAD_REQUEST, res.getStatusCode());
		assertEquals("Cannot start ride to departure, status must be ACCEPTED!", res.getBody().getMessage());
	}
	
	@Test
	public void shouldReturBadRequest_ForRideWithNullScheduledTime_StartRideToDeparture() {
		ResponseEntity<ExceptionDTO> res = restTemplate.exchange("/api/ride/driver-took-off/" + ACCEPTED_RIDE_ID, HttpMethod.POST, makeJwtHeader(TOKEN_DRIVER), ExceptionDTO.class);
		
		assertEquals(HttpStatus.BAD_REQUEST, res.getStatusCode());
		assertEquals("Cannot start ride to departure, scheduled time must not be null!", res.getBody().getMessage());
	}
	
	@Test
	public void shouldReturBadRequest_ForInvalidRideId_StartRideToDeparture() {
		ResponseEntity<ExceptionDTO> res = restTemplate.exchange("/api/ride/driver-took-off/" + INVALID_RIDE_ID, HttpMethod.POST, makeJwtHeader(TOKEN_DRIVER), ExceptionDTO.class);
		
		assertEquals(HttpStatus.BAD_REQUEST, res.getStatusCode());
		assertEquals("Field id must be greater than 0.", res.getBody().getMessage());
	}
	
	@Test
	public void shouldStartRideToDeparture() {
		ResponseEntity<RideReturnedDTO> res = restTemplate.exchange("/api/ride/driver-took-off/" + SCHEDULED_RIDE_ID, HttpMethod.POST, makeJwtHeader(TOKEN_DRIVER), RideReturnedDTO.class);
		
		RideReturnedDTO ride = res.getBody();
		
		assertEquals(HttpStatus.OK, res.getStatusCode());
		assertEquals(ride.getId(), SCHEDULED_RIDE_ID);
		assertTrue(ride.getScheduledTime() == null);
	}
	
	@Test
	public void shouldReturnUnathorised_ForNoToken_GetActiveRideForPassenger() {
		ResponseEntity<String> res = restTemplate.exchange("/api/ride/passenger/" + PASSENGER_ID + "/active", HttpMethod.GET, null, String.class);

		assertEquals(HttpStatus.UNAUTHORIZED, res.getStatusCode());
	}
	
	@Test
	public void shouldReturnForbidden_ForWrongRole_GetActiveRideForPassenger() {
		ResponseEntity<String> res = restTemplate.exchange("/api/ride/passenger/" + PASSENGER_ID + "/active", HttpMethod.GET, makeJwtHeader(TOKEN_DRIVER), String.class);

		assertEquals(HttpStatus.FORBIDDEN, res.getStatusCode());
	}
	
	@Test
	public void shouldReturnBadRequest_ForNotExistantPassenger_GetActiveRideForPassenger() {
		ResponseEntity<String> res = restTemplate.exchange("/api/ride/passenger/" + NON_EXISTANT_RIDE_ID + "/active", HttpMethod.GET, makeJwtHeader(TOKEN_ADMIN), String.class);

		assertEquals(HttpStatus.BAD_REQUEST, res.getStatusCode());
		assertEquals("Passenger doesn't exist!", res.getBody());
	}
	
	@Test
	public void shouldReturnNotFound_ForPassengerWithNoRides_GetActiveRideForPassenger() {
		ResponseEntity<String> res = restTemplate.exchange("/api/ride/passenger/" + PASSENGER_NO_RIDES + "/active", HttpMethod.GET, makeJwtHeader(TOKEN_ADMIN), String.class);

		assertEquals(HttpStatus.NOT_FOUND, res.getStatusCode());
		assertEquals("Active ride does not exist", res.getBody());
	}
	
	@Test
	public void shouldReturnNotFound_ForPassengerWithActiveRides_GetActiveRideForPassenger() {
		ResponseEntity<String> res = restTemplate.exchange("/api/ride/passenger/" + PASSENGER_ONLY_STARTED_RIDE + "/active", HttpMethod.GET, makeJwtHeader(TOKEN_ADMIN), String.class);

		assertEquals(HttpStatus.NOT_FOUND, res.getStatusCode());
		assertEquals("Active ride does not exist", res.getBody());
	}
	
	@Test
	public void shouldReturnBadRequest_ForInvalidPassengerId_GetActiveRideForPassenger() {
		ResponseEntity<ExceptionDTO> res = restTemplate.exchange("/api/ride/passenger/" + INVALID_RIDE_ID + "/active", HttpMethod.GET, makeJwtHeader(TOKEN_ADMIN), ExceptionDTO.class);
		
		assertEquals(HttpStatus.BAD_REQUEST, res.getStatusCode());
		assertEquals("Field id must be greater than 0.", res.getBody().getMessage());
	}
	
	//TODO: dodati sta je izbrisao merge
	@Test
	public void shouldGetActiveRideForPassenger() {
		ResponseEntity<RideReturnedDTO> res = restTemplate.exchange("/api/ride/passenger/" + PASSENGER_ID + "/active", HttpMethod.GET, makeJwtHeader(TOKEN_ADMIN), RideReturnedDTO.class);
		
		RideReturnedDTO ride = res.getBody();
		
		assertTrue(ride.getStartTime() == null);
		assertEquals(ride.getId(), PENDING_RIDE_ID);
		assertTrue(ride.getStatus() == RideStatus.PENDING);
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
	public void shouldThrowUnauthorizedException_ForNoToken_RejectRide() {
		ResponseEntity<RideReturnedDTO> res = restTemplate.exchange("/api/ride/" + PENDING_RIDE_ID + "/cancel", HttpMethod.PUT, null, RideReturnedDTO.class);
		assertEquals(res.getStatusCode(), HttpStatus.UNAUTHORIZED);
	}
	
	@Test
	public void shouldThrowForbiddenException_ForAdminRole_RejectRide() {
		ResponseEntity<String> res = restTemplate.exchange("/api/ride/" + PENDING_RIDE_ID + "/cancel", HttpMethod.PUT, makeJwtHeader(TOKEN_ADMIN), String.class);
	}
	 /*GET_RIDE*/
	@Test
	public void shouldReturnUnathorised_ForNoToken_GetRide() {
        ResponseEntity<String> res = restTemplate.withBasicAuth("driver@gmail.com", "123")
                  .exchange("/api/ride/" + ACCEPTED_RIDE_ID, HttpMethod.GET, null, String.class);

        assertEquals(res.getStatusCode(), HttpStatus.UNAUTHORIZED);
    }
	
	@Test
	public void shouldThrowForbiddenExceptionWhenGettingRideAsPassenger() {
		ResponseEntity<String> res = restTemplate.exchange("/api/ride/" + ACCEPTED_RIDE_ID, HttpMethod.GET, makeJwtHeader(TOKEN_PASSENGER), String.class);
		assertEquals(res.getStatusCode(), HttpStatus.FORBIDDEN);
	}
	
	@Test
	public void shouldThrowMethodArgumentTypeMismatchException_ForInvalidPathParam_RejectRide() {
		ResponseEntity<String> res = restTemplate.exchange("/api/ride/" + "s" + "/cancel", HttpMethod.PUT, makeJwtHeader(TOKEN_DRIVER), String.class);
	}
	
	public void shouldThrowJSONExceptionWhenGettingRideWithInvalidRideId() {
		ResponseEntity<String> res = restTemplate.exchange("/api/ride/" + INVALID_RIDE_ID, HttpMethod.GET, makeJwtHeader(TOKEN_DRIVER), String.class);
		assertEquals(res.getStatusCode(), HttpStatus.BAD_REQUEST);
	}
	
	@Test
	public void shouldThrowJSONException_ForInvalidRideId_RejectRide() {
		ResponseEntity<String> res = restTemplate.exchange("/api/ride/" + INVALID_RIDE_ID + "/cancel", HttpMethod.PUT, makeJwtHeader(TOKEN_DRIVER), String.class);
		assertNotEquals(res.getStatusCode(), HttpStatus.OK);
	}
	
	@Test
	public void shouldThrowJSONException_ForInvalidReason_RejectRide() {
		ResponseEntity<String> res = restTemplate.exchange("/api/ride/" + PENDING_RIDE_ID + "/cancel", HttpMethod.PUT, makeJwtHeaderWithRequestBody(convertReasonDTOToJson("reason"), TOKEN_DRIVER), String.class);
		assertNotEquals(res.getStatusCode(), HttpStatus.OK);
	}
	
	@Test
	public void shouldThrowJSONException_ForNullReason_RejectRide() {
		ResponseEntity<String> res = restTemplate.exchange("/api/ride/" + PENDING_RIDE_ID + "/cancel", HttpMethod.PUT, makeJwtHeader(TOKEN_DRIVER), String.class);
		assertEquals(res.getStatusCode(), HttpStatus.BAD_REQUEST);
	}
	
	@Test
	public void shouldThrowBadRequestException_ForInvalidRideStatus_RejectRide() {
		ResponseEntity<ExceptionDTO> res = restTemplate.exchange("/api/ride/" + STARTED_RIDE_ID + "/cancel", HttpMethod.PUT, makeJwtHeaderWithRequestBody(convertReasonDTOToJson("reason"), TOKEN_DRIVER), ExceptionDTO.class);
		assertEquals(HttpStatus.BAD_REQUEST, res.getStatusCode());
		assertEquals(res.getBody().getMessage(), "Cannot cancel a ride that is not in status PENDING or ACCEPTED!");
	}
	
	@Test
	public void shouldThrowNotFoundException_ForNonExistingRide_RejectRide() {
		ResponseEntity<String> res = restTemplate.withBasicAuth("driver@gmail.com", "123")
				  .exchange("/api/ride/" + NON_EXISTANT_RIDE_ID + "/cancel", HttpMethod.PUT, makeJwtHeaderWithRequestBody(convertReasonDTOToJson("reason"), TOKEN_DRIVER), String.class);
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
	}
	
	@Test
	public void shouldReturnUnathorised_ForNoToken_GetRidesBetweenDates() {
		ResponseEntity<String> res = restTemplate.exchange("/api/ride/date/range", HttpMethod.GET, null, String.class);

		assertEquals(HttpStatus.UNAUTHORIZED, res.getStatusCode());
	}
	
	@Test
	public void shouldReturnForbidden_ForWrongRole_GetRidesBetweenDates() {
		String from = "2022/12/25";
		String to = "2021/02/07";
		
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString("/api/ride/date/range")
                .queryParam("from", from)
                .queryParam("to", to);
		
		ResponseEntity<String> res = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, makeJwtHeader(TOKEN_DRIVER), String.class);

		assertEquals(HttpStatus.FORBIDDEN, res.getStatusCode());
	}
	
	@Test
	public void shouldReturnBadRequest_ForBadDateFormat_GetRidesBetweenDates() {
		String from = "2.12.2022.";
		String to = "3.12.2022.";
		
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString("/api/ride/date/range")
                .queryParam("from", from)
                .queryParam("to", to);
		
		ResponseEntity<String> res = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, makeJwtHeader(TOKEN_ADMIN), String.class);

		assertEquals(HttpStatus.BAD_REQUEST, res.getStatusCode());
		assertEquals("Wrong date format! Use yyyy/MM/dd.", res.getBody());
	}
	
	@Test
	public void shouldReturnBadRequest_ForBadDateRange_GetRidesBetweenDates() {
		String from = "2022/12/25";
		String to = "2021/02/07";
		
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString("/api/ride/date/range")
                .queryParam("from", from)
                .queryParam("to", to);
		
		ResponseEntity<String> res = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, makeJwtHeader(TOKEN_ADMIN), String.class);

		assertEquals(HttpStatus.BAD_REQUEST, res.getStatusCode());
		assertEquals("End of range date must be after start of range date!", res.getBody());
	}
	
	@Test
	public void shouldReturnBadRequest_ForMissingDateParams_GetRidesBetweenDates() {
		ResponseEntity<String> res = restTemplate.exchange("/api/ride/date/range", HttpMethod.GET, makeJwtHeader(TOKEN_ADMIN), String.class);

		assertEquals(HttpStatus.BAD_REQUEST, res.getStatusCode());
		assertEquals("Request parameter from is missing!", res.getBody());
	}
	
	@Test
	public void shouldReturnBadRequest_ForMissingDateParams() {
		ResponseEntity<String> res = restTemplate.exchange("/api/ride/date/range", HttpMethod.GET, makeJwtHeader(TOKEN_ADMIN), String.class);

		assertEquals(HttpStatus.BAD_REQUEST, res.getStatusCode());
		assertEquals("Request parameter from is missing!", res.getBody());
	}
	
	//TODO: DODAJ OVO KAD VIDIS OSTALE
	@Test 
	public void shouldGetRidesBetweenDates() {
		String from = "2022/12/25";
		String to = "2023/02/07";
		
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString("/api/ride/date/range")
                .queryParam("from", from)
                .queryParam("to", to);
		
		ResponseEntity<String> res = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, makeJwtHeader(TOKEN_ADMIN), String.class);

		assertEquals(HttpStatus.BAD_REQUEST, res.getStatusCode());
		assertEquals("End of range date must be after start of range date!", res.getBody());
	}
	
	@Test
	public void shouldReturnUnathorised_ForNoToken_AddFavoriteRide() {
		ResponseEntity<String> res = restTemplate.exchange("/api/ride/favorites", HttpMethod.POST, null, String.class);

		assertEquals(HttpStatus.UNAUTHORIZED, res.getStatusCode());
	}
	
	@Test
	public void shouldReturnForbidden_ForWrongRole_AddFavoriteRide() {
		ResponseEntity<String> res = restTemplate.exchange("/api/ride/favorites", HttpMethod.POST, makeJwtHeader(TOKEN_DRIVER), String.class);

		assertEquals(HttpStatus.FORBIDDEN, res.getStatusCode());
	}
	
	// TODO: KAKO OVO DA UBACIM U BAZU???
	@Test 
	public void shouldReturnBadRequest_ForMoreThan10Favs_AddFavoriteRide() {
		ResponseEntity<ExceptionDTO> res = restTemplate.exchange("/api/ride/favorites", HttpMethod.POST, makeJwtHeader(TOKEN_PASSENGER),ExceptionDTO.class);

		assertEquals(HttpStatus.BAD_REQUEST, res.getStatusCode());
		assertEquals("Number of favorite rides cannot exceed 10!", res.getBody().getMessage());
	}
	
	// TODO: JE L IMA OVO PRAVLJENJE DTO-A NEKO??
	@Test 
	public void shouldReturnBadRequest_WhenCurrentPassengerNotInRide_AddFavoriteRide() {
		ResponseEntity<ExceptionDTO> res = restTemplate.exchange("/api/ride/favorites", HttpMethod.POST, makeJwtHeader(TOKEN_PASSENGER),ExceptionDTO.class);

		assertEquals(HttpStatus.BAD_REQUEST, res.getStatusCode());
		assertEquals("Number of favorite rides cannot exceed 10!", res.getBody().getMessage());
	}
	
	@Test
	public void shouldThrowUnauthorizedException_ForNoToken_PanicRide() {
		ResponseEntity<?> res = restTemplate.exchange("/api/ride/" + STARTED_RIDE_ID + "/panic", HttpMethod.PUT, null, RideReturnedDTO.class);
		assertEquals(res.getStatusCode(), HttpStatus.UNAUTHORIZED);
	}
	
	@Test
	public void shouldThrowForbiddenException_ForAdminRole_PanicRide() {
		ResponseEntity<?> res = restTemplate.exchange("/api/ride/" + PENDING_RIDE_ID + "/panic", HttpMethod.PUT, makeJwtHeader(TOKEN_ADMIN), String.class);
	}
	
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
		
		assertEquals(res.getStatusCode(), HttpStatus.OK);
		assertEquals(ride.getId(), ACCEPTED_RIDE_ID);
	}
	
	
	/*DELETE_FAVORITES*/
	@Test
	public void shouldReturnUnathorised_ForNoToken_DeleteFavoriteRide() {
        ResponseEntity<String> res = restTemplate.exchange("/api/ride/favorites/" + FAVORITE_RIDE_ID, HttpMethod.DELETE, null, String.class);

        assertEquals(res.getStatusCode(), HttpStatus.UNAUTHORIZED);
    }
	
	@Test
	public void shouldThrowForbiddenExceptionWhenDeletingFavoriteRideAsAdmin() {
		ResponseEntity<String> res = restTemplate.exchange("/api/ride/favorites/" + FAVORITE_RIDE_ID, HttpMethod.DELETE, makeJwtHeader(TOKEN_ADMIN), String.class);
		assertEquals(res.getStatusCode(), HttpStatus.FORBIDDEN);
	}
	
	@Test
	public void shouldThrowJSONException_ForInvalidRideId_PanicRide() {
		ResponseEntity<String> res = restTemplate.exchange("/api/ride/" + INVALID_RIDE_ID + "/panic", HttpMethod.PUT, makeJwtHeader(TOKEN_DRIVER), String.class);
		assertNotEquals(res.getStatusCode(), HttpStatus.OK);
	}
	
	@Test
	public void shouldThrowMethodArgumentTypeMismatchException_ForInvalidPathParam_PanicRide() {
		ResponseEntity<String> res = restTemplate.exchange("/api/ride/" + "S" + "/panic", HttpMethod.PUT, makeJwtHeaderWithRequestBody(convertReasonDTOToJson("reason"), TOKEN_DRIVER), String.class);
	}
	
	public void shouldThrowJSONExceptionWhenDeletingFavoriteRideWithInvalidFavoriteRideId() {
		ResponseEntity<String> res = restTemplate.exchange("/api/ride/favorites/" + INVALID_RIDE_ID, HttpMethod.DELETE, makeJwtHeader(TOKEN_PASSENGER), String.class);
		assertEquals(res.getStatusCode(), HttpStatus.BAD_REQUEST);
	}
	
	@Test
	public void shouldThrowJSONException_ForNullReason_PanicRide() {
		ResponseEntity<String> res = restTemplate.exchange("/api/ride/" + PENDING_RIDE_ID + "/panic", HttpMethod.PUT, makeJwtHeader(TOKEN_DRIVER), String.class);
	}
	
	public void shouldThrowFavoriteRideNotFoundExceptionWhenDeletingFavoriteRideForNonExistingFavoriteRide() {
		ResponseEntity<String> res = restTemplate.exchange("/api/ride/favorites/" + NON_EXISTANT_RIDE_ID, HttpMethod.DELETE, makeJwtHeader(TOKEN_PASSENGER), String.class);
		assertEquals(res.getStatusCode(), HttpStatus.NOT_FOUND);
		assertEquals(res.getBody(), "Favorite location does not exist!");
	}
	
	@Transactional
	@Test
	public void shouldDeleteFavoriteRide() {
		ResponseEntity<String> res = restTemplate.exchange("/api/ride/favorites/" + FAVORITE_RIDE_ID, HttpMethod.DELETE, makeJwtHeader(TOKEN_PASSENGER), String.class);
		assertEquals(res.getStatusCode(), HttpStatus.NO_CONTENT);
	}
	
	
	/*ACCEPT_RIDE*/
	@Test
	public void shouldReturnUnathorised_ForNoToken_AcceptRide() {
        ResponseEntity<String> res = restTemplate.exchange("/api/ride/" + PENDING_RIDE_ID + "/accept", HttpMethod.PUT, null, String.class);

        assertEquals(res.getStatusCode(), HttpStatus.UNAUTHORIZED);
    }
	
	@Test
	public void shouldThrowForbiddenExceptionWhenAcceptingRideRideAsAdmin() {
		ResponseEntity<String> res = restTemplate.exchange("/api/ride/" + PENDING_RIDE_ID + "/accept", HttpMethod.PUT, makeJwtHeader(TOKEN_ADMIN), String.class);
		assertEquals(res.getStatusCode(), HttpStatus.FORBIDDEN);
	}
	
	@Test
	public void shouldThrowJSONExceptionWhenAcceptingRideWithInvalidRideId() {
		ResponseEntity<String> res = restTemplate.exchange("/api/ride/" + INVALID_RIDE_ID + "/accept", HttpMethod.PUT, makeJwtHeader(TOKEN_DRIVER), String.class);
		assertEquals(res.getStatusCode(), HttpStatus.BAD_REQUEST);
	}
	
	@Test
	public void shouldThrowJSONException_ForInvalidReason_PanicRide() {
		ResponseEntity<String> res = restTemplate.exchange("/api/ride/" + PENDING_RIDE_ID + "/panic", HttpMethod.PUT, makeJwtHeaderWithRequestBody(convertReasonDTOToJson(""), TOKEN_DRIVER), String.class);
		assertNotEquals(res.getStatusCode(), HttpStatus.OK);
	}
	
	@Test
	public void shouldThrowNotFoundException_ForNonExistingRide_PanicRide() {
		ResponseEntity<String> res = restTemplate.exchange("/api/ride/" + NON_EXISTANT_RIDE_ID + "/panic", HttpMethod.PUT, makeJwtHeaderWithRequestBody(convertReasonDTOToJson("reason"), TOKEN_DRIVER), String.class);
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
	public void shouldThrowUnauthorizedException_ForNoToken_GetingActiveRideForDriver() {
		ResponseEntity<?> res = restTemplate.exchange("/api/ride/driver/" + DRIVER_ID + "/active", HttpMethod.GET, null, RideReturnedDTO.class);
		assertEquals(res.getStatusCode(), HttpStatus.UNAUTHORIZED);
	}
	
	@Test
	public void shouldThrowForbiddenException_ForPassengerRole_GetActiveRideForDriver() {
		ResponseEntity<?> res = restTemplate.exchange("/api/ride/driver/" + DRIVER_ID + "/active", HttpMethod.GET, makeJwtHeader(TOKEN_PASSENGER), String.class);
		assertEquals(res.getStatusCode(), HttpStatus.FORBIDDEN);
	}
	
	@Test
	public void shouldThrowJSONException_ForInvalidDriverId_GetActiveRideForDriver() {
		ResponseEntity<String> res = restTemplate.exchange("/api/ride/driver/" + INVALID_RIDE_ID + "/active", HttpMethod.GET, makeJwtHeader(TOKEN_DRIVER), String.class);
		assertNotEquals(res.getStatusCode(), HttpStatus.OK);
	}
	
	@Test
	public void shouldThrowMethodArgumentTypeMismatchException_ForInvalidPathParam_GetActiveRideForDriver() {
		ResponseEntity<String> res = restTemplate.exchange("/api/ride/driver/" + "S" + "/active", HttpMethod.GET, makeJwtHeader(TOKEN_DRIVER), String.class);
		assertEquals(res.getStatusCode(), HttpStatus.BAD_REQUEST);
	}
	
	@Test
	public void shouldThrowNoActiveDriverRideException_ForDriverWithNoRide_GetActiveRideForDriver() {
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
	public void shouldThrowUnauthorizedException_ForNoToken_GetPendingRideForDriver() {
		ResponseEntity<?> res = restTemplate.exchange("/api/ride/driver/" + DRIVER_ID + "/pending", HttpMethod.GET, null, RideReturnedDTO.class);
		assertEquals(res.getStatusCode(), HttpStatus.UNAUTHORIZED);
	}
	
	@Test
	public void shouldThrowForbiddenException_ForPassengerRole_GetPendingRideForDriver() {
		ResponseEntity<?> res = restTemplate.exchange("/api/ride/driver/" + DRIVER_ID + "/pending", HttpMethod.GET, makeJwtHeader(TOKEN_PASSENGER), String.class);
	}
	
	public void shouldThrowBadRequestExceptionWhenAcceptingRideWithInvalidStatus() {
		ResponseEntity<ExceptionDTO> res = restTemplate.exchange("/api/ride/" + ACCEPTED_RIDE_ID + "/accept", HttpMethod.PUT, makeJwtHeader(TOKEN_DRIVER), ExceptionDTO.class);
		assertEquals(res.getStatusCode(), HttpStatus.BAD_REQUEST);
		assertEquals(res.getBody().getMessage(), "Cannot accept a ride that is not in status PENDING!");
	}
	
	@Test
	public void shouldThrowNotFoundExceptionWhenAcceptingRideWithNonExistingRide() {
		ResponseEntity<String> res = restTemplate.exchange("/api/ride/" + NON_EXISTANT_RIDE_ID + "/accept", HttpMethod.PUT, makeJwtHeader(TOKEN_DRIVER), String.class);
		assertEquals(res.getStatusCode(), HttpStatus.NOT_FOUND);
		assertEquals(res.getBody(), "Ride does not exist!");
	}
	
	@Test(priority = 10)
	public void shouldAcceptRide() {
		ResponseEntity<RideReturnedDTO> res = restTemplate.exchange("/api/ride/" + PENDING_RIDE_ID + "/accept", HttpMethod.PUT, makeJwtHeader(TOKEN_DRIVER), RideReturnedDTO.class);
		
		RideReturnedDTO ride = res.getBody();
		
		assertEquals(res.getStatusCode(), HttpStatus.OK);
		assertEquals(ride.getStatus(), RideStatus.ACCEPTED);
		assertEquals(ride.getId(), PENDING_RIDE_ID);
	}
	
	
	/*CANCEL_RIDE*/
	@Test
	public void shouldReturnUnathorised_ForNoToken_CancelRide() {
        ResponseEntity<String> res = restTemplate.exchange("/api/ride/" + RIDE_TO_CANCEL_ID + "/withdraw", HttpMethod.PUT, null, String.class);

        assertEquals(res.getStatusCode(), HttpStatus.UNAUTHORIZED);
    }
	
	@Test
	public void shouldThrowForbiddenExceptionWhenCancelingRideAsAdmin() {
		ResponseEntity<String> res = restTemplate.exchange("/api/ride/" + RIDE_TO_CANCEL_ID + "/withdraw", HttpMethod.PUT, makeJwtHeader(TOKEN_ADMIN), String.class);
		assertEquals(res.getStatusCode(), HttpStatus.FORBIDDEN);
	}
	
	@Test
	public void shouldThrowJSONException_ForInvalidDriverId_GetPendingRideForDriver() {
		ResponseEntity<String> res = restTemplate.exchange("/api/ride/driver/" + INVALID_RIDE_ID + "/pending", HttpMethod.GET, makeJwtHeader(TOKEN_DRIVER), String.class);
		assertNotEquals(res.getStatusCode(), HttpStatus.OK);
	}
	
	@Test
	public void shouldThrowMethodArgumentTypeMismatchException_ForInvalidPathParam_GetPendingRideForDriver() {
		ResponseEntity<String> res = restTemplate.exchange("/api/ride/driver/" + "S" + "/pending", HttpMethod.GET, makeJwtHeader(TOKEN_DRIVER), String.class);
	}
	
	public void shouldThrowJSONExceptionWhenCancelingRideWithInvalidRideId() {
		ResponseEntity<String> res = restTemplate.exchange("/api/ride/" + INVALID_RIDE_ID + "/withdraw", HttpMethod.PUT, makeJwtHeader(TOKEN_PASSENGER), String.class);
		assertEquals(res.getStatusCode(), HttpStatus.BAD_REQUEST);
	}
	
	@Test
	public void shouldThrowNoActiveDriverRideException_ForDriverWithNoRide_GetPendingRideForDriver() {
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
	public void shouldThrowUnauthorizedException_ForNoToken_GetScheduledRidesForUser() {
		ResponseEntity<?> res = restTemplate.exchange("/api/ride/scheduled-rides/" + INVALID_RIDE_ID, HttpMethod.GET, null, RideReturnedDTO.class);
		assertEquals(res.getStatusCode(), HttpStatus.UNAUTHORIZED);
	}
	
	@Test
	public void shouldThrowForbiddenException_ForAdminRole_GetScheduledRidesForUser() {
		ResponseEntity<?> res = restTemplate.exchange("/api/ride/scheduled-rides/" + INVALID_RIDE_ID, HttpMethod.GET, makeJwtHeader(TOKEN_ADMIN), String.class);
		assertEquals(res.getStatusCode(), HttpStatus.FORBIDDEN);
	}
	
	public void shouldThrowBadRequestExceptionWhenCancelingRideWithInvalidStatus() {
		ResponseEntity<ExceptionDTO> res = restTemplate.exchange("/api/ride/" + SCHEDULED_RIDE_ID + "/withdraw", HttpMethod.PUT, makeJwtHeader(TOKEN_PASSENGER), ExceptionDTO.class);
		assertEquals(res.getStatusCode(), HttpStatus.BAD_REQUEST);
		assertEquals(res.getBody().getMessage(), "Cannot cancel a ride that is not in status PENDING or STARTED!");
	}
	
	@Test
	public void shouldThrowNotFoundExceptionWhenCancelingRideWithNonExistingRide() {
		ResponseEntity<String> res = restTemplate.exchange("/api/ride/" + NON_EXISTANT_RIDE_ID + "/withdraw", HttpMethod.PUT, makeJwtHeader(TOKEN_PASSENGER), String.class);
		assertEquals(res.getStatusCode(), HttpStatus.NOT_FOUND);
		assertEquals(res.getBody(), "Ride does not exist!");
	}
	
	@Test
	public void shouldCancelRide() {
		ResponseEntity<RideReturnedDTO> res = restTemplate.exchange("/api/ride/" + RIDE_TO_CANCEL_ID + "/withdraw", HttpMethod.PUT, makeJwtHeader(TOKEN_PASSENGER), RideReturnedDTO.class);
		
		RideReturnedDTO ride = res.getBody();
		
		assertEquals(res.getStatusCode(), HttpStatus.OK);
		assertEquals(ride.getStatus(), RideStatus.CANCELED);
		assertEquals(ride.getId(), RIDE_TO_CANCEL_ID);
	}
	
	
	/*PRICE*/
	@Test
	public void shouldReturnUnathorised_ForNoToken_GetRidePrice() {
        ResponseEntity<Double> res = restTemplate.exchange("/api/ride/price", HttpMethod.POST, null, Double.class);

        assertEquals(res.getStatusCode(), HttpStatus.UNAUTHORIZED);
    }
	
	@Test
	public void shouldThrowJSONExceptionWhenGettingRidePriceWithInvalidDto() {
		UnregisteredRideSuggestionDTO dto = new UnregisteredRideSuggestionDTO("", DISTANCE);
		HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(TOKEN_PASSENGER);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<UnregisteredRideSuggestionDTO> entity = new HttpEntity<>(dto, headers);

        ResponseEntity<String> res = restTemplate.exchange("/api/ride/price", HttpMethod.POST, entity, String.class);

		assertEquals(res.getStatusCode(), HttpStatus.BAD_REQUEST);
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
	
	
	/*GET_FAVORITE_RIDES*/
	@Test
	public void shouldReturnUnathorised_ForNoToken_GetFavoriteRides() {
        ResponseEntity<String> res = restTemplate.exchange("/api/ride/favorites", HttpMethod.GET, null, String.class);

        assertEquals(res.getStatusCode(), HttpStatus.UNAUTHORIZED);
    }
	
	@Test
	public void shouldThrowForbiddenExceptionWhenGettingFavoriteRideAsDriver() {
		ResponseEntity<String> res = restTemplate.exchange("/api/ride/favorites", HttpMethod.GET, makeJwtHeader(TOKEN_DRIVER), String.class);
		assertEquals(res.getStatusCode(), HttpStatus.FORBIDDEN);
	}
	
	@Test
	public void shouldThrowJSONException_ForInvalidUserId_GetScheduledRidesForUser() {
		ResponseEntity<String> res = restTemplate.exchange("/api/ride/scheduled-rides/" + INVALID_RIDE_ID, HttpMethod.GET, makeJwtHeader(TOKEN_DRIVER), String.class);
		assertNotEquals(res.getStatusCode(), HttpStatus.OK);
	}
	
	@Test
	public void shouldThrowMethodArgumentTypeMismatchException_ForInvalidPathParam_GetScheduledRidesForUser() {
		ResponseEntity<String> res = restTemplate.exchange("/api/ride/scheduled-rides/" + "S", HttpMethod.GET, makeJwtHeader(TOKEN_DRIVER), String.class);
		assertEquals(res.getStatusCode(), HttpStatus.BAD_REQUEST);
	}
	
	@Test
	public void shouldThrowUserNotFoundException_ForNotExistingUser_GetScheduledRidesForUser() {
		ResponseEntity<String> res = restTemplate.exchange("/api/ride/scheduled-rides/" + NON_EXISTANT_RIDE_ID, HttpMethod.GET, makeJwtHeader(TOKEN_DRIVER), String.class);
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
	
	@Test
	public void shouldGetFavoriteRides() {
		ResponseEntity<ArrayList<RideReturnedDTO>> res = restTemplate.exchange("/api/ride/favorites", HttpMethod.GET, makeJwtHeader(TOKEN_PASSENGER), new ParameterizedTypeReference<ArrayList<RideReturnedDTO>>() {});
		assertEquals(res.getStatusCode(), HttpStatus.OK);
		assertTrue(res.getBody().size() <= 1);
	}
}
