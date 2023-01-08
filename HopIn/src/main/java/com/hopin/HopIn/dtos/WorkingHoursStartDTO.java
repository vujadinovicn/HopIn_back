package com.hopin.HopIn.dtos;

import java.time.LocalDateTime;

public class WorkingHoursStartDTO {

	private LocalDateTime start;

	public WorkingHoursStartDTO() {
	}

	public WorkingHoursStartDTO(LocalDateTime start) {
		super();
		this.start = start;
	}

	public LocalDateTime getStart() {
		return start;
	}

	public void setStart(LocalDateTime start) {
		this.start = start;
	}

}
