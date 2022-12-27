package com.hopin.HopIn.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hopin.HopIn.repositories.DriverAccountUpdateDocumentRequestRepository;
import com.hopin.HopIn.repositories.DriverAccountUpdateInfoRequestRepository;
import com.hopin.HopIn.repositories.DriverAccountUpdatePasswordRequestRepository;
import com.hopin.HopIn.repositories.DriverAccountUpdateRequestRepository;
import com.hopin.HopIn.repositories.DriverAccountUpdateVehicleRequestRepository;
import com.hopin.HopIn.services.interfaces.IAdministratorService;

@Service
public class AdministratorServiceImpl implements IAdministratorService{

	@Autowired
	private DriverAccountUpdatePasswordRequestRepository allDriverAccountUpdatePasswordRequests;
	
	@Autowired
	private DriverAccountUpdateDocumentRequestRepository allDriverAccountDocumentPasswordRequests;
	
	@Autowired
	private DriverAccountUpdateInfoRequestRepository allDriverAccountInfoPasswordRequests;
	
	@Autowired
	private DriverAccountUpdateVehicleRequestRepository allDriverAccountVehiclePasswordRequests;
	
	@Autowired
	private DriverAccountUpdateRequestRepository allDriverAccountUpdateRequests;
	
}
