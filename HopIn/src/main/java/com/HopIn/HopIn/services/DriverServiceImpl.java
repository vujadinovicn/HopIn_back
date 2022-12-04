package com.HopIn.HopIn.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.HopIn.HopIn.dtos.UserDTO;
import com.HopIn.HopIn.dtos.UserReturnedDTO;
import com.HopIn.HopIn.entities.Driver;
import com.HopIn.HopIn.services.interfaces.IDriverService;

@Service
public class DriverServiceImpl implements IDriverService {

	private Map<Integer, Driver> allDrivers = new HashMap<Integer, Driver>();
	private int currId = 0;
	
	@Override
	public UserReturnedDTO insert(UserDTO dto) {
		Driver driver = dtoToDriver(dto, null);
		driver.setId(currId);
		this.allDrivers.put(currId, driver);
		
		return new UserReturnedDTO(driver);
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
