package com.hopin.HopIn.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hopin.HopIn.entities.VehicleType;
import com.hopin.HopIn.enums.VehicleTypeName;
import com.hopin.HopIn.repositories.VehicleTypeRepository;
import com.hopin.HopIn.services.interfaces.IVehicleTypeService;

@Service
public class VehicleTypeServiceImpl implements IVehicleTypeService {
	
	@Autowired
	private VehicleTypeRepository allVehicleTypes;
	
	@Override
	public VehicleType getByName(VehicleTypeName name) {
		return this.allVehicleTypes.getByName(name);
	}
}
