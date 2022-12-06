package com.HopIn.HopIn.services.interfaces;

import com.HopIn.HopIn.dtos.EstimatedRideDetailsDTO;
import com.HopIn.HopIn.dtos.UnregisteredUserRideDTO;

public interface IUnregisteredUserService{
	
	public EstimatedRideDetailsDTO getEstimatedRideDetails(UnregisteredUserRideDTO ride);
	
}
