package com.hopin.HopIn.services;

import org.springframework.beans.factory.annotation.Autowired;

import com.hopin.HopIn.repositories.PanicRepository;
import com.hopin.HopIn.services.interfaces.IPanicService;

public class PanicServiceImpl implements IPanicService {
	
	@Autowired
	private PanicRepository allPanics;

}
