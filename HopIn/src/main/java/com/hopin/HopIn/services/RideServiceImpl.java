package com.hopin.HopIn.services;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.hopin.HopIn.dtos.AllPanicRidesDTO;
import com.hopin.HopIn.dtos.AllPassengerRidesDTO;
import com.hopin.HopIn.dtos.LocationNoIdDTO;
import com.hopin.HopIn.dtos.PanicRideDTO;
import com.hopin.HopIn.dtos.ReasonDTO;
import com.hopin.HopIn.dtos.RideDTO;
import com.hopin.HopIn.dtos.RideForReportDTO;
import com.hopin.HopIn.dtos.RideReturnedDTO;
import com.hopin.HopIn.dtos.RideReturnedWithRejectionDTO;
import com.hopin.HopIn.dtos.UnregisteredRideSuggestionDTO;
import com.hopin.HopIn.dtos.UserInRideDTO;
import com.hopin.HopIn.entities.Driver;
import com.hopin.HopIn.entities.Ride;
import com.hopin.HopIn.entities.VehicleType;
import com.hopin.HopIn.enums.RideStatus;
import com.hopin.HopIn.enums.VehicleTypeName;
import com.hopin.HopIn.exceptions.NoActiveDriverException;
import com.hopin.HopIn.exceptions.NoAvailableDriversException;
import com.hopin.HopIn.exceptions.NoDriverWithAppropriateVehicleForRideException;
import com.hopin.HopIn.exceptions.PassengerAlreadyInRideException;
import com.hopin.HopIn.repositories.RideRepository;
import com.hopin.HopIn.repositories.VehicleTypeRepository;
import com.hopin.HopIn.services.interfaces.IDriverService;
import com.hopin.HopIn.services.interfaces.IRideEstimationService;
import com.hopin.HopIn.services.interfaces.IRideService;
import com.hopin.HopIn.services.interfaces.IWorkingHoursService;

@Service
public class RideServiceImpl implements IRideService {
	
	@Autowired
	private RideRepository allRides;
	
	@Autowired
	private VehicleTypeRepository allVehicleTypes;
	
	@Autowired 
	private IDriverService driverService;
	
	@Autowired
	private IRideEstimationService rideEstimationService;
	
	@Autowired 
	private IWorkingHoursService workingHoursService;
	
	private Map<Integer, Ride> allRidess = new HashMap<Integer, Ride>();
	private Set<PanicRideDTO> allPanicRides = new HashSet<PanicRideDTO>();
	private int currId = 0;
	
	@Override
	public List<RideForReportDTO> getAllPassengerRidesBetweenDates(int id, String from, String to) {
		DateTimeFormatter formatter = DateTimeFormatter. ofPattern("yyyy/MM/dd"); 
		List<Ride> rides = allRides.getAllPassengerRidesBetweenDates(id, LocalDate.parse(from, formatter).atStartOfDay(), LocalDate.parse(to, formatter).atStartOfDay());
		List<RideForReportDTO> res = new ArrayList<RideForReportDTO>();
		for(Ride ride: rides) {
			res.add(new RideForReportDTO(ride));
		}
		return res;
	}
	
