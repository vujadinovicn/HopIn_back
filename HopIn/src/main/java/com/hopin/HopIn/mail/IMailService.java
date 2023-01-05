package com.hopin.HopIn.mail;

import com.hopin.HopIn.entities.Passenger;

public interface IMailService {
	
	public void send(String message);
	
	public void sendVerificationMail(Passenger passenger);
}
