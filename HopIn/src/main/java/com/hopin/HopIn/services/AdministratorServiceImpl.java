package com.hopin.HopIn.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.hopin.HopIn.dtos.DriverAccountUpdateDocumentRequestDTO;
import com.hopin.HopIn.dtos.DriverAccountUpdateInfoRequestDTO;
import com.hopin.HopIn.dtos.DriverAccountUpdatePasswordRequestDTO;
import com.hopin.HopIn.dtos.DriverAccountUpdateRequestDTO;
import com.hopin.HopIn.dtos.DriverAccountUpdateVehicleRequestDTO;
import com.hopin.HopIn.entities.DriverAccountUpdateDocumentRequest;
import com.hopin.HopIn.entities.DriverAccountUpdateInfoRequest;
import com.hopin.HopIn.entities.DriverAccountUpdatePasswordRequest;
import com.hopin.HopIn.entities.DriverAccountUpdateRequest;
import com.hopin.HopIn.entities.DriverAccountUpdateVehicleRequest;
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
	private DriverAccountUpdateDocumentRequestRepository allDriverAccountUpdateDocumentRequests;
	
	@Autowired
	private DriverAccountUpdateInfoRequestRepository allDriverAccountUpdateInfoRequests;
	
	@Autowired
	private DriverAccountUpdateVehicleRequestRepository allDriverAccountUpdateVehicleRequests;
	
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
	
	@Override
	public List<DriverAccountUpdateRequestDTO> getAllDriverProcessed(int id) {
		List<DriverAccountUpdateRequest> requests = allDriverAccountUpdateRequests.findAllDriverProcessed(id);
		return driverAccountUpdateRequestsToDtoList(requests);
	}
	
	@Override
	public List<DriverAccountUpdateRequestDTO> getAllDriverPending(int id) {
		List<DriverAccountUpdateRequest> requests = allDriverAccountUpdateRequests.findAllDriverPending(id);
		return driverAccountUpdateRequestsToDtoList(requests);
	}
	
	@Override
	public List<DriverAccountUpdateRequestDTO> getAllAdminProcessed(int id) {
		List<DriverAccountUpdateRequest> requests = allDriverAccountUpdateRequests.findAllAdminProcessed(id);
		return driverAccountUpdateRequestsToDtoList(requests);
	}
	
//	@Override
//	public DriverAccountUpdateRequest getById(int id) {
//		Optional<DriverAccountUpdateRequest> found = this.allDriverAccountUpdateRequests.findById(id);
//		if (found.isEmpty()) {
//			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
//		}
//		
//		DriverAccountUpdateRequest ret;
//		if (found.get().getType() == RequestType.INFO) {
//			ret = this.allDriverAccountInfoPasswordRequests.findById(id).get();
//		} else if (found.get().getType() == RequestType.PASSWORD) {
//			ret = this.allDriverAccountUpdatePasswordRequests.findById(id).get();
//		} else if (found.get().getType() == RequestType.DOCUMENT) {
//			ret = this.allDriverAccountDocumentPasswordRequests.findById(id).get();
//		}else {
//			ret = this.allDriverAccountVehiclePasswordRequests.findById(id).get();			
//		}
//		return ret;
//	}

	@Override
	public DriverAccountUpdateInfoRequestDTO getInfoById(int id) {
		Optional<DriverAccountUpdateRequest> found = this.allDriverAccountUpdateRequests.findById(id);
		if (found.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
		}
		DriverAccountUpdateInfoRequest request = allDriverAccountUpdateInfoRequests.findById(id).get();
		return driverAccountUpdateInfoRequestToDto(request);
	}
	
	private DriverAccountUpdateInfoRequestDTO driverAccountUpdateInfoRequestToDto(DriverAccountUpdateInfoRequest request) {
		return new DriverAccountUpdateInfoRequestDTO(request);
	}

	@Override
	public DriverAccountUpdatePasswordRequestDTO getPasswordById(int id) {
		Optional<DriverAccountUpdateRequest> found = this.allDriverAccountUpdateRequests.findById(id);
		if (found.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
		}
		DriverAccountUpdatePasswordRequest request = allDriverAccountUpdatePasswordRequests.findById(id).get();
		return driverAccountUpdatePasswordRequestToDto(request);
	}
	
	private DriverAccountUpdatePasswordRequestDTO driverAccountUpdatePasswordRequestToDto(DriverAccountUpdatePasswordRequest request) {
		return new DriverAccountUpdatePasswordRequestDTO(request);
	}

	@Override
	public DriverAccountUpdateDocumentRequestDTO getDocumentById(int id) {
		Optional<DriverAccountUpdateRequest> found = this.allDriverAccountUpdateRequests.findById(id);
		if (found.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
		}
		DriverAccountUpdateDocumentRequest request = allDriverAccountUpdateDocumentRequests.findById(id).get();
		return driverAccountUpdateDocumentRequestToDto(request);
	}
	
	private DriverAccountUpdateDocumentRequestDTO driverAccountUpdateDocumentRequestToDto(DriverAccountUpdateDocumentRequest request) {
		return new DriverAccountUpdateDocumentRequestDTO(request);
	}

	@Override
	public DriverAccountUpdateVehicleRequestDTO getVehicleById(int id) {
		Optional<DriverAccountUpdateRequest> found = this.allDriverAccountUpdateRequests.findById(id);
		if (found.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
		}
		DriverAccountUpdateVehicleRequest request = allDriverAccountUpdateVehicleRequests.findById(id).get();
		return driverAccountUpdateVehicleRequestToDto(request);
	}
	
	private DriverAccountUpdateVehicleRequestDTO driverAccountUpdateVehicleRequestToDto(DriverAccountUpdateVehicleRequest request) {
		return new DriverAccountUpdateVehicleRequestDTO(request);
	}
	
	@Override
	public DriverAccountUpdateRequestDTO getRequestById(int id) {
		Optional<DriverAccountUpdateRequest> found = this.allDriverAccountUpdateRequests.findById(id);
		if (found.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
		}
		return driverAccountUpdateRequestToDto(found.get());
	}
	
	private DriverAccountUpdateRequestDTO driverAccountUpdateRequestToDto(DriverAccountUpdateRequest request) {
		return new DriverAccountUpdateRequestDTO(request);
	}
	
	
}
