package com.hopin.HopIn.services.interfaces;

import com.hopin.HopIn.dtos.AllUsersDTO;
import com.hopin.HopIn.dtos.UserDTO;
import com.hopin.HopIn.dtos.UserReturnedDTO;

public interface IDriverService {

	public UserReturnedDTO insert(UserDTO driver);

	public UserReturnedDTO getById(int id);

	public UserReturnedDTO update(int id, UserDTO newData);

	public AllUsersDTO getAll(int page, int size);
}