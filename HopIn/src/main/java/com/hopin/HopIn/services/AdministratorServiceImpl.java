package com.hopin.HopIn.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hopin.HopIn.dtos.DriverAccountUpdateRequestDTO;
import com.hopin.HopIn.entities.DriverAccountUpdateRequest;
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

	@Override
	public List<DriverAccountUpdateRequestDTO> getAll() {
		List<DriverAccountUpdateRequest> requests = allDriverAccountUpdateRequests.findAll();
		return driverAccountUpdateRequestsToDtoList(requests);
	}

	@Override
	public List<DriverAccountUpdateRequestDTO> getAllPending() {
		List<DriverAccountUpdateRequest> requests = allDriverAccountUpdateRequests.findAllPending();
		return driverAccountUpdateRequestsToDtoList(requests);
	}
	
	@Override
	public List<DriverAccountUpdateRequestDTO> getAllProcessed() {
		List<DriverAccountUpdateRequest> requests = allDriverAccountUpdateRequests.findAllProcessed();
		return driverAccountUpdateRequestsToDtoList(requests);
	}	

	private List<DriverAccountUpdateRequestDTO> driverAccountUpdateRequestsToDtoList(
			List<DriverAccountUpdateRequest> requests) {
		List<DriverAccountUpdateRequestDTO> requestsDTO = new ArrayList<DriverAccountUpdateRequestDTO>();
		for (DriverAccountUpdateRequest request : requests) {
			requestsDTO.add(new DriverAccountUpdateRequestDTO(request));
		}
		return requestsDTO;
	}
}
