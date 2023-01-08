package com.hopin.HopIn.dtos;

import java.time.LocalDateTime;

public class WorkingHoursEndDTO {

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
