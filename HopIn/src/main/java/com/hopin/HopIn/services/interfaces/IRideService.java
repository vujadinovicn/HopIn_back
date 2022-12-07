package com.hopin.HopIn.services.interfaces;

import com.hopin.HopIn.dtos.RejectedRideDTO;

public interface IRideService {
	
//	public RideReturnedDTO create(RideDTO dto);
	
	public RejectedRideDTO getRide(int id);
}
