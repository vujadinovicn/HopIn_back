package com.hopin.HopIn.services;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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
	
	
	@Override
	public AllUsersDTO getAll() {
		return new AllUsersDTO(this.allPassengerss);
	}
	
	@Override
	public UserReturnedDTO getPassenger(int id) {
		Passenger passenger = this.getById(id);
		if (passenger == null) { return null; }
		return new UserReturnedDTO(passenger);
	}
	
	@Override
	public UserReturnedDTO insert(UserDTO dto) {
		Passenger passenger = new Passenger(dto);
		passenger.setId(++this.currId);
		this.allPassengers.save(passenger);
		this.allPassengers.flush();
		return new UserReturnedDTO(passenger);
	}
	
	@Override
	public boolean Activate(int id) {
		User passenger = this.getById(id);
		if(passenger != null) {
			passenger.setActivated(true);
			return true;
		}
		return false;
	}
	
	@Override
	public UserReturnedDTO update(int id, UserDTO dto) {
		Passenger passenger = this.getById(id);
		if (passenger == null) {
			return null;
		}
		if (dto.getNewPassword() != "" && dto.getNewPassword() != null) {
			if (!this.checkPasswordMatch(passenger.getPassword(), dto.getPassword())) {
				System.out.println(passenger.getPassword());
				System.out.println(dto.getPassword());
				return null;	
			}
			dto.setPassword(dto.getNewPassword());
		}
		passenger.copy(dto);
		this.allPassengers.save(passenger);
		this.allPassengers.flush();
		return new UserReturnedDTO(passenger);
	}
	
	private boolean checkPasswordMatch(String password, String subbmitedPassword) {
		return password.equals(subbmitedPassword);
	}
	
	private Passenger getById(int passengerId) {
		Optional<Passenger> passenger = allPassengers.findById(passengerId);
		if (passenger.isEmpty()) {
			return null;
		}
		return passenger.get();
	}

}
