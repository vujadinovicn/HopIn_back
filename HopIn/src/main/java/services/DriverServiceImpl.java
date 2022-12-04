package services;

import java.util.HashMap;
import java.util.Map;

import dtos.UserDTO;
import entities.Driver;
import service.interfaces.IDriverService;

public class DriverServiceImpl implements IDriverService {

	private Map<Integer, Driver> allDrivers = new HashMap<Integer, Driver>();
	private int currId = 0;
	
	@Override
	public Driver insert(UserDTO dto) {
		Driver driver = dtoToDriver(dto);
		driver.setId(currId);
		allDrivers.put(currId, driver);
		
		return driver;
	}

	
	private Driver dtoToDriver(UserDTO dto) {
		Driver driver = new Driver();
		driver.setName(dto.getName());
		driver.setSurname(dto.getSurname());
		driver.setEmail(dto.getEmail());
		driver.setAddress(dto.getAddress());
		driver.setPhone(dto.getPhone());
		driver.setPassword(dto.getPassword());
		
		return driver;
	}
}
