package com.hopin.HopIn.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hopin.HopIn.dtos.AllPanicRidesDTO;
import com.hopin.HopIn.entities.Panic;
import com.hopin.HopIn.repositories.PanicRepository;
import com.hopin.HopIn.services.interfaces.IPanicService;

@Service
public class PanicServiceImpl implements IPanicService {
	
	@Autowired
	private PanicRepository allPanics;

	@Override
	public AllPanicRidesDTO getAllPanicRides() {
		List<Panic> panics = allPanics.findAll();
		return new AllPanicRidesDTO(panics);
	}

}
