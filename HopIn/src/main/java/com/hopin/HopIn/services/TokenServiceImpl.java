package com.hopin.HopIn.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hopin.HopIn.entities.User;
import com.hopin.HopIn.services.interfaces.ITokenService;
import com.hopin.HopIn.services.interfaces.IUserService;
import com.hopin.HopIn.util.TokenUtils;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class TokenServiceImpl implements ITokenService {
	
	@Autowired
	TokenUtils tokenUtils;
	
	@Autowired
	IUserService userService;
	
	@Override
	public User getUserFromToken(String header) {
		String token = getToken(header);
		int id = (int) tokenUtils.getIdFromToken(token);
		
		return userService.getById(id);
	}
	
	private String getToken(String header) {
		if (header != null && header.startsWith("Bearer ")) {
			String token = header.substring(7);
			return token.substring(1, token.length() - 1);
		}

		return null;
	}
}
