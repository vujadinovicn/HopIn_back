package com.hopin.HopIn.services.interfaces;

import com.hopin.HopIn.dtos.LocationNoIdDTO;
import com.hopin.HopIn.entities.Vehicle;

public interface IVehicleService {

	LocationNoIdDTO updateLocation(int vehicleId, LocationNoIdDTO newLocation);
	
	public Vehicle findByDriverId(int driverId);

}
