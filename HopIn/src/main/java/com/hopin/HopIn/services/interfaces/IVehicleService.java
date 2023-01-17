package com.hopin.HopIn.services.interfaces;

import com.hopin.HopIn.dtos.LocationNoIdDTO;
import com.hopin.HopIn.entities.Vehicle;

public interface IVehicleService {

	void updateLocation(int vehicleId, LocationNoIdDTO newLocation);
	
	public Vehicle findByDriverId(int driverId);

}
