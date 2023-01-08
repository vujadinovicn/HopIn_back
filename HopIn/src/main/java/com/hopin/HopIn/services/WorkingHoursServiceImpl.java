package com.hopin.HopIn.services;

import com.hopin.HopIn.dtos.WorkingHoursDTO;
import com.hopin.HopIn.dtos.WorkingHoursStartDTO;
import com.hopin.HopIn.entities.WorkingHours;
import com.hopin.HopIn.repositories.WorkingHoursRepository;
import com.hopin.HopIn.services.interfaces.IWorkingHoursService;

public class WorkingHoursServiceImpl implements IWorkingHoursService {
	
	WorkingHoursRepository allWorkingHours;
	
	@Override
	public WorkingHoursDTO addWorkingHours(int driverId, WorkingHoursStartDTO dto) {
		WorkingHours workingHours = new WorkingHours(driverId, dto);
		this.allWorkingHours.save(workingHours);
		this.allWorkingHours.flush();
		return new WorkingHoursDTO(workingHours);
	}

}
