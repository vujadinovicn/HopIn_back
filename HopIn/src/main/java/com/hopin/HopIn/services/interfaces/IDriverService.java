package com.hopin.HopIn.services.interfaces;

import java.util.List;

import com.hopin.HopIn.dtos.AllUsersDTO;
import com.hopin.HopIn.dtos.DocumentDTO;
import com.hopin.HopIn.dtos.UserDTO;
import com.hopin.HopIn.dtos.UserReturnedDTO;
import com.hopin.HopIn.dtos.VehicleDTO;
import com.hopin.HopIn.entities.Document;
import com.hopin.HopIn.entities.Vehicle;

public interface IDriverService {

	public UserReturnedDTO insert(UserDTO driver);

	public UserReturnedDTO getById(int id);

	public UserReturnedDTO update(int id, UserDTO newData);

	public AllUsersDTO getAll(int page, int size);

	public Document addDocument(int id, DocumentDTO newDocument);

	public List<Document> getDocuments(int driverId);

	public Vehicle setVehicle(int driverId, VehicleDTO dto);

	public Vehicle getVehicle(int driverId);

	public Vehicle updateVehicle(int driverId, VehicleDTO vehicle);

}
