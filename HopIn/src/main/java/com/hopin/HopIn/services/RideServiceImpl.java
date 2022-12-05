package com.hopin.HopIn.services;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.hopin.HopIn.dtos.PanicRideDTO;
import com.hopin.HopIn.dtos.ReasonDTO;
import com.hopin.HopIn.dtos.RideDTO;
import com.hopin.HopIn.dtos.RideReturnedDTO;
import com.hopin.HopIn.dtos.RideReturnedWithRejectionDTO;
import com.hopin.HopIn.entities.RejectionNotice;
import com.hopin.HopIn.entities.Ride;
import com.hopin.HopIn.enums.RideStatus;
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
	
	@Override
	public boolean cancelRide(int id) {
		for (int key : this.allRides.keySet()) {
			if (key == id) {
				this.allRides.remove(key);
				return true;
			}
		}
		return false;
	}
	
	@Override
	public PanicRideDTO panicRide(int id, ReasonDTO reason) {
		Ride ride = this.allRides.get(id);
		if(ride != null) {
			return new PanicRideDTO(new RideReturnedDTO(ride), reason.getReason());
		}
		return null;
	}
	
	@Override
	public RideReturnedDTO changeRideStatus(int id, RideStatus status) {
		Ride ride = this.allRides.get(id);
		if (ride != null) {
			ride.setStatus(status);
			return new RideReturnedDTO(ride);
		}
		return null;
	}
	
	@Override
	public RideReturnedDTO rejectRide(int id, ReasonDTO reason) {
		Ride ride = this.allRides.get(id);
		if (ride != null) {
			ride.setStatus(RideStatus.REJECTED);
			ride.setRejectionNotice(new RejectionNotice(reason.getReason()));
			return new RideReturnedDTO(ride);
		}
		return null;
	}
	
}
