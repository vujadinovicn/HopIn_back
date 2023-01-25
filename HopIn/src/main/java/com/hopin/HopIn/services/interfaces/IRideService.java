package com.hopin.HopIn.services.interfaces;

import java.util.List;

import com.hopin.HopIn.dtos.AllPanicRidesDTO;
import com.hopin.HopIn.dtos.AllPassengerRidesDTO;

import com.hopin.HopIn.dtos.FavoriteRideDTO;
import com.hopin.HopIn.dtos.FavoriteRideReturnedDTO;

import com.hopin.HopIn.dtos.AllUserRidesReturnedDTO;

import com.hopin.HopIn.dtos.PanicRideDTO;
import com.hopin.HopIn.dtos.ReasonDTO;
import com.hopin.HopIn.dtos.RideDTO;
import com.hopin.HopIn.dtos.RideForReportDTO;
import com.hopin.HopIn.dtos.RideReturnedDTO;
import com.hopin.HopIn.dtos.UnregisteredRideSuggestionDTO;
import com.hopin.HopIn.entities.Driver;
import com.hopin.HopIn.enums.RideStatus;

import jakarta.validation.constraints.Min;

public interface IRideService {

	public RideReturnedDTO add(RideDTO dto);

	public RideReturnedDTO getActiveRideForDriver(int id);

	public RideReturnedDTO getActiveRideForPassenger(int id);

	public RideReturnedDTO getRide(int id);

	public RideReturnedDTO cancelRide(int id);

	public PanicRideDTO panicRide(int id, ReasonDTO reason);

	public RideReturnedDTO changeRideStatus(int id, RideStatus status);

	public RideReturnedDTO rejectRide(int id, ReasonDTO reason);

	public AllPanicRidesDTO getAllPanicRides();

	public AllPassengerRidesDTO getAllPassengerRides(int id, int page, int size, String sort, String from, String to);

	public List<RideForReportDTO> getAllPassengerRidesBetweenDates(int id, String from, String to);

	public List<RideForReportDTO> getAllDriverRidesBetweenDates(int id, String from, String to);

	public Double getRideSugestionPrice(UnregisteredRideSuggestionDTO dto);

	public FavoriteRideReturnedDTO insertFavoriteRide(FavoriteRideDTO dto);

	public List<FavoriteRideReturnedDTO> getFavoriteRides();

	public void deleteFavoriteRide(int id);

	public RideReturnedDTO startRide(int id);

	public RideReturnedDTO acceptRide(int id);

	public RideReturnedDTO finishRide(int id);

	public AllPassengerRidesDTO getAllDriverRides(int driverId, int page, int size, String sort, String from,
			String to);

	public RideReturnedDTO getPendingRideForPassenger(int id);

	RideReturnedDTO getPendingRideForDriver(int id);

	public RideReturnedDTO getAcceptedOrStartedRideForDriver(int driverId);

}
