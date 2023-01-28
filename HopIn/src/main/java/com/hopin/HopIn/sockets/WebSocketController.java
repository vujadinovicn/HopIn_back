package com.hopin.HopIn.sockets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.hopin.HopIn.dtos.PanicDTO;
import com.hopin.HopIn.services.interfaces.IPanicService;

@CrossOrigin(origins = "http://localhost:4200")
@Controller
public class WebSocketController {
	
	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;
	
	@Autowired
	private IPanicService panicService;
	
	@MessageMapping("/send/invite/{to}")
	public String sendInvite(@DestinationVariable String to, String invite) {
		this.simpMessagingTemplate.convertAndSend("/topic/invites/" + to, invite);
		
		return invite;
	}
	
	@MessageMapping("/send/invite-response/{to}")
	public String sendInviteResponse(@DestinationVariable String to, String response) {
		System.out.println("RES: " + response);
		this.simpMessagingTemplate.convertAndSend("/topic/invite-response/" + to, response);
		
		return response;
	}
	

	@MessageMapping("/send/driver/ride-offer/{to}")
	public String sendRideOffer(@DestinationVariable String to, String message) {
        this.simpMessagingTemplate.convertAndSend("/topic/driver/ride-offers" + to, message);

        return message;
    }
	
	@MessageMapping("/send/driver/ride-offer-response/{to}")
	public String sendRideOfferResponse(@DestinationVariable String to, String message) {
        this.simpMessagingTemplate.convertAndSend("/topic/driver/ride-offer-response" + to, message);

        return message;
    }
	
	@MessageMapping("/send/panic")
	public PanicDTO panic(PanicDTO panic) {
		this.panicService.add(panic);
        this.simpMessagingTemplate.convertAndSend("/topic/panic", panic);
        return panic;
	}
	
}
