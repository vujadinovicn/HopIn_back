package com.hopin.HopIn.services;

import org.springframework.beans.factory.annotation.Autowired;

import com.hopin.HopIn.repositories.RejectionNoticeRepository;
import com.hopin.HopIn.services.interfaces.IRejectionNoticeService;

public class RejectionNoticeServiceImpl implements IRejectionNoticeService {
	
	@Autowired
	private RejectionNoticeRepository rejectionNoticeRepository;
}
