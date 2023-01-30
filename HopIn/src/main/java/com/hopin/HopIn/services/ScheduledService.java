package com.hopin.HopIn.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.hopin.HopIn.entities.Driver;
import com.hopin.HopIn.entities.Location;
import com.hopin.HopIn.entities.Ride;
import com.hopin.HopIn.services.interfaces.IRideService;
import com.hopin.HopIn.services.interfaces.IScheduledService;

@Service
public class ScheduledService implements IScheduledService{
	

	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;
	
	@Autowired
	private IRideService rideService;
	
	@Override
	public void checkIfVehicleArrivedAtDeparture() {
		ArrayList<Ride> allAcceptedRides = (ArrayList<Ride>) this.rideService.getAllAcceptedRides();
		
		for (Ride ride: allAcceptedRides) {
			Driver driver = ride.getDriver(); 
			Location location = driver.getVehicle().getCurrentLocation();
			
			if (areDoublesSame(location.getLatitude(), ride.getDepartureLocation().getLatitude()) && areDoublesSame(location.getLongitude(), ride.getDepartureLocation().getLongitude())) {
				System.out.println("ARRIVED!");
				this.simpMessagingTemplate.convertAndSend("/topic/vehicle-arrival/" + ride.getId(), "arrived");
			} 
		}
	}
	
	private boolean areDoublesSame(Double d1, Double d2) {
		double epsilon = 0.0002d;
		return Math.abs(d1 - d2) < epsilon;
	}
	
}
