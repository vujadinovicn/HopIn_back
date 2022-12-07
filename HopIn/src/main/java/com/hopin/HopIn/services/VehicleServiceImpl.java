package com.hopin.HopIn.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.hopin.HopIn.dtos.LocationNoIdDTO;
import com.hopin.HopIn.entities.Vehicle;
import com.hopin.HopIn.services.interfaces.IVehicleService;

@Service
public class VehicleServiceImpl implements IVehicleService {
	
	private Map<Integer, Vehicle> allVehicles = new HashMap<Integer, Vehicle>();
	int currId = 0;

	@Override
	public boolean updateLocation(int vehicleId, LocationNoIdDTO newLocation) {
		Vehicle vehicle = allVehicles.get(vehicleId);
		if (vehicle != null)
			vehicle.setCurrentLocation(newLocation);
		return true;
	}
}
