package com.hopin.HopIn.services.interfaces;

import java.time.LocalDate;

import com.hopin.HopIn.dtos.WorkingHoursDTO;
import com.hopin.HopIn.dtos.WorkingHoursEndDTO;
import com.hopin.HopIn.dtos.WorkingHoursStartDTO;

public interface IWorkingHoursService {

	WorkingHoursDTO addWorkingHours(int driverId, WorkingHoursStartDTO dto);

	WorkingHoursDTO updateWorkingHours(int id, WorkingHoursEndDTO dto);

	double getWorkedHoursForDate(int driverId, LocalDate date);

}
