package com.hopin.HopIn.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.hopin.HopIn.dtos.DocumentRequestDTO;
import com.hopin.HopIn.dtos.DriverSentPasswordRequestDTO;
import com.hopin.HopIn.dtos.InfoRequestDTO;
import com.hopin.HopIn.dtos.PasswordRequestDTO;
import com.hopin.HopIn.dtos.RequestDTO;
import com.hopin.HopIn.dtos.VehicleRequestDTO;
import com.hopin.HopIn.entities.Administrator;
import com.hopin.HopIn.entities.Driver;
import com.hopin.HopIn.entities.DriverAccountUpdateDocumentRequest;
import com.hopin.HopIn.entities.DriverAccountUpdateInfoRequest;
import com.hopin.HopIn.entities.DriverAccountUpdatePasswordRequest;
import com.hopin.HopIn.entities.DriverAccountUpdateRequest;
import com.hopin.HopIn.entities.DriverAccountUpdateVehicleRequest;
import com.hopin.HopIn.enums.RequestStatus;
import com.hopin.HopIn.enums.RequestType;
import com.hopin.HopIn.repositories.AdministratorRepository;
import com.hopin.HopIn.repositories.DriverAccountUpdateDocumentRequestRepository;
import com.hopin.HopIn.repositories.DriverAccountUpdateInfoRequestRepository;
import com.hopin.HopIn.repositories.DriverAccountUpdatePasswordRequestRepository;
import com.hopin.HopIn.repositories.DriverAccountUpdateRequestRepository;
import com.hopin.HopIn.repositories.DriverAccountUpdateVehicleRequestRepository;
import com.hopin.HopIn.services.interfaces.IAdministratorService;
import com.hopin.HopIn.services.interfaces.IDriverService;
import com.hopin.HopIn.services.interfaces.IUserService;

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
	
	@Autowired
	private AdministratorRepository allAdministrators;
	
	@Autowired
	private IDriverService driverService;
	
	@Autowired
	private IUserService userService;

	@Override
	public List<RequestDTO> getAll() {
		List<DriverAccountUpdateRequest> requests = allDriverAccountUpdateRequests.findAll();
		return driverAccountUpdateRequestsToDtoList(requests);
	}

	@Override
	public List<RequestDTO> getAllPending() {
		List<DriverAccountUpdateRequest> requests = allDriverAccountUpdateRequests.findAllPending();
		return driverAccountUpdateRequestsToDtoList(requests);
	}
	
	@Override
	public List<RequestDTO> getAllProcessed() {
		List<DriverAccountUpdateRequest> requests = allDriverAccountUpdateRequests.findAllProcessed();
		return driverAccountUpdateRequestsToDtoList(requests);
	}	

	private List<RequestDTO> driverAccountUpdateRequestsToDtoList(
			List<DriverAccountUpdateRequest> requests) {
		List<RequestDTO> requestsDTO = new ArrayList<RequestDTO>();
		for (DriverAccountUpdateRequest request : requests) {
			requestsDTO.add(new RequestDTO(request));
		}
		return requestsDTO;
	}
	
	@Override
	public List<RequestDTO> getAllDriverProcessed(int id) {
		List<DriverAccountUpdateRequest> requests = allDriverAccountUpdateRequests.findAllDriverProcessed(id);
		return driverAccountUpdateRequestsToDtoList(requests);
	}
	
	@Override
	public List<RequestDTO> getAllDriverPending(int id) {
		List<DriverAccountUpdateRequest> requests = allDriverAccountUpdateRequests.findAllDriverPending(id);
		return driverAccountUpdateRequestsToDtoList(requests);
	}
	
	@Override
	public List<RequestDTO> getAllAdminProcessed(int id) {
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
	public InfoRequestDTO getInfoById(int id) {
		Optional<DriverAccountUpdateRequest> found = this.allDriverAccountUpdateRequests.findById(id);
		if (found.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
		}
		DriverAccountUpdateInfoRequest request = allDriverAccountUpdateInfoRequests.findById(id).get();
		return driverAccountUpdateInfoRequestToDto(request);
	}
	
	private InfoRequestDTO driverAccountUpdateInfoRequestToDto(DriverAccountUpdateInfoRequest request) {
		return new InfoRequestDTO(request);
	}

	@Override
	public PasswordRequestDTO getPasswordById(int id) {
		Optional<DriverAccountUpdateRequest> found = this.allDriverAccountUpdateRequests.findById(id);
		if (found.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
		}
		DriverAccountUpdatePasswordRequest request = allDriverAccountUpdatePasswordRequests.findById(id).get();
		return driverAccountUpdatePasswordRequestToDto(request);
	}
	
	private PasswordRequestDTO driverAccountUpdatePasswordRequestToDto(DriverAccountUpdatePasswordRequest request) {
		return new PasswordRequestDTO(request);
	}

	@Override
	public DocumentRequestDTO getDocumentById(int id) {
		Optional<DriverAccountUpdateRequest> found = this.allDriverAccountUpdateRequests.findById(id);
		if (found.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
		}
		DriverAccountUpdateDocumentRequest request = allDriverAccountUpdateDocumentRequests.findById(id).get();
		return driverAccountUpdateDocumentRequestToDto(request);
	}
	
	private DocumentRequestDTO driverAccountUpdateDocumentRequestToDto(DriverAccountUpdateDocumentRequest request) {
		return new DocumentRequestDTO(request);
	}

	@Override
	public VehicleRequestDTO getVehicleById(int id) {
		Optional<DriverAccountUpdateRequest> found = this.allDriverAccountUpdateRequests.findById(id);
		if (found.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
		}
		DriverAccountUpdateVehicleRequest request = allDriverAccountUpdateVehicleRequests.findById(id).get();
		return driverAccountUpdateVehicleRequestToDto(request);
	}
	
	private VehicleRequestDTO driverAccountUpdateVehicleRequestToDto(DriverAccountUpdateVehicleRequest request) {
		return new VehicleRequestDTO(request);
	}
	
	@Override
	public RequestDTO getRequestById(int id) {
		Optional<DriverAccountUpdateRequest> found = this.allDriverAccountUpdateRequests.findById(id);
		if (found.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
		}
		return driverAccountUpdateRequestToDto(found.get());
	}
	
	private RequestDTO driverAccountUpdateRequestToDto(DriverAccountUpdateRequest request) {
		return new RequestDTO(request);
	}
	
	@Override
	public void acceptRequest(int requestId, int adminId) {
		Optional<DriverAccountUpdateRequest> foundRequest = this.allDriverAccountUpdateRequests.findById(requestId);
		Optional<Administrator> foundAdmin = this.allAdministrators.findById(adminId);
		if (foundRequest.isEmpty() || foundAdmin.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Request not found.");
		}
		
		if (foundRequest.get().getType() == RequestType.INFO) {
			DriverAccountUpdateInfoRequest request = this.allDriverAccountUpdateInfoRequests.findById(requestId).get();
			this.driverService.updateByInfoRequest(request);
			request.setAdmin(foundAdmin.get());
		} else if (foundRequest.get().getType() == RequestType.PASSWORD) {
			DriverAccountUpdatePasswordRequest request = this.allDriverAccountUpdatePasswordRequests.findById(requestId).get();
			this.driverService.updateByPasswordRequest(request);
			request.setAdmin(foundAdmin.get());
		} else if (foundRequest.get().getType() == RequestType.VEHICLE) {
			DriverAccountUpdateVehicleRequest request = this.allDriverAccountUpdateVehicleRequests.findById(requestId).get();
			this.driverService.updateByVehicleRequest(request);
			request.setAdmin(foundAdmin.get());
		} else {
			DriverAccountUpdateDocumentRequest request = this.allDriverAccountUpdateDocumentRequests.findById(requestId).get();
			this.driverService.updateByDocumentRequest(request);
			request.setAdmin(foundAdmin.get());
		}
		foundRequest.get().setStatus(RequestStatus.ACCEPTED);
		this.allDriverAccountUpdateRequests.save(foundRequest.get());
		this.allDriverAccountUpdateRequests.flush();
	}
	
	@Override
	public void denyRequest(int requestId, int adminId, String reason) {
		Optional<DriverAccountUpdateRequest> foundRequest = this.allDriverAccountUpdateRequests.findById(requestId);
		Optional<Administrator> foundAdmin = this.allAdministrators.findById(adminId);
		if (foundRequest.isEmpty() || foundAdmin.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Request not found.");
		}
		
		DriverAccountUpdateRequest request = foundRequest.get();
		request.setReason(reason);
		request.setAdmin(foundAdmin.get());
		request.setStatus(RequestStatus.DENIED);
		this.allDriverAccountUpdateRequests.save(request);
		this.allDriverAccountUpdateRequests.flush();
	}
	
	@Override
	public void insertPasswordRequest(int driverId, DriverSentPasswordRequestDTO dto) {
		Driver driver = this.driverService.getDriver(driverId);
		
		DriverAccountUpdatePasswordRequest request = new DriverAccountUpdatePasswordRequest(dto, driver);
		this.allDriverAccountUpdatePasswordRequests.save(request);
		this.allDriverAccountUpdatePasswordRequests.flush();
	}
	
	
}
