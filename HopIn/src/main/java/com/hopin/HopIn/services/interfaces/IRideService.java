package com.hopin.HopIn.services.interfaces;

import java.util.List;

import com.hopin.HopIn.dtos.AllPanicRidesDTO;
import com.hopin.HopIn.dtos.AllPassengerRidesDTO;
import com.hopin.HopIn.dtos.FavoriteRideDTO;
import com.hopin.HopIn.dtos.FavoriteRideReturnedDTO;
import com.hopin.HopIn.dtos.PanicRideDTO;
import com.hopin.HopIn.dtos.ReasonDTO;
import com.hopin.HopIn.dtos.RideDTO;
import com.hopin.HopIn.dtos.RideForReportDTO;
import com.hopin.HopIn.dtos.RideReturnedDTO;
import com.hopin.HopIn.dtos.UnregisteredRideSuggestionDTO;
import com.hopin.HopIn.entities.Ride;

public interface IRideService {

	public RideReturnedDTO add(RideDTO dto);

	public RideReturnedDTO getActiveRideForDriver(int id);

	public RideReturnedDTO getActiveRideForPassenger(int id);

	public RideReturnedDTO getRide(int id);

	public RideReturnedDTO cancelRide(int id);

	public PanicRideDTO panicRide(int id, ReasonDTO reason);

	public RideReturnedDTO rejectRide(int id, ReasonDTO reason);

	public Double getRideSugestionPrice(UnregisteredRideSuggestionDTO dto);

	public FavoriteRideReturnedDTO insertFavoriteRide(FavoriteRideDTO dto);

	public List<FavoriteRideReturnedDTO> getFavoriteRides();

	public void deleteFavoriteRide(int id);

	public RideReturnedDTO startRide(int id);

	public RideReturnedDTO acceptRide(int id);

	public RideReturnedDTO finishRide(int id);

	public RideReturnedDTO getPendingRideForPassenger(int id);

	public RideReturnedDTO getPendingRideForDriver(int id);

	public List<RideForReportDTO> getAllRidesBetweenDates(String from, String to);

	public List<Ride> getAllAcceptedRides();

	public RideReturnedDTO startRideToDeparture(int id);

	public List<RideReturnedDTO> getScheduledRidesForUser(int userId);

}
