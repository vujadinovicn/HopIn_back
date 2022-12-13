package com.hopin.HopIn.services;

import org.springframework.beans.factory.annotation.Autowired;

import com.hopin.HopIn.repositories.LocationRepository;
import com.hopin.HopIn.services.interfaces.ILocationService;

public class LocationServiceImpl implements ILocationService {
	
	@Autowired
	private LocationRepository locationRepository;
}
