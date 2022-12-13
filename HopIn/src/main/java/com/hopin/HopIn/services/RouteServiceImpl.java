package com.hopin.HopIn.services;

import org.springframework.beans.factory.annotation.Autowired;

import com.hopin.HopIn.repositories.RouteRepository;
import com.hopin.HopIn.services.interfaces.IRouteService;

public class RouteServiceImpl implements IRouteService {
	
	@Autowired
	private RouteRepository allRoutes;
}
