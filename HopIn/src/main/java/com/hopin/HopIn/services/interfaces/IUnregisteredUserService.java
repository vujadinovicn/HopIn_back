package com.hopin.HopIn.services.interfaces;

import com.hopin.HopIn.dtos.EstimatedRideDetailsDTO;
import com.hopin.HopIn.dtos.UnregisteredUserRideDTO;

public interface IUnregisteredUserService{
	
	public EstimatedRideDetailsDTO getEstimatedRideDetails(UnregisteredUserRideDTO ride);
	
}
