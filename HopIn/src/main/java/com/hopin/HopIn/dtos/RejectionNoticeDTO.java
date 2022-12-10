package com.hopin.HopIn.dtos;

import java.time.LocalDateTime;

import com.hopin.HopIn.entities.RejectionNotice;

public class RejectionNoticeDTO {

	private LocalDateTime timeOfRejection;
	private String reason;
	
	public RejectionNoticeDTO(String reason) {
		this.timeOfRejection = LocalDateTime.now();
		this.reason = reason;
	}
	
	public RejectionNoticeDTO(RejectionNotice rejection) {
		this.timeOfRejection = rejection.getTimeOfRejection();
		this.reason = rejection.getReason();
	}

	public LocalDateTime getTimeOfRejection() {
		return timeOfRejection;
	}

	public void setTimeOfRejection(LocalDateTime timeOfRejection) {
		this.timeOfRejection = timeOfRejection;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
}
