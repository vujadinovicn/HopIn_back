package com.hopin.HopIn.services.interfaces;

import java.util.List;

import com.hopin.HopIn.dtos.DocumentRequestDTO;
import com.hopin.HopIn.dtos.InfoRequestDTO;
import com.hopin.HopIn.dtos.PasswordRequestDTO;
import com.hopin.HopIn.dtos.RequestDTO;
import com.hopin.HopIn.dtos.VehicleRequestDTO;

public interface IAdministratorService {
	
	public List<RequestDTO> getAll();
	
	public List<RequestDTO> getAllPending();
	
	public List<RequestDTO> getAllProcessed();

	List<RequestDTO> getAllDriverProcessed(int id);

	List<RequestDTO> getAllDriverPending(int id);

	List<RequestDTO> getAllAdminProcessed(int id);
	
	public InfoRequestDTO getInfoById(int id);
	
	public PasswordRequestDTO getPasswordById(int id);
	
	public DocumentRequestDTO getDocumentById(int id);
	
	public VehicleRequestDTO getVehicleById(int id);

	RequestDTO getRequestById(int id);
	
	public void acceptRequest(int requestId, int adminId);

	void denyRequest(int requestId, int adminId, String reason);

	void insertPasswordRequest(int driverId, PasswordRequestDTO dto);

	void insertInfoRequest(int driverId, InfoRequestDTO dto);

	void insertVehicleRequest(int driverId, VehicleRequestDTO dto);

	void insertDocumentRequest(int driverId, int operationNumber, int documentId, DocumentRequestDTO dto);

}
