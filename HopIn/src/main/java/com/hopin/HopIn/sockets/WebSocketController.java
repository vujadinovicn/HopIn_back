package com.hopin.HopIn.sockets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.hopin.HopIn.dtos.RideInviteDTO;
import com.hopin.HopIn.security.auth.TokenBasedAuthentication;
import com.hopin.HopIn.util.TokenUtils;

@CrossOrigin(origins = "http://localhost:4200")
@Controller
public class WebSocketController {

	@Autowired
	private TokenUtils tokenUtils;
	
	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;
	
	@MessageMapping("/send/invite/{to}")
	public RideInviteDTO sendInvite(@DestinationVariable String to, RideInviteDTO invite) {
		this.simpMessagingTemplate.convertAndSend("/topic/invites/" + to, invite);
		
		return invite;
	}
}
