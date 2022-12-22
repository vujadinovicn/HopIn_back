package com.hopin.HopIn.services.interfaces;

import com.hopin.HopIn.dtos.AllPanicRidesDTO;
import com.hopin.HopIn.dtos.AllPassengerRidesDTO;
import com.hopin.HopIn.dtos.PanicRideDTO;
import com.hopin.HopIn.dtos.ReasonDTO;
import com.hopin.HopIn.dtos.RideDTO;
import com.hopin.HopIn.dtos.RideReturnedDTO;
import com.hopin.HopIn.dtos.RideReturnedWithRejectionDTO;
import com.hopin.HopIn.dtos.UnregisteredRideSuggestionDTO;
import com.hopin.HopIn.enums.RideStatus;
public interface IRideService {
	
	public RideReturnedDTO create(RideDTO dto);
	
	public RideReturnedDTO getActiveRideForDriver(int id);
	
	public RideReturnedDTO getActiveRideForPassenger(int id);
	
	public RideReturnedWithRejectionDTO getRide(int id);
	
	public RideReturnedDTO cancelRide(int id);
	
	public PanicRideDTO panicRide(int id, ReasonDTO reason);
	
	public RideReturnedDTO changeRideStatus(int id, RideStatus status);
	
	public RideReturnedDTO rejectRide(int id, ReasonDTO reason);
	
	public AllPanicRidesDTO getAllPanicRides();
		
	public AllPassengerRidesDTO getAllPassengerRides(int id, int page, int size, String sort, String from, String to);

	public Double getRideSugestionPrice(UnregisteredRideSuggestionDTO dto);

}
