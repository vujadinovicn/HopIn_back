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
import com.hopin.HopIn.entities.Location;
import com.hopin.HopIn.entities.Vehicle;
import com.hopin.HopIn.exceptions.VehicleNotFoundException;
import com.hopin.HopIn.repositories.VehicleRepository;
import com.hopin.HopIn.services.interfaces.IVehicleService;

@Service
public class VehicleServiceImpl implements IVehicleService {
	
	@Autowired
	private VehicleRepository allVehicles;

	@Override
	public LocationNoIdDTO updateLocation(int vehicleId, LocationNoIdDTO newLocation) {
		Optional<Vehicle> vehicle = allVehicles.findById(vehicleId);
		if (vehicle.isEmpty())
			throw new VehicleNotFoundException();
		
		Location vehicleLocation = vehicle.get().getCurrentLocation();
		
		vehicleLocation.setAddress(newLocation.getAddress());
		vehicleLocation.setLatitude(newLocation.getLatitude());
		vehicleLocation.setLongitude(newLocation.getLongitude()); 
		
		this.allVehicles.save(vehicle.get());
		this.allVehicles.flush();
		
		return new LocationNoIdDTO(vehicleLocation);
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
