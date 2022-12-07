package com.HopIn.HopIn.services;

import org.springframework.stereotype.Service;

import com.HopIn.HopIn.dtos.EstimatedRideDetailsDTO;
import com.HopIn.HopIn.dtos.UnregisteredUserRideDTO;
import com.HopIn.HopIn.services.interfaces.IUnregisteredUserService;

@Service
public class UnregisteredUserServiceImpl implements IUnregisteredUserService{

	@Override
	public EstimatedRideDetailsDTO getEstimatedRideDetails(UnregisteredUserRideDTO ride) {
		return new EstimatedRideDetailsDTO(10, 450);
	}

}
