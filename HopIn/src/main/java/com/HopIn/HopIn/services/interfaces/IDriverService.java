package com.HopIn.HopIn.services.interfaces;

import com.HopIn.HopIn.dtos.UserDTO;
import com.HopIn.HopIn.dtos.UserReturnedDTO;

public interface IDriverService {

	public UserReturnedDTO insert(UserDTO driver);

	public UserReturnedDTO getById(int id);

	public UserReturnedDTO update(int id, UserDTO newData);

}
