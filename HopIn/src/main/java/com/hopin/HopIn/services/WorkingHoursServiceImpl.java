package com.hopin.HopIn.services;

import java.text.DecimalFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hopin.HopIn.dtos.WorkingHoursDTO;
import com.hopin.HopIn.dtos.WorkingHoursEndDTO;
import com.hopin.HopIn.dtos.WorkingHoursStartDTO;
import com.hopin.HopIn.entities.Driver;
import com.hopin.HopIn.entities.WorkingHours;
import com.hopin.HopIn.exceptions.BadDateTimeFormatException;
import com.hopin.HopIn.exceptions.BadIdFormatException;
import com.hopin.HopIn.exceptions.DriverAlreadyActiveException;
import com.hopin.HopIn.exceptions.NoActiveDriverException;
import com.hopin.HopIn.exceptions.NoVehicleException;
import com.hopin.HopIn.exceptions.WorkingHoursException;
import com.hopin.HopIn.repositories.WorkingHoursRepository;
import com.hopin.HopIn.services.interfaces.IDriverService;
import com.hopin.HopIn.services.interfaces.IWorkingHoursService;

@Service
public class WorkingHoursServiceImpl implements IWorkingHoursService {
	
	@Autowired
	WorkingHoursRepository allWorkingHours;
	
	@Autowired
	private IDriverService driverService;
	
	@Override 
	public WorkingHoursDTO getWorkingHoursById(int id) {
		Optional<WorkingHours> found = allWorkingHours.findById(id);
		
		if (found.isEmpty()) {
			throw new WorkingHoursException();
		}
		
		return new WorkingHoursDTO(found.get());
	}
	
	@Override
	public WorkingHoursDTO addWorkingHours(int driverId, WorkingHoursStartDTO dto) {
		Driver driver = this.driverService.getDriver(driverId);
		if (driver.isActive()) {
			throw new DriverAlreadyActiveException();
		} else if (this.getWorkedHoursForToday(driverId, dto.getStart()) > 8) {
			throw new WorkingHoursException(); 
		} 
//		else if (driver.getVehicle() == null) {
//			throw new NoVehicleException();
//		}
		
		WorkingHours workingHours = new WorkingHours(driverId, dto);
		this.allWorkingHours.save(workingHours);
		this.allWorkingHours.flush();
		
		this.driverService.setDriverActivity(driverId, true);
		
		return new WorkingHoursDTO(workingHours);
	}
	
	@Override
	public WorkingHoursDTO updateWorkingHours(int id, WorkingHoursEndDTO dto) {
		Optional<WorkingHours> found = this.allWorkingHours.findById(id);
		if (found.isEmpty()) {
			throw new BadIdFormatException();
		} 
//		else if (found.get().getStart().isAfter(dto.getEnd())) {
//			throw new BadDateTimeFormatException();
//		}
		
		Driver driver = this.driverService.getDriver(found.get().getDriverId());
		if (!driver.isActive()) {
			throw new NoActiveDriverException();
		} 
//		else if (driver.getVehicle() == null) {
//			throw new NoVehicleException();
//		}
		
		found.get().setEnd(dto.getEnd());
		this.allWorkingHours.save(found.get());
		this.allWorkingHours.flush();
		
		int driverId = found.get().getDriverId();
		this.driverService.setDriverActivity(driverId, false);
		
		return new WorkingHoursDTO(found.get());
	}
	
	@Override
	public double getWorkedHoursForToday(int driverId, LocalDateTime end) {
		LocalDate date = LocalDate.now();
		LocalDateTime start = date.atStartOfDay();
		List<WorkingHours> allWorkingHours = this.allWorkingHours.findByDriverAndDates(driverId, start, date);
		
		double hours = 0;
		DecimalFormat df = new DecimalFormat("#.##");
		for (WorkingHours workingHours : allWorkingHours) {
			long minutes= Duration.between(workingHours.getStart(), workingHours.getEnd()).toMinutes();
			if (start.isAfter(workingHours.getStart())) {
				minutes = Duration.between(start, workingHours.getEnd()).toMinutes();
			}
			hours += minutes / 60;
			if ((minutes % 60) < 10) {
				hours += (minutes%60) * 0.1;
			} else {
				hours += (minutes%60) * 0.01;
			}
		}
		return Double.valueOf(df.format(hours));
	}
	
	
	
	@Override
	public double getWorkedHoursForTodayWithNewRide(int driverId, int rideMinutes) {
		double hours = getWorkedHoursForToday(driverId, LocalDateTime.now()) + (rideMinutes/60);
		DecimalFormat df = new DecimalFormat("#.##");
		return Double.valueOf(df.format(hours));
	}
}
