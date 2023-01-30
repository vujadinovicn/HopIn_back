package com.hopin.HopIn.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import com.hopin.HopIn.services.interfaces.IScheduledService;

import jakarta.transaction.Transactional;

@Controller
public class ScheduledController {
	
	@Autowired 
	private IScheduledService scheduledService; 

	@Transactional
	@Scheduled(initialDelayString = "${initialdelay}", fixedRateString = "${fixedrate}")
	public void checkIfVehicleArrivedAtDeparture() {
		scheduledService.checkIfVehicleArrivedAtDeparture();
	} 
} 
