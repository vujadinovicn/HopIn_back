package com.hopin.HopIn.services;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.hopin.HopIn.dtos.LocationNoIdDTO;
import com.hopin.HopIn.dtos.UserReturnedDTO;
import com.hopin.HopIn.entities.Vehicle;
import com.hopin.HopIn.repositories.VehicleRepository;
import com.hopin.HopIn.services.interfaces.IVehicleService;

@Service
public class VehicleServiceImpl implements IVehicleService {
	
	@Autowired
	private VehicleRepository allVehicles;
	
	private Map<Integer, Vehicle> allVehiclesMap = new HashMap<Integer, Vehicle>();
	int currId = 0;

	@Override
	public boolean updateLocation(int vehicleId, LocationNoIdDTO newLocation) {
		Vehicle vehicle = allVehiclesMap.get(vehicleId);
//		if (vehicle != null)
//			vehicle.setCurrentLocation(newLocation);
		return true;
	}
	
	@Override
	public Vehicle findByDriverId(int driverId) {
		Optional<Vehicle> found = allVehicles.findByDriverId(driverId);
		if (found.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
		}
		return found.get();
	}
}
