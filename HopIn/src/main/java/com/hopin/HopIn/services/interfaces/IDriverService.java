package com.hopin.HopIn.services.interfaces;

import java.util.List;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;

import com.hopin.HopIn.dtos.ActiveVehicleDTO;
import com.hopin.HopIn.dtos.AllHoursDTO;
import com.hopin.HopIn.dtos.AllPassengerRidesDTO;
import com.hopin.HopIn.dtos.AllUserRidesReturnedDTO;
import com.hopin.HopIn.dtos.AllUsersDTO;
import com.hopin.HopIn.dtos.DocumentDTO;
import com.hopin.HopIn.dtos.DocumentReturnedDTO;
import com.hopin.HopIn.dtos.DriverReturnedDTO;
import com.hopin.HopIn.dtos.UserDTO;
import com.hopin.HopIn.dtos.RideDTO;
import com.hopin.HopIn.dtos.RideForReportDTO;
import com.hopin.HopIn.dtos.UserDTOOld;
import com.hopin.HopIn.dtos.UserReturnedDTO;
import com.hopin.HopIn.dtos.VehicleDTO;
import com.hopin.HopIn.dtos.VehicleReturnedDTO;
import com.hopin.HopIn.dtos.WorkingHoursDTO;
import com.hopin.HopIn.entities.Driver;
import com.hopin.HopIn.entities.DriverAccountUpdateDocumentRequest;
import com.hopin.HopIn.entities.DriverAccountUpdateInfoRequest;
import com.hopin.HopIn.entities.DriverAccountUpdatePasswordRequest;
import com.hopin.HopIn.entities.DriverAccountUpdateVehicleRequest;
import com.hopin.HopIn.entities.User;

public interface IDriverService {

	public UserReturnedDTO insert(UserDTO driver);

	public DriverReturnedDTO getById(int id);

	public UserReturnedDTO update(int id, UserDTO newData);

	public AllUsersDTO getAll(int page, int size);

	public DocumentReturnedDTO addDocument(int id, DocumentDTO newDocument);

	public List<DocumentReturnedDTO> getDocuments(int driverId);

	public VehicleReturnedDTO insertVehicle(int driverId, VehicleDTO dto);

	public VehicleReturnedDTO getVehicle(int driverId);

	public VehicleReturnedDTO updateVehicle(int driverId, VehicleDTO vehicle);

	public WorkingHoursDTO getWorkingHours(int hoursId);

	AllUsersDTO getAllPaginated(int page, int size);

	public WorkingHoursDTO addWorkingHours(int driverId, WorkingHoursDTO hours);

	WorkingHoursDTO updateWorkingHours(int hoursId, WorkingHoursDTO hours);

	public AllUserRidesReturnedDTO getAllRides(int driverId, int page, int size, String sort, String from, String to);

	void updateByInfoRequest(DriverAccountUpdateInfoRequest request);

	void updateByPasswordRequest(DriverAccountUpdatePasswordRequest request);

	void updateByVehicleRequest(DriverAccountUpdateVehicleRequest request);

	void updateByDocumentRequest(DriverAccountUpdateDocumentRequest request);
	
	public List<Driver> getActiveDrivers();
	
	public void setDriverActivity(int driverId, boolean isActive);

	Driver getDriver(int id);
	
	public boolean isDriverVehicleAppropriateForRide(int id, RideDTO rideDTO);
	
	public List<Driver> getDriversWithAppropriateVehicleForRide(List<Driver> drivers, RideDTO rideDTO);

	void deleteDocument(int id);

	public AllHoursDTO getAllHours(int id, int page, int size);

	AllUsersDTO getAll();

	public List<ActiveVehicleDTO> getAllVehicles();
	
	public Driver getByEmail(String email);

	public AllPassengerRidesDTO getAllDriverRides(int id);

	public AllPassengerRidesDTO getAllDriverRidesPaginated(int driverId, int page, int size, String sort, String from,
			String to);

	public List<RideForReportDTO> getAllDriverRidesBetweenDates(int id, String from, String to);
}