	public List<RideForReportDTO> getAllDriverRidesBetweenDates(int id, String from, String to) {
		DateTimeFormatter formatter = DateTimeFormatter. ofPattern("yyyy/MM/dd"); 
		List<Ride> rides = allRides.getAllDriverRidesBetweenDates(id, LocalDate.parse(from, formatter).atStartOfDay(), LocalDate.parse(to, formatter).atStartOfDay());
		List<RideForReportDTO> res = new ArrayList<RideForReportDTO>();
		for(Ride ride: rides) {
			res.add(new RideForReportDTO(ride));
		}
		return res;
	}
	
	
//	public RideServiceImpl() {
//		List<LocationNoIdDTO> locs = new ArrayList<LocationNoIdDTO>();
//		locs.add(new LocationNoIdDTO("Bulevar oslobodjenja 46", 45.267136, 19.833549));
//		locs.add(new LocationNoIdDTO("Bulevar oslobodjenja 46", 45.267136, 19.833549));
//
//		List<UserInRideDTO> passengers = new ArrayList<UserInRideDTO>();
//		passengers.add(new UserInRideDTO(1, "mika@gmail.com"));
//		
//		Ride ride = new Ride(++this.currId, LocalDateTime.now().minusHours(1), LocalDateTime.now().plusHours(1), 2000.0, 5,
//			RideStatus.REJECTED, false, false, VehicleTypeName.STANDARDNO, null,
//			new RejectionNotice("Odbijaaaam!"), passengers, locs, new UserInRideDTO(1, "driver@gmail.com"));
//		
//		this.allRides.put(1, ride);
//	}
	
//	@Override
//	public RideReturnedDTO create(RideDTO dto) {
//		Ride ride = new Ride(dto, ++this.currId);
//		this.allRides.put(ride.getId(), ride);
//		return new RideReturnedDTO(ride);
//	}
	
//	@Override
//	public RideReturnedDTO getActiveRideForDriver(int id) {
//		for(Ride ride : this.allRides.values()) {
//			if(ride.getStartTime().isBefore(LocalDateTime.now()) && ride.getEndTime().isAfter(LocalDateTime.now()) 
//					&& ride.getDriver().getId() == id) {
//				return new RideReturnedDTO(ride);
//			}
//		}
//		return null;
//	}
	
//	@Override
//	public RideReturnedDTO getActiveRideForPassenger(int id) {
//		for(Ride ride : this.allRides.values()) {
//			if(ride.getStartTime().isBefore(LocalDateTime.now()) && ride.getEndTime().isAfter(LocalDateTime.now())) {
//				for(UserInRideDTO passenger : ride.getPassengers()) {
//					if(passenger.getId() == id) {
//						return new RideReturnedDTO(ride);
//					}
//				}
//			}
//		}
//		return null;
//	}
	
//	@Override
//	public RideReturnedWithRejectionDTO getRide(int id) {
//		for(int key: this.allRides.keySet()) {
//			if (key == id) {
//				return new RideReturnedWithRejectionDTO(this.allRides.get(key));
//			}
//		}
//		return null;
//	}
//	
//	@Override
//	public RideReturnedDTO cancelRide(int id) {
//		for (int key : this.allRides.keySet()) {
//			if (key == id) {
//				this.allRides.get(key).setStatus(RideStatus.CANCELED);;
//				return new RideReturnedDTO(this.allRides.get(key));
//			}
//		}
//		return null;
//	}
//	
//	@Override
//	public PanicRide panicRide(int id, ReasonDTO reason) {
//		Ride ride = this.allRides.get(id);
//		if(ride != null) {
//			PanicRide panicRide = new PanicRide(new RideReturnedDTO(ride), reason.getReason());
//			this.allPanicRides.add(panicRide);
//			return panicRide;
//		}
//		return null;
//	}
//	
//	@Override
//	public RideReturnedDTO changeRideStatus(int id, RideStatus status) {
//		Ride ride = this.allRides.get(id);
//		if (ride != null) {
//			ride.setStatus(status);
//			return new RideReturnedDTO(ride);
//		}
//		return null;
//	}
//	
//	@Override
//	public RideReturnedDTO rejectRide(int id, ReasonDTO reason) {
//		Ride ride = this.allRides.get(id);
//		if (ride != null) {
//			ride.setStatus(RideStatus.REJECTED);
//			ride.setRejectionNotice(new RejectionNotice(reason.getReason()));
//			return new RideReturnedDTO(ride);
//		}
//		return null;
//	}
//	
//	@Override
//	public AllPanicRidesDTO getAllPanicRides() {
//		return new AllPanicRidesDTO(this.allPanicRides);
//	}
//	


	@Override
	public RideReturnedDTO add(RideDTO dto){
		List<Driver> availableDrivers = new ArrayList<Driver>();
		Driver driverForRide = new Driver();
		
		List<Driver> driversForRide = new ArrayList<Driver>();
		
		driversForRide = this.driverService.getActiveDrivers();
		if (driversForRide.size() == 0) {
			throw new NoActiveDriverException();
		}
		
		driversForRide = this.driverService.getDriversWithAppropriateVehicleForRide(driversForRide, dto);
		if (driversForRide.size() == 0) {
			throw new NoDriverWithAppropriateVehicleForRideException();
		}
		
		dto.getPassengers().forEach((UserInRideDTO passenger) -> {
			if (this.getActiveRideForPassenger(passenger.getId()) != null)
				{
				System.out.println(passenger.getId());
				throw new PassengerAlreadyInRideException();
				}
		});
		
		int newRideDuration = this.rideEstimationService.getEstimatedTime(dto.getDepartureLocation(), dto.getDestinationLocation());
		System.out.println("newrideduration" + newRideDuration);
		
		List<Driver> driversWithNoActiveRide = this.getAllDriversWithNoActiveRide(driversForRide);
		List<Driver> driversWithActiveRide = new ArrayList<Driver>(driversForRide);
		driversWithActiveRide.removeAll(driversWithNoActiveRide);
		
		boolean availabilityOfDrivers = driversWithNoActiveRide.size() != 0 ? true : false;
		
		if (availabilityOfDrivers) {
			driverForRide = this.getBestDriver(dto, driversWithNoActiveRide, newRideDuration, availabilityOfDrivers);
		} else {
			driverForRide = this.getBestDriver(dto, driversWithActiveRide, newRideDuration, availabilityOfDrivers);
		}
		
		if (driverForRide.getId() == 0)
			throw new NoAvailableDriversException();
		
		System.out.println(driverForRide.getId());
		return null;
	}
	
