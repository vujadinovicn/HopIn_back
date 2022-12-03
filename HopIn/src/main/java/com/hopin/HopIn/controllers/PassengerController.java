package com.hopin.HopIn.controllers;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hopin.HopIn.dtos.AllPassengersDto;
import com.hopin.HopIn.dtos.UserDto;
import com.hopin.HopIn.entities.User;

@RestController
@RequestMapping("/api/passenger")
public class PassengerController {
	
	Set<User> passengers = this.setMockUp();
	
	@GetMapping()
	public AllPassengersDto getPassengers() {
		return new AllPassengersDto(this.passengers);
	}
	
	@PostMapping()
	public UserDto insertPassenger(@RequestBody User user) {
		this.passengers.add(user);
		return new UserDto(user);
	}
	
	private Set<User> setMockUp() {
		Set<User> s = new HashSet<User>();
		User firstUser = new User(1, "Mika", "Mikic", "mika@gmail.com", "123", "ul. Vuka Karadzica bb", "065454454");
		User secondUser = new User(1, "Zika", "Zikic", "zika@gmail.com", "123", "ul. Bele Njive bb", "065455554");
		
		s.add(secondUser);
		s.add(firstUser);
		return s;
	}

}
