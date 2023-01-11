package com.hopin.HopIn.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.hopin.HopIn.dtos.AllHoursDTO;
import com.hopin.HopIn.dtos.AllUserRidesReturnedDTO;
import com.hopin.HopIn.dtos.AllUsersDTO;
import com.hopin.HopIn.dtos.DocumentDTO;
import com.hopin.HopIn.dtos.DocumentReturnedDTO;
import com.hopin.HopIn.dtos.DriverReturnedDTO;
import com.hopin.HopIn.dtos.RideDTO;
import com.hopin.HopIn.dtos.UserDTOOld;
import com.hopin.HopIn.dtos.UserReturnedDTO;
import com.hopin.HopIn.dtos.VehicleDTO;
import com.hopin.HopIn.dtos.VehicleReturnedDTO;
import com.hopin.HopIn.dtos.WorkingHoursDTO;
import com.hopin.HopIn.entities.Document;
import com.hopin.HopIn.entities.Driver;
import com.hopin.HopIn.entities.DriverAccountUpdateDocumentRequest;
import com.hopin.HopIn.entities.DriverAccountUpdateInfoRequest;
import com.hopin.HopIn.entities.DriverAccountUpdatePasswordRequest;
import com.hopin.HopIn.entities.DriverAccountUpdateVehicleRequest;
import com.hopin.HopIn.entities.Vehicle;
import com.hopin.HopIn.entities.WorkingHours;
import com.hopin.HopIn.enums.DocumentOperationType;
import com.hopin.HopIn.enums.Role;
import com.hopin.HopIn.enums.VehicleTypeName;
import com.hopin.HopIn.repositories.DocumentRepository;
import com.hopin.HopIn.repositories.DriverRepository;
import com.hopin.HopIn.repositories.VehicleRepository;
import com.hopin.HopIn.services.interfaces.IDriverService;

@Service
public class DriverServiceImpl implements IDriverService {

	@Autowired
	private DriverRepository allDrivers;
	
	@Autowired
	private VehicleRepository allVehicles;
	
	@Autowired
	private DocumentRepository allDocuments;
	
	private Map<Integer, Driver> allDriversMap = new HashMap<Integer, Driver>();
	private int currId = 1;
	private int currDocId = 1;
	private int currVehicleId = 1;
	private int currHoursId = 1;

	@Override
	public UserReturnedDTO insert(UserDTOOld dto) {
		Driver driver = dtoToDriver(dto, null);
		driver.setRole(Role.DRIVER);
		this.allDrivers.save(driver);
		this.allDrivers.flush();
		return new UserReturnedDTO(driver);
	}
	
	@Override
	public AllUsersDTO getAllPaginated(Pageable pageable) {
		if (allDriversMap.size() == 0) {
			Driver driver = new Driver(0, "Pera", "Peric", "pera.peric@email.com", "123", "Bulevar Oslobodjenja 74", "+381123123", "U3dhZ2dlciByb2Nrcw==".getBytes());
			allDriversMap.put(driver.getId(), driver);
		}
		
		return new AllUsersDTO(this.allDriversMap.values());
	}

	@Override
	public DriverReturnedDTO getById(int id) {
		Optional<Driver> found = allDrivers.findById(id);
		if (found.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
		}
		return new DriverReturnedDTO(found.get(), found.get().getVehicle());
	}
	
	@Override 
	public Driver getDriver(int id) {
		Optional<Driver> found = allDrivers.findById(id);
		if (found.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
		}
		return found.get();
	}
	
	@Override
	public UserReturnedDTO update(int id, UserDTOOld dto) {
		Optional<Driver> driver = allDrivers.findById(id);
		if (driver.isEmpty()) {
			return null;
		}
		if (dto.getNewPassword() != "" && dto.getNewPassword() != null) {
			if (!this.checkPasswordMatch(driver.get().getPassword(), dto.getPassword())) {
				return null;	
			}
			dto.setPassword(dto.getNewPassword());
		}
		driver.get().copy(dto);
		this.allDrivers.save(driver.get());
		this.allDrivers.flush();
		return new UserReturnedDTO(driver.get());
	}
	
	private boolean checkPasswordMatch(String password, String subbmitedPassword) {
		return password.equals(subbmitedPassword);
	}
	
