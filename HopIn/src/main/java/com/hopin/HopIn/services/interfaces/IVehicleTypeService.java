package com.hopin.HopIn.services.interfaces;

import com.hopin.HopIn.entities.VehicleType;
import com.hopin.HopIn.enums.VehicleTypeName;

public interface IVehicleTypeService {

	VehicleType getByName(VehicleTypeName name);

}
