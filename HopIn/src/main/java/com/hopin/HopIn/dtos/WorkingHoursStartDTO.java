package com.hopin.HopIn.dtos;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotEmpty;

public class WorkingHoursStartDTO {
	
	@NotEmpty(message="is required")
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
