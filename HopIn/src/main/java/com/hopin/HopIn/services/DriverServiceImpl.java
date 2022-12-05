package com.hopin.HopIn.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.text.Document;

import org.springframework.stereotype.Service;

import com.hopin.HopIn.dtos.AllUsersDTO;
import com.hopin.HopIn.dtos.UserDTO;
import com.hopin.HopIn.dtos.UserReturnedDTO;
import com.hopin.HopIn.entities.Driver;
import com.hopin.HopIn.services.interfaces.IDriverService;

@Service
public class DriverServiceImpl implements IDriverService {

	private Map<Integer, Driver> allDrivers = new HashMap<Integer, Driver>();
	private int currId = 0;
	private int currDocId = 0;
	
	@Override
	public UserReturnedDTO insert(UserDTO dto) {
		Driver driver = dtoToDriver(dto, null);
		driver.setId(currId);
		this.allDrivers.put(currId++, driver);
		
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
	
	@Override
	public AllUsersDTO getAll(int page, int size) {
		// TODO Auto-generated method stub
		return null;
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