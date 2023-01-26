package com.hopin.HopIn.mail;

import com.hopin.HopIn.entities.Passenger;
import com.hopin.HopIn.entities.User;
import com.hopin.HopIn.tokens.SecureToken;

public interface IMailService {
	
	public void send(String message);
	
	public void sendVerificationMail(Passenger passenger, String token);
	
	public void sendForgotPasswordMail(User user, String token);
}
