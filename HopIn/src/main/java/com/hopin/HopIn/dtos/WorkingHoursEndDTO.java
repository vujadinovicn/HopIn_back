package com.hopin.HopIn.dtos;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotEmpty;

public class WorkingHoursEndDTO {
	
	@NotEmpty(message="is required")
	private LocalDateTime end;

	public WorkingHoursEndDTO() {
	}

	public WorkingHoursEndDTO(LocalDateTime end) {
		super();
		this.end = end;
	}

	public LocalDateTime getEnd() {
		return end;
	}

	public void setEnd(LocalDateTime end) {
		this.end = end;
	}

}
