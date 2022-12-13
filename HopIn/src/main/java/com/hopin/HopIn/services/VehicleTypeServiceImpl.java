package com.hopin.HopIn.services;

import org.springframework.beans.factory.annotation.Autowired;

import com.hopin.HopIn.repositories.VehicleTypeRepository;
import com.hopin.HopIn.services.interfaces.IVehicleTypeService;

public class VehicleTypeServiceImpl implements IVehicleTypeService {
	
	@Autowired
	private VehicleTypeRepository allVehicleTypes;
}