	@Override
	public AllUsersDTO getAll(int page, int size) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DocumentReturnedDTO> getDocuments(int driverId) {
		Optional<Driver> driver = allDrivers.findById(driverId);
		if (driver.isEmpty()){
			return null;
		}
		List<DocumentReturnedDTO> documents = new ArrayList<DocumentReturnedDTO>();
		driver.get().getDocuments().forEach((document) -> {documents.add(new DocumentReturnedDTO(document));});
		return documents;
	}

	@Override
	public DocumentReturnedDTO addDocument(int driverId, DocumentDTO documentDTO) {
		Optional<Driver> driver = allDrivers.findById(driverId);
		if (driver.isEmpty()){
			return null;
		}
		Document document = new Document();
		this.dtoToDocument(documentDTO, document);
		document.setDriverId(driverId);
		driver.get().getDocuments().add(document);
		
		allDrivers.save(driver.get());
		allDrivers.flush();
		
		return new DocumentReturnedDTO(document);
	}

	@Override
	public VehicleDTO getVehicle(int driverId) {
		Optional<Driver> driver = allDrivers.findById(driverId);
		if (driver.isEmpty()){
			return null;
		}
		VehicleDTO vehicleDTO = new VehicleDTO(driver.get().getVehicle());
		return vehicleDTO;
	}

	@Override
	public VehicleReturnedDTO insertVehicle(int driverId, VehicleDTO dto) {
		Optional<Driver> driver = allDrivers.findById(driverId);
		if (driver.isEmpty()){
			return null;
		}
		
		Vehicle vehicle = new Vehicle();
		dtoToVehicle(dto, driverId, vehicle);
		driver.get().setVehicle(vehicle);
		allDrivers.save(driver.get());
		allDrivers.flush();
		
		return new VehicleReturnedDTO(driver.get().getVehicle());
	}

	@Override
	public VehicleReturnedDTO updateVehicle(int driverId, VehicleDTO dto) {
		Optional<Driver> driver = allDrivers.findById(driverId);
		if (driver.isEmpty()){
			return null;
		}
		Vehicle vehicle = driver.get().getVehicle();
		dtoToVehicle(dto, driverId, vehicle);
		allDrivers.save(driver.get());
		allDrivers.flush();
		
		return new VehicleReturnedDTO(driver.get().getVehicle());
	}
	
	@Override
	public AllHoursDTO getAllHours(int id, int page, int size, String from, String to) {
//		return new AllHoursDTO(1, new ArrayList<WorkingHours>() {
//            {
//                add(new WorkingHours(0, LocalDateTime.now(), LocalDateTime.now(), 0));
//            }
//        });
		return null;
	}
	
	@Override
	public WorkingHoursDTO getWorkingHours(int hoursId) {
//		return new WorkingHoursDTO(new WorkingHours(hoursId, LocalDateTime.now(), LocalDateTime.now(), 0));
		return null;
	}
	
	@Override
	public WorkingHoursDTO addWorkingHours(int driverId, WorkingHoursDTO hours) {
//		Driver driver = this.allDriversMap.get(driverId);
//		WorkingHours newHours = new WorkingHours(currHoursId++, hours.getStart(), hours.getEnd(), driverId);
//		driver.getWorkingHours().add(newHours);
//		
//		System.out.println(new Date());
//		
//		return new WorkingHoursDTO(newHours);
		
		return null;
	}
	
	
	@Override
	public WorkingHoursDTO updateWorkingHours(int hoursId, WorkingHoursDTO hours) {
		// vidi ovde kako bi zapravo sa repo bilo
//		return new WorkingHoursDTO(new WorkingHours(hoursId, hours.getStart(), hours.getEnd(), hoursId));
		return null;
	}

	
	@Override
	public AllUserRidesReturnedDTO getAllRides(int driverId, int page, int size, String sort, String from, String to) {
		return new AllUserRidesReturnedDTO();
	}
	
	
	private Vehicle dtoToVehicle(VehicleDTO dto, int driverId, Vehicle vehicle) {
		vehicle.setDriverId(driverId);
		vehicle.setModel(dto.getModel());
		vehicle.setLicenseNumber(dto.getLicenseNumber());
		//vehicle.setCurrentLocation(dto.getCurrentLocation());
		vehicle.setPassengerSeats(dto.getPassengerSeats());
		vehicle.setBabyTransport(dto.isBabyTransport());
		vehicle.setPetTransport(dto.isPetTransport());
		if (dto.getVehicleType().equals(VehicleTypeName.CAR)) {
			vehicle.getVehicleType().setName(VehicleTypeName.CAR);
		} else if (dto.getVehicleType().equals(VehicleTypeName.VAN)) {
			vehicle.getVehicleType().setName(VehicleTypeName.VAN);
		} else {
			vehicle.getVehicleType().setName(VehicleTypeName.LUXURY);
		}

		return vehicle;
	}

