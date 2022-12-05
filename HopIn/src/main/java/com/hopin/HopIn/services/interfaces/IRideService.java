package com.hopin.HopIn.services.interfaces;

import com.hopin.HopIn.dtos.RideDTO;
import com.hopin.HopIn.dtos.RideReturnedDTO;

public interface IRideService {
	
	public RideReturnedDTO create(RideDTO dto);
	
//	public RejectedRideDTO getRide(int id);
}
