package com.hopin.HopIn.services;

import org.springframework.stereotype.Service;

import com.hopin.HopIn.dtos.EstimatedRideDetailsDTO;
import com.hopin.HopIn.dtos.UnregisteredUserRideDTO;
import com.hopin.HopIn.services.interfaces.IUnregisteredUserService;

@Service
public class UnregisteredUserServiceImpl implements IUnregisteredUserService{

	@Override
	public EstimatedRideDetailsDTO getEstimatedRideDetails(UnregisteredUserRideDTO ride) {
		return new EstimatedRideDetailsDTO(10, 450);
	}

}
