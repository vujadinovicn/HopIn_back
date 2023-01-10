package com.hopin.HopIn.sockets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.hopin.HopIn.dtos.RideInviteDTO;

@CrossOrigin(origins = "http://localhost:4200")
@Controller
public class WebSocketController {
	
	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;
	
	@MessageMapping("/send/invite/{to}")
	public String sendInvite(@DestinationVariable String to, String invite) {
		this.simpMessagingTemplate.convertAndSend("/topic/invites/" + to, invite);
		
		return invite;
	}
	
	@MessageMapping("/send/invite-response/{to}")
	public String sendInviteResponse(@DestinationVariable String to, String response) {
		this.simpMessagingTemplate.convertAndSend("/topic/invite-response/" + to, response);
		
		return response;
	}
}
