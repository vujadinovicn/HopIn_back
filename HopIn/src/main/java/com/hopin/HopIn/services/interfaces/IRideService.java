package com.HopIn.HopIn.services.interfaces;

import com.HopIn.HopIn.dtos.RejectedRideDTO;

public interface IRideService {
	
//	public RideReturnedDTO create(RideDTO dto);
	
	public RejectedRideDTO getRide(int id);
}
