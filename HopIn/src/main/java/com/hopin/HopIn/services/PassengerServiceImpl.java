package com.hopin.HopIn.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hopin.HopIn.dtos.AllUsersDTO;
import com.hopin.HopIn.dtos.UserDTO;
import com.hopin.HopIn.dtos.UserReturnedDTO;
import com.hopin.HopIn.entities.Passenger;
import com.hopin.HopIn.entities.User;
import com.hopin.HopIn.repositories.PassengerRepository;
import com.hopin.HopIn.services.interfaces.IPassengerService;

@Service
public class PassengerServiceImpl implements IPassengerService {
	
	@Autowired
	private PassengerRepository allPassengers;
	
	private Map<Integer, User> allPassengerss= new HashMap<Integer, User>();
	private int currId = 0;
	
	public PassengerServiceImpl() {
		User passenger = new User(++this.currId, "Mika", "Mikic", "mika@gmail.com", "123", "Bulevar Oslobodjenja 7", "065454454",
				"U3dhZ2dlciByb2Nrcw==");
		this.allPassengerss.put(this.currId, passenger);
			}
	
	@Override
	public AllUsersDTO getAll() {
		return new AllUsersDTO(this.allPassengerss);
	}
	
	@Override
	public UserReturnedDTO getPassenger(int id) {
		User passenger = this.allPassengerss.get(id);
		if (passenger == null) { return null; }
		return new UserReturnedDTO(this.allPassengerss.get(id));
	}
	
	@Override
	public UserReturnedDTO insert(UserDTO dto) {
		Passenger passenger = new Passenger(dto);
		passenger.setId(++this.currId);
		this.allPassengerss.put(this.currId, passenger);
		
		return new UserReturnedDTO(passenger);
	}
	
	@Override
	public boolean Activate(int id) {
		User passenger = this.allPassengerss.get(id);
		if(passenger != null) {
			passenger.setActivated(true);
			return true;
		}
		return false;
	}
	
	@Override
	public UserReturnedDTO update(int id, UserDTO dto) {
		User passenger = this.allPassengerss.get(id);
		if(passenger == null) {
			return null;
		}
		passenger.copy(dto);
		return new UserReturnedDTO(passenger);
	}

}
