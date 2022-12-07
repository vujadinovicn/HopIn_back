package com.hopin.HopIn.services.interfaces;

import com.hopin.HopIn.dtos.LocationNoIdDTO;

public interface IVehicleService {

	boolean updateLocation(int vehicleId, LocationNoIdDTO newLocation);

}
