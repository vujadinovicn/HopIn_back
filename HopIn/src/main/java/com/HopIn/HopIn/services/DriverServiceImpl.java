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
		Driver driver = dtoToDriver(dto);
		driver.setId(currId);
		this.allDrivers.put(currId, driver);
		
		return new UserReturnedDTO(driver);
	}
	
	@Override
	public UserReturnedDTO getById(int id) {
		return new UserReturnedDTO(this.allDrivers.get(id));
	}

	
	private Driver dtoToDriver(UserDTO dto) {
		Driver driver = new Driver();
		driver.setName(dto.getName());
		driver.setSurname(dto.getSurname());
		driver.setEmail(dto.getEmail());
		driver.setAddress(dto.getAddress());
		driver.setTelephoneNumber(dto.getPhone());
		driver.setPassword(dto.getPassword());
		driver.setProfilePicture(dto.getProfilePicture());
		
		return driver;
	}


	
}
