package com.hopin.HopIn.mail;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hopin.HopIn.entities.Passenger;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;

@Service
public class MailServiceImpl implements IMailService {
	
	@Autowired
	SendGrid sendGrid;

	@Override
	public void send(String message) {
		
		Email from = new Email("hopinapp22@gmail.com", "HopIn");
	    String subject = "First email JUPIII";
	    Email to = new Email("t.s.mihajlovic25@gmail.com");
	    Content content = new Content("text/plain", message);
	    Mail mail = new Mail(from, subject, to, content);
	    
	    Request request = new Request();
	    try {
	      request.setMethod(Method.POST);
	      request.setEndpoint("mail/send");
	      request.setBody(mail.build());
	      Response response = this.sendGrid.api(request);     
	    } catch (IOException ex) {
	      
	    }	   
	}
	
	@Override
	public void sendVerificationMail(Passenger passenger) {
		
		Email from = new Email("hopinapp22@gmail.com", "HopIn");
	    Email to = new Email(passenger.getEmail());
	    
	    Personalization personalization = new Personalization();
	    personalization.addTo(to);
	    personalization.addDynamicTemplateData("name", passenger.getName());
	    personalization.addDynamicTemplateData("verificationLink", "http://localhost:4200/verify?code=" + passenger.getVerificationCode());
	    
	    Mail mail = new Mail();
	    mail.setFrom(from);
	    mail.addPersonalization(personalization);
		mail.setTemplateId("d-dda97bd6ad8144a38a5edd121b7e1a55");
	    
	    Request request = new Request();
	    try {
	      request.setMethod(Method.POST);
	      request.setEndpoint("mail/send");
	      request.setBody(mail.build());
	      Response response = this.sendGrid.api(request);     
	    } catch (IOException ex) {
	      
	    }	   
	}

}
