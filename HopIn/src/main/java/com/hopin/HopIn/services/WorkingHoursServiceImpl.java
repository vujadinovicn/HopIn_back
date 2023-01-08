package com.hopin.HopIn.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hopin.HopIn.dtos.WorkingHoursDTO;
import com.hopin.HopIn.dtos.WorkingHoursEndDTO;
import com.hopin.HopIn.dtos.WorkingHoursStartDTO;
import com.hopin.HopIn.entities.WorkingHours;
import com.hopin.HopIn.repositories.WorkingHoursRepository;
import com.hopin.HopIn.services.interfaces.IWorkingHoursService;

@Service
public class WorkingHoursServiceImpl implements IWorkingHoursService {
	
	@Autowired
	WorkingHoursRepository allWorkingHours;
	
	@Override
	public WorkingHoursDTO addWorkingHours(int driverId, WorkingHoursStartDTO dto) {
		WorkingHours workingHours = new WorkingHours(driverId, dto);
		this.allWorkingHours.save(workingHours);
		this.allWorkingHours.flush();
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
		return new WorkingHoursDTO(found.get());
	}

}