	private Driver getBestDriver(RideDTO rideDTO, List<Driver> drivers, int newRideDuration, boolean availability) {
		Driver foundDriver = new Driver();
		int bestTime = Integer.MAX_VALUE;
		
		for (Driver driver: drivers) {
			//LocalDateTime rideStart = rideDTO.getTime();
			LocalDateTime startOfDrivingToDeparture = LocalDateTime.now();
			
			int timeForNewRideDepartureArrival = 0;
			
			if (availability)
				timeForNewRideDepartureArrival = this.rideEstimationService.getEstimatedTime(rideDTO.getDepartureLocation(), driver.getVehicleLocation());
			else {
				RideReturnedDTO currentRide = this.getActiveRideForDriver(driver.getId());
				startOfDrivingToDeparture = currentRide.getStartTime().plusMinutes(currentRide.getEstimatedTimeInMinutes());
				timeForNewRideDepartureArrival = this.rideEstimationService.getEstimatedTime(rideDTO.getDepartureLocation(), currentRide.getLocations().get(0).getDestination());
			}
			
			Ride nextRide = this.allRides.getFirstUpcomingRideForDriver(driver.getId());
			
			int timeFromStartOfNewToStartOfNext = timeForNewRideDepartureArrival + newRideDuration;
			
			int timeForUpcomingRideDepartureArrival = 0;
			if (nextRide != null) {
				timeForUpcomingRideDepartureArrival= this.rideEstimationService.getEstimatedTime(rideDTO.getDepartureLocation(), new LocationNoIdDTO(nextRide.getDepartureLocation()));
				timeFromStartOfNewToStartOfNext += timeForUpcomingRideDepartureArrival;
				
				if (startOfDrivingToDeparture.plusMinutes(timeFromStartOfNewToStartOfNext).isAfter(nextRide.getStartTime())) {
					System.out.println("ne moze ovaj driver" + driver.getId());
					continue;
				}
			}
			int totalTimeForDepartureArrival = timeForNewRideDepartureArrival;
			if (!availability)
				totalTimeForDepartureArrival += this.getMinutesUntilEndOfCurrentRideForDriver(driver);
			if (totalTimeForDepartureArrival < bestTime) {
				foundDriver = driver;
				bestTime = (int) (totalTimeForDepartureArrival);
			}
	//			double workingHoursForCurrentDay = this.workingHoursService.getWorkedHoursForCurrentDay(driver.getId());
	//			double workingHoursWithNewRide = this.workingHoursService.getWorkedHoursForDateWithNewRide(workingHoursForCurrentDay, timeFromStartOfNewToStartOfNext);
	//			if (workingHoursWithNewRide <= 8) {
	//				availableDrivers.add(driver);
	//			}
				//availableDrivers.add(driver);
		}
		return foundDriver;
	}
	
	private double getMinutesUntilEndOfCurrentRideForDriver(Driver driver) {
		RideReturnedDTO currentRide = this.getActiveRideForDriver(driver.getId());
		LocalDateTime now = LocalDateTime.now();
		return ChronoUnit.MINUTES.between(now, currentRide.getStartTime().plusMinutes(currentRide.getEstimatedTimeInMinutes()));
	}
	
	@Override
	public RideReturnedDTO getActiveRideForPassenger(int id) {
		LocalDateTime now = LocalDateTime.now();
		Ride activeRide = this.allRides.getActiveRideForPassenger(id, now);
		if (activeRide == null)
			return null;
		return new RideReturnedDTO(activeRide);
	}

	@Override
	public RideReturnedDTO getActiveRideForDriver(int id) {
		LocalDateTime now = LocalDateTime.now();
		Ride activeRide = this.allRides.getActiveRideForDriver(id, now);
		if (activeRide == null)
			return null;
		return new RideReturnedDTO(activeRide);
	}
	
	private List<RideReturnedDTO> getAllActiveRidesForDrivers(List<Driver> drivers){
		List<RideReturnedDTO> rides = new ArrayList<RideReturnedDTO>();
		for (Driver driver: drivers) {
			rides.add(this.getActiveRideForDriver(driver.getId()));
		}
		return rides;
	}
	
	private List<Driver> getAllDriversWithNoActiveRide(List<Driver> drivers){
		List<Driver> availableDrivers = new ArrayList<Driver>();
		for (Driver driver: drivers) {
			if (this.getActiveRideForDriver(driver.getId()) == null) 
				availableDrivers.add(driver);
		}
		
		return availableDrivers;
	}

	@Override
	public RideReturnedWithRejectionDTO getRide(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RideReturnedDTO cancelRide(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PanicRideDTO panicRide(int id, ReasonDTO reason) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RideReturnedDTO changeRideStatus(int id, RideStatus status) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RideReturnedDTO rejectRide(int id, ReasonDTO reason) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AllPanicRidesDTO getAllPanicRides() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AllPassengerRidesDTO getAllPassengerRides(int id, int page, int size, String sort, String from, String to) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Double getRideSugestionPrice(UnregisteredRideSuggestionDTO dto) {
		VehicleType vehicleType = this.allVehicleTypes.getByName(VehicleTypeName.valueOf(VehicleTypeName.class, dto.getVehicleTypeName()));
		return vehicleType.getPricePerKm() * dto.getDistance();
	}
	
}
