package com.hopin.HopIn.services;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.hopin.HopIn.dtos.RideDTO;
import com.hopin.HopIn.dtos.RideReturnedDTO;
import com.hopin.HopIn.dtos.RideReturnedWithRejectionDTO;
import com.hopin.HopIn.entities.Location;
import com.hopin.HopIn.entities.Ride;
import com.hopin.HopIn.services.interfaces.IRideService;

@Service
public class RideServiceImpl implements IRideService {
	
	private Map<Integer, Ride> allRides = new HashMap<Integer, Ride>();
	private int currId = 0;
	
	@Override
	public RideReturnedDTO create(RideDTO dto) {
		Ride ride = new Ride(dto, ++this.currId);
		this.allRides.put(ride.getId(), ride);
		return new RideReturnedDTO(ride);
	}
	
	@Override
	public RideReturnedDTO getActiveRideForDriver(int id) {
		for(Ride ride : this.allRides.values()) {
			if(ride.getStartTime().isBefore(LocalDateTime.now()) && ride.getEndTime().isAfter(LocalDateTime.now()) 
					&& ride.getDriver().getId() == id) {
				return new RideReturnedDTO(ride);
			}
		}
		return null;
	}
	
	@Override
	public RideReturnedWithRejectionDTO getRide(int id) {
		for(int key: this.allRides.keySet()) {
			if (key == id) {
				return new RideReturnedWithRejectionDTO(this.allRides.get(key));
			}
		}
		return null;
	}
	
}
