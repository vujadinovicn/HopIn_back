package com.HopIn.HopIn.services;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.HopIn.HopIn.dtos.RejectedRideDTO;
import com.HopIn.HopIn.entities.Ride;
import com.HopIn.HopIn.enums.RideStatus;
import com.HopIn.HopIn.enums.VehicleType;
import com.HopIn.HopIn.services.interfaces.IRideService;

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
