package com.hopin.HopIn.services.interfaces;

import com.hopin.HopIn.entities.User;

public interface ITokenService {

	User getUserFromToken(String header);

}
