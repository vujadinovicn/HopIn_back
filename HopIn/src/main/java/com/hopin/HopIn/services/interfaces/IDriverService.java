package com.hopin.HopIn.services.interfaces;

import java.util.List;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;

import com.hopin.HopIn.dtos.AllHoursDTO;
import com.hopin.HopIn.dtos.AllUserRidesReturnedDTO;
import com.hopin.HopIn.dtos.AllUsersDTO;
import com.hopin.HopIn.dtos.DocumentDTO;
import com.hopin.HopIn.dtos.DocumentReturnedDTO;
import com.hopin.HopIn.dtos.DriverReturnedDTO;
import com.hopin.HopIn.dtos.UserDTOOld;
import com.hopin.HopIn.dtos.UserReturnedDTO;
import com.hopin.HopIn.dtos.VehicleDTO;
import com.hopin.HopIn.dtos.WorkingHoursDTO;
import com.hopin.HopIn.entities.Document;
import com.hopin.HopIn.entities.Driver;
import com.hopin.HopIn.entities.DriverAccountUpdateDocumentRequest;
import com.hopin.HopIn.entities.DriverAccountUpdateInfoRequest;
import com.hopin.HopIn.entities.DriverAccountUpdatePasswordRequest;
import com.hopin.HopIn.entities.DriverAccountUpdateVehicleRequest;
import com.hopin.HopIn.entities.Vehicle;

public interface IDriverService {

	public UserReturnedDTO insert(UserDTOOld driver);

	public DriverReturnedDTO getById(int id);

	public UserReturnedDTO update(int id, UserDTOOld newData);

	public AllUsersDTO getAll(int page, int size);

	public DocumentReturnedDTO addDocument(int id, DocumentDTO newDocument);

	public List<DocumentReturnedDTO> getDocuments(int driverId);

	public Vehicle setVehicle(int driverId, VehicleDTO dto);

	public VehicleDTO getVehicle(int driverId);

	public Vehicle updateVehicle(int driverId, VehicleDTO vehicle);

	public WorkingHoursDTO getWorkingHours(int hoursId);

	public AllUsersDTO getAllPaginated(Pageable pageable);

	public WorkingHoursDTO addWorkingHours(int driverId, WorkingHoursDTO hours);

	public AllHoursDTO getAllHours(int id, int page, int size, String from, String to);

	WorkingHoursDTO updateWorkingHours(int hoursId, WorkingHoursDTO hours);

	public AllUserRidesReturnedDTO getAllRides(int driverId, int page, int size, String sort, String from, String to);

	void updateByInfoRequest(DriverAccountUpdateInfoRequest request);

	void updateByPasswordRequest(DriverAccountUpdatePasswordRequest request);

	void updateByVehicleRequest(DriverAccountUpdateVehicleRequest request);

	void updateByDocumentRequest(DriverAccountUpdateDocumentRequest request);

	Driver getDriver(int id);


}
