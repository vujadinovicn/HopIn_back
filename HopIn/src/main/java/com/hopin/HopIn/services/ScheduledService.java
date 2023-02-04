package com.hopin.HopIn.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.hopin.HopIn.dtos.RideReturnedDTO;
import com.hopin.HopIn.entities.Driver;
import com.hopin.HopIn.entities.Location;
import com.hopin.HopIn.entities.Ride;
import com.hopin.HopIn.services.interfaces.IRideService;
import com.hopin.HopIn.services.interfaces.IScheduledService;

@Service
public class ScheduledService implements IScheduledService{
	
	@Autowired
	private Environment env;

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

	@Override
	public void notifyAboutScheduledRide() {
		ArrayList<Ride> allAcceptedRides = (ArrayList<Ride>) this.rideService.getAllAcceptedRides().stream().filter(x -> x.getScheduledTime() != null).collect(Collectors.toCollection(ArrayList::new));
		for (Ride ride: allAcceptedRides) {
			long differenceInMinutes = Math.abs(ChronoUnit.MINUTES.between(ride.getScheduledTime(), LocalDateTime.now()));

			int firstNotication = Integer.parseInt(env.getProperty("firstnotification")) / 60000;
			int secondNotication = Integer.parseInt(env.getProperty("secondnotification")) / 60000;
			int thirdNotication = Integer.parseInt(env.getProperty("thirdnotification")) / 60000;
			if ((differenceInMinutes == firstNotication)  || (differenceInMinutes == secondNotication) || (differenceInMinutes == thirdNotication)) {
				System.out.println("PODSETNIK");
				this.simpMessagingTemplate.convertAndSend("/topic/scheduled-ride/" + ride.getId(), new RideReturnedDTO(ride));
			}
		}
	}
	
	private boolean areDoublesSame(Double d1, Double d2) {
		double epsilon = 0.0003d;
		return Math.abs(d1 - d2) <= epsilon;
	}
	
}
