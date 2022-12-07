package com.hopin.HopIn.services;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.hopin.HopIn.dtos.RejectedRideDTO;
import com.hopin.HopIn.entities.Ride;
import com.hopin.HopIn.enums.RideStatus;
import com.hopin.HopIn.enums.VehicleType;
import com.hopin.HopIn.services.interfaces.IRideService;

@Service
public class RideServiceImpl implements IRideService {
	
	private Map<Integer, Ride> allRides = new HashMap<Integer, Ride>();
	private int currId = 0;
	
	@Override
	public RejectedRideDTO getRide(int id) {
		Ride r = new Ride(id, LocalDateTime.now(), LocalDateTime.now(), 300, 5, RideStatus.PENDING, false, false, false, VehicleType.STANDARD, null, null, null, null, null);
		this.allRides.put(id, r);
		Ride ride = this.allRides.get(id);
		return new RejectedRideDTO(ride);
	}
	
}
