package com.hopin.HopIn.services;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hopin.HopIn.dtos.WorkingHoursDTO;
import com.hopin.HopIn.dtos.WorkingHoursEndDTO;
import com.hopin.HopIn.dtos.WorkingHoursStartDTO;
import com.hopin.HopIn.entities.Driver;
import com.hopin.HopIn.entities.WorkingHours;
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
	public WorkingHoursDTO addWorkingHours(int driverId, WorkingHoursStartDTO dto) {
		WorkingHours workingHours = new WorkingHours(driverId, dto);
		this.allWorkingHours.save(workingHours);
		this.allWorkingHours.flush();
		
		this.driverService.setDriverActivity(driverId, true);
		
		return new WorkingHoursDTO(workingHours);
	}
	
	@Override
	public WorkingHoursDTO updateWorkingHours(int id, WorkingHoursEndDTO dto) {
		Optional<WorkingHours> found = this.allWorkingHours.findById(id);
		if (found.isEmpty() || found.get().getStart().isAfter(dto.getEnd())) {
			return null;
		}
		found.get().setEnd(dto.getEnd());
		this.allWorkingHours.save(found.get());
		this.allWorkingHours.flush();
		
		int driverId = found.get().getDriverId();
		this.driverService.setDriverActivity(driverId, false);
		
		return new WorkingHoursDTO(found.get());
	}
	
	@Override
	public double getWorkedHoursForDate(int driverId, LocalDate date) {
		List<WorkingHours> allWorkingHours = this.allWorkingHours.findByDriverAndStart(driverId, date);
		double hours = 0;
		
		for (WorkingHours workingHours : allWorkingHours) {
			long minutes= Duration.between(workingHours.getStart(), workingHours.getEnd()).toMinutes();
			hours += minutes / 60;
			hours += (minutes % 60) * 0.1;
		}
		return hours;
	}
}
