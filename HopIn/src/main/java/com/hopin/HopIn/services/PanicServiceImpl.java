package com.hopin.HopIn.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hopin.HopIn.dtos.PanicDTO;
import com.hopin.HopIn.entities.Panic;
import com.hopin.HopIn.entities.Ride;
import com.hopin.HopIn.entities.User;
import com.hopin.HopIn.repositories.PanicRepository;
import com.hopin.HopIn.repositories.RideRepository;
import com.hopin.HopIn.services.interfaces.IPanicService;
import com.hopin.HopIn.services.interfaces.IUserService;

@Service
public class PanicServiceImpl implements IPanicService {
	
	@Autowired
	private PanicRepository allPanics;
	
	@Autowired
	private RideRepository allRides;
	
	@Autowired
	private IUserService userSerice;
	
	
	@Override
	public void add(PanicDTO dto) {
		User user = userSerice.getById(dto.getUserId());
		Ride ride = allRides.findById(dto.getRideId()).get();
		
		Panic panic = new Panic(user, ride, dto.getReason());
		allPanics.save(panic);
		allPanics.flush();
	}

}
