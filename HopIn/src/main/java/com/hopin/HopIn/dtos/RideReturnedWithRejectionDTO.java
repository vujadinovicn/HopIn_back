package com.hopin.HopIn.dtos;

import java.time.LocalDateTime;
import java.util.List;

import com.hopin.HopIn.entities.RejectionNotice;
import com.hopin.HopIn.entities.Ride;
import com.hopin.HopIn.enums.RideStatus;
import com.hopin.HopIn.enums.VehicleTypeName;

public class RideReturnedWithRejectionDTO extends RideReturnedDTO {
	public RideReturnedWithRejectionDTO(int id, LocalDateTime startTime, LocalDateTime endTime, double totalCost,
			UserInRideDTO driver, List<UserInRideDTO> passengers, int estimatedTimeInMinutes,
			VehicleTypeName vehicleType, boolean petTransport, boolean babyTransport, List<LocationDTO> locations,
			RideStatus status) {
		super(id, startTime, endTime, totalCost, driver, passengers, estimatedTimeInMinutes, vehicleType, petTransport,
				babyTransport, locations, status);
		// TODO Auto-generated constructor stub
	}

	private RejectionNotice rejection;
	
//	public RideReturnedWithRejectionDTO(Ride ride) {
//		super(ride);
//		this.setRejection(ride.getRejectionNotice());
//	}

	public RejectionNotice getRejection() {
		return rejection;
	}

	public void setRejection(RejectionNotice rejection) {
		this.rejection = rejection;
	}
}
