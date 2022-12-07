package com.hopin.HopIn.dtos;

import com.hopin.HopIn.entities.RejectionNotice;
import com.hopin.HopIn.entities.Ride;

public class RideReturnedWithRejectionDTO extends RideReturnedDTO {
	private RejectionNotice rejection;
	
	public RideReturnedWithRejectionDTO(Ride ride) {
		super(ride);
		this.setRejection(ride.getRejectionNotice());
	}

	public RejectionNotice getRejection() {
		return rejection;
	}

	public void setRejection(RejectionNotice rejection) {
		this.rejection = rejection;
	}
}
