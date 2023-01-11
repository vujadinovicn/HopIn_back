package com.hopin.HopIn.services.interfaces;

import java.util.List;

import com.hopin.HopIn.dtos.LocationNoIdDTO;
import com.hopin.HopIn.dtos.RideDTO;
import com.hopin.HopIn.dtos.RideReturnedDTO;
import com.hopin.HopIn.entities.Driver;
import com.hopin.HopIn.entities.Ride;

public interface IRideEstimationService {
	
	public int getEstimatedTime(LocationNoIdDTO departureLocation, LocationNoIdDTO destinationLocation);
	
	public Driver getDriverClosestToDeparture(RideDTO dto, List<Driver> drivers);
	
}
