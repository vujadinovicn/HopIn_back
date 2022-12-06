package com.hopin.HopIn.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.stereotype.Service;

import com.hopin.HopIn.dtos.AllUsersDTO;
import com.hopin.HopIn.dtos.DocumentDTO;
import com.hopin.HopIn.dtos.UserDTO;
import com.hopin.HopIn.dtos.UserReturnedDTO;
import com.hopin.HopIn.dtos.VehicleDTO;
import com.hopin.HopIn.dtos.WorkingHoursReturnedDTO;
import com.hopin.HopIn.entities.Document;
import com.hopin.HopIn.entities.Driver;
import com.hopin.HopIn.entities.Vehicle;
import com.hopin.HopIn.entities.WorkingHours;
import com.hopin.HopIn.services.interfaces.IDriverService;

@Service
public class DriverServiceImpl implements IDriverService {

	private Map<Integer, Driver> allDrivers = new HashMap<Integer, Driver>();
	private int currId = 0;
	private int currDocId = 0;
	private int currVehicleId = 0;

	@Override
	public UserReturnedDTO insert(UserDTO dto) {
		Driver driver = dtoToDriver(dto, null);
		driver.setId(currId);
		this.allDrivers.put(currId++, driver);

		return new UserReturnedDTO(driver);
	}
	
	@Override
	public AllUsersDTO getAllPaginated(Pageable pageable) {
		if (allDrivers.size() == 0) {
			Driver driver = new Driver(0, "Pera", "Peric", "pera.peric@email.com", "123", "Bulevar Oslobodjenja 74", "+381123123", "U3dhZ2dlciByb2Nrcw==");
			allDrivers.put(driver.getId(), driver);
		}
		
		return new AllUsersDTO(this.allDrivers.values());
	}

	@Override
	public UserReturnedDTO getById(int id) {
		return new UserReturnedDTO(this.allDrivers.get(id));
	}

	@Override
	public UserReturnedDTO update(int id, UserDTO newData) {
		Driver driver = this.allDrivers.get(id);
		driver = dtoToDriver(newData, driver);
		return new UserReturnedDTO(driver);
	}

	@Override
	public AllUsersDTO getAll(int page, int size) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Document> getDocuments(int driverId) {
		return this.allDrivers.get(driverId).getDocuments();
	}

	@Override
	public Document addDocument(int driverId, DocumentDTO newDocument) {
		Driver driver = this.allDrivers.get(driverId);
		Document document = this.dtoToDocument(newDocument, null);
		document.setDriverId(driverId);
		driver.getDocuments().add(document);

		return document;
	}

	@Override
	public Vehicle getVehicle(int driverId) {
		Driver driver = this.allDrivers.get(driverId);
		return driver.getVehicle();
	}

	@Override
	public Vehicle setVehicle(int driverId, VehicleDTO dto) {
		Driver driver = this.allDrivers.get(driverId);
		Vehicle vehicle = dtoToVehicle(dto, driverId, null);
		driver.setVehicle(vehicle);

		return vehicle;
	}

	@Override
	public Vehicle updateVehicle(int driverId, VehicleDTO dto) {
		Driver driver = this.allDrivers.get(driverId);
		Vehicle vehicle = driver.getVehicle();
		dtoToVehicle(dto, driverId, vehicle);
		
		return vehicle;
	}
	
	@Override
	public WorkingHoursReturnedDTO getWorkingHours(int driverId, int hoursId) {
		Driver driver = this.allDrivers.get(driverId);
		WorkingHours workingHours = driver.getWorkingHours().stream().filter(hours -> hoursId == hours.getId()).findFirst().orElse(null);
		return new WorkingHoursReturnedDTO(workingHours.getId(), workingHours.getStart(), workingHours.getEnd());
	}


	private Vehicle dtoToVehicle(VehicleDTO dto, int driverId, Vehicle vehicle) {
		if (vehicle == null) {
			vehicle = new Vehicle();
			vehicle.setId(currVehicleId++);
		}
		
		vehicle.setModel(dto.getModel());
		vehicle.setLicenseNumber(dto.getLicenseNumber());
		vehicle.setCurrentLocation(dto.getCurrentLocation());
		vehicle.setPassengerSeats(dto.getPassengerSeats());
		vehicle.setBabyTransport(dto.isBabyTransport());
		vehicle.setPetTransport(dto.isBabyTransport());
		vehicle.setVehicleType(dto.getVehicleType());
		vehicle.setDriverId(driverId);

		return vehicle;
	}

	private Document dtoToDocument(DocumentDTO dto, Document document) {
		if (document == null) {
			document = new Document();
			document.setId(currDocId++);
		}

		document.setName(dto.getName());
		document.setDocumentImage(dto.getDocumentImage());

		return document;

	}

	private Driver dtoToDriver(UserDTO dto, Driver driver) {
		if (driver == null)
			driver = new Driver();

		driver.setName(dto.getName());
		driver.setSurname(dto.getSurname());
		driver.setEmail(dto.getEmail());
		driver.setAddress(dto.getAddress());
		driver.setTelephoneNumber(dto.getTelephoneNumber());
		driver.setPassword(dto.getPassword());
		driver.setProfilePicture(dto.getProfilePicture());

		return driver;
	}

	

	
}
