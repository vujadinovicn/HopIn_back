package com.hopin.HopIn.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hopin.HopIn.dtos.EstimatedRideDetailsDTO;
import com.hopin.HopIn.dtos.LocationDTO;
import com.hopin.HopIn.dtos.LocationNoIdDTO;
import com.hopin.HopIn.dtos.UnregisteredUserRideDTO;
import com.hopin.HopIn.entities.VehicleType;
import com.hopin.HopIn.enums.VehicleTypeName;
import com.hopin.HopIn.repositories.VehicleTypeRepository;
import com.hopin.HopIn.services.interfaces.IRideEstimationService;
import com.hopin.HopIn.services.interfaces.IUnregisteredUserService;

@Service
public class UnregisteredUserServiceImpl implements IUnregisteredUserService{

	@Autowired
	private IRideEstimationService rideEstimationService;
	
	@Autowired
	private VehicleTypeRepository allVehicleTypes;
	
	@Override
	public EstimatedRideDetailsDTO getEstimatedRideDetails(UnregisteredUserRideDTO ride) {
		LocationDTO location = ride.getLocations().get(0);
		LocationNoIdDTO departureLocation = location.getDeparture();
		LocationNoIdDTO destinationLocation = location.getDestination();
		
		int durationInMinutes = this.rideEstimationService.getEstimatedTime(departureLocation, destinationLocation);
		double distance = this.rideEstimationService.getEstimatedDistance(departureLocation, destinationLocation);
		int price = (int) calculatePrice(distance, ride.getVehicleType().toString());
		return new EstimatedRideDetailsDTO(durationInMinutes, price, distance);
	}
	
	private double calculatePrice(double distance, String vehicleTypeName) {
		return 60 * distance;
	}

}