	private Document dtoToDocument(DocumentDTO dto, Document document) {
		document.setName(dto.getName());
		document.setDocumentImage(dto.getDocumentImage());

		return document;

	}

	private Driver dtoToDriver(UserDTOOld dto, Driver driver) {
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
	
	@Override
	public void updateByInfoRequest(DriverAccountUpdateInfoRequest request) {
		Driver driver = request.getDriver();
		driver.setInfoByRequest(request);
		
		this.allDrivers.save(driver);
		this.allDrivers.flush();
	}
	
	@Override
	public void updateByPasswordRequest(DriverAccountUpdatePasswordRequest request) {
		Driver driver = request.getDriver();
		if (request.getNewPassword() != "" && request.getNewPassword() != null) {
			if (!this.checkPasswordMatch(driver.getPassword(), request.getOldPassword())) {
				return;	
			}
			driver.setPassword(request.getNewPassword());
		}
		this.allDrivers.save(driver);
		this.allDrivers.flush();
		
	}
	
	@Override
	public void updateByVehicleRequest(DriverAccountUpdateVehicleRequest request) {
		Driver driver = request.getDriver();
		Vehicle vehicle = request.getDriver().getVehicle();
		vehicle.setInfoByRequest(request);
		this.allDrivers.save(driver);
		this.allDrivers.flush();
	}
	
	@Override
	public void updateByDocumentRequest(DriverAccountUpdateDocumentRequest request) {
		Driver driver = request.getDriver();
		if (request.getDocumentOperationType() == DocumentOperationType.ADD) {
			Document document = new Document();
			document.setName(request.getName());
			document.setDocumentImage(request.getDocumentImage());
			document.setDriverId(driver.getId());
			driver.getDocuments().add(document);	
		} else {
			if (request.getDocumentOperationType() == DocumentOperationType.UPDATE) {
				Document document = this.getDocumentById(driver.getDocuments(), request.getDocumentId());
				document.setName(request.getName());
				document.setDocumentImage(request.getDocumentImage());
			} else {
				if (request.getDocumentOperationType() == DocumentOperationType.DELETE) {
					Document document = this.getDocumentById(driver.getDocuments(), request.getDocumentId());
					driver.getDocuments().remove(document);
					this.allDocuments.delete(document);
				}
			}
		}
	
		this.allDrivers.save(driver);
		this.allDrivers.flush();
	}

	private Document getDocumentById(Set<Document> documents, int id) {
		for (Document elem: documents) {
			if (elem.getId() == id) {
				return elem;
			}
		}
		
		return null;
	}
	
	public List<Driver> getActiveDrivers(){
		List<Driver> drivers = this.allDrivers.findByIsActive(true);
		return drivers;
	}

	@Override
	public void setDriverActivity(int driverId, boolean isActive) {
		Driver driver = this.getDriver(driverId);
		driver.setActive(isActive);
		this.allDrivers.save(driver);
		this.allDrivers.flush();
	}

	@Override
	public boolean isDriverVehicleAppropriateForRide(int id, RideDTO rideDTO) {
		Driver driver = this.getDriver(id);
		Vehicle vehicle = driver.getVehicle();
		if (vehicle.isBabyTransport() != rideDTO.isBabyTransport() ||
				vehicle.isPetTransport() != rideDTO.isPetTransport() ||
				vehicle.getPassengerSeats() < rideDTO.getPassengers().size() ||
				vehicle.getVehicleType().getName() != rideDTO.getVehicleType())
			return false;
		return true;
	}

	@Override
	public List<Driver> getDriversWithAppropriateVehicleForRide(List<Driver> activeDrivers, RideDTO rideDTO) {
		List<Driver> driversWithAppropriateVehicle = new ArrayList<Driver>();
		for (Driver driver: activeDrivers) {
			if (this.isDriverVehicleAppropriateForRide(driver.getId(), rideDTO))
				driversWithAppropriateVehicle.add(driver);
		}
		return driversWithAppropriateVehicle;
	}
}
