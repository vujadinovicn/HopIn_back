package com.hopin.HopIn.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.stereotype.Service;

import com.hopin.HopIn.dtos.AllHoursDTO;
import com.hopin.HopIn.dtos.AllUserRidesReturnedDTO;
import com.hopin.HopIn.dtos.AllUsersDTO;
import com.hopin.HopIn.dtos.DocumentDTO;
import com.hopin.HopIn.dtos.LocationNoIdDTO;
import com.hopin.HopIn.dtos.UserDTO;
import com.hopin.HopIn.dtos.UserReturnedDTO;
import com.hopin.HopIn.dtos.VehicleDTO;
import com.hopin.HopIn.dtos.WorkingHoursDTO;
import com.hopin.HopIn.entities.Document;
import com.hopin.HopIn.entities.Driver;
import com.hopin.HopIn.entities.Vehicle;
import com.hopin.HopIn.entities.WorkingHours;
import com.hopin.HopIn.repositories.DriverRepository;
import com.hopin.HopIn.services.interfaces.IDriverService;

@Service
public class DriverServiceImpl implements IDriverService {

	@Autowired
	private DriverRepository allDrivers;
	
	private Map<Integer, Driver> allDriversMap = new HashMap<Integer, Driver>();
	private int currId = 1;
	private int currDocId = 1;
	private int currVehicleId = 1;
	private int currHoursId = 1;

	@Override
	public UserReturnedDTO insert(UserDTO dto) {
		Driver driver = dtoToDriver(dto, null);
		driver.setId(currId);
		this.allDriversMap.put(currId++, driver);
		return new UserReturnedDTO(driver);
	}
	
	@Override
	public AllUsersDTO getAllPaginated(Pageable pageable) {
		if (allDriversMap.size() == 0) {
			Driver driver = new Driver(0, "Pera", "Peric", "pera.peric@email.com", "123", "Bulevar Oslobodjenja 74", "+381123123", "U3dhZ2dlciByb2Nrcw==");
			allDriversMap.put(driver.getId(), driver);
		}
		
		return new AllUsersDTO(this.allDriversMap.values());
	}

	@Override
	public UserReturnedDTO getById(int id) {
		Driver driver = this.allDriversMap.get(id);
		if (driver == null)
			driver = new Driver();
		return new UserReturnedDTO(driver);
	}

	@Override
	public UserReturnedDTO update(int id, UserDTO newData) {
		Driver driver = this.allDriversMap.get(id);
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
//		return this.allDrivers.get(driverId).getDocuments();
		return null;
	}

	@Override
	public Document addDocument(int driverId, DocumentDTO newDocument) {
		Driver driver = this.allDriversMap.get(driverId);
		Document document = this.dtoToDocument(newDocument, null);
		document.setDriverId(driverId);
		driver.getDocuments().add(document);

		return document;
	}

	@Override
	public Vehicle getVehicle(int driverId) {
		Driver driver = this.allDriversMap.get(driverId);
		Vehicle vehicle = new Vehicle();
		
//		if (driver != null)
//			vehicle = driver.getVehicle();
//		if (vehicle == null) {
//			vehicle = new Vehicle();
//			vehicle.setCurrentLocation(new LocationNoIdDTO());
//		}
		
		return vehicle;
	}

	@Override
	public Vehicle setVehicle(int driverId, VehicleDTO dto) {
		Driver driver = this.allDriversMap.get(driverId);
		Vehicle vehicle;
		if (driver != null) {
			vehicle = dtoToVehicle(dto, driverId, null);
			driver.setVehicle(vehicle);
		} else {
			vehicle = new Vehicle();
		}
		
		return vehicle;
	}

	@Override
	public Vehicle updateVehicle(int driverId, VehicleDTO dto) {
		Driver driver = this.allDriversMap.get(driverId);
		Vehicle vehicle = driver.getVehicle();
		if (vehicle == null)
			vehicle = new Vehicle();
		dtoToVehicle(dto, driverId, vehicle);
		
		return vehicle;
	}
	
	@Override
	public AllHoursDTO getAllHours(int id, int page, int size, String from, String to) {
		return new AllHoursDTO(1, new ArrayList<WorkingHours>() {
            {
                add(new WorkingHours(0, LocalDateTime.now(), LocalDateTime.now(), 0));
            }
        });
	}
	
	@Override
	public WorkingHoursDTO getWorkingHours(int hoursId) {
		return new WorkingHoursDTO(new WorkingHours(hoursId, LocalDateTime.now(), LocalDateTime.now(), 0));
	}
	
	@Override
	public WorkingHoursDTO addWorkingHours(int driverId, WorkingHoursDTO hours) {
		Driver driver = this.allDriversMap.get(driverId);
		WorkingHours newHours = new WorkingHours(currHoursId++, hours.getStart(), hours.getEnd(), driverId);
		driver.getWorkingHours().add(newHours);
		
		System.out.println(new Date());
		
		return new WorkingHoursDTO(newHours);
	}
	
	
	@Override
	public WorkingHoursDTO updateWorkingHours(int hoursId, WorkingHoursDTO hours) {
		// vidi ovde kako bi zapravo sa repo bilo
		return new WorkingHoursDTO(new WorkingHours(hoursId, hours.getStart(), hours.getEnd(), hoursId));
	}

	
	@Override
	public AllUserRidesReturnedDTO getAllRides(int driverId, int page, int size, String sort, String from, String to) {
		return new AllUserRidesReturnedDTO();
	}
	
	
	private Vehicle dtoToVehicle(VehicleDTO dto, int driverId, Vehicle vehicle) {
		if (vehicle == null) {
			vehicle = new Vehicle();
			vehicle.setId(currVehicleId++);
		}
		
//		vehicle.setModel(dto.getModel());
//		vehicle.setLicenseNumber(dto.getLicenseNumber());
//		vehicle.setCurrentLocation(dto.getCurrentLocation());
//		vehicle.setPassengerSeats(dto.getPassengerSeats());
//		vehicle.setBabyTransport(dto.isBabyTransport());
//		vehicle.setPetTransport(dto.isBabyTransport());
//		vehicle.setVehicleType(dto.getVehicleType());
//		vehicle.setDriverId(driverId);

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
