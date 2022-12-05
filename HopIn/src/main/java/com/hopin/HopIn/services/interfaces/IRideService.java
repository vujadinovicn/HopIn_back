package com.hopin.HopIn.services.interfaces;

import com.hopin.HopIn.dtos.RideDTO;
import com.hopin.HopIn.dtos.RideReturnedDTO;
import com.hopin.HopIn.dtos.RideReturnedWithRejectionDTO;

public interface IRideService {
	
	public RideReturnedDTO create(RideDTO dto);
	
	public RideReturnedDTO getActiveRideForDriver(int id);
	
	public RideReturnedWithRejectionDTO getRide(int id);
}
