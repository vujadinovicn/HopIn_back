package com.hopin.HopIn.dtos;

import java.time.LocalDateTime;

import com.hopin.HopIn.entities.WorkingHours;

public class WorkingHoursReturnedDTO {
	private int id;
	private LocalDateTime start;
	private LocalDateTime end;

	public WorkingHoursReturnedDTO(int id, LocalDateTime start, LocalDateTime end) {
		super();
		this.id = id;
		this.start = start;
		this.end = end;
	}
	
	public WorkingHoursReturnedDTO(WorkingHours hours) {
		super();
		this.id = hours.getId();
		this.start = hours.getStart();
		this.end = hours.getEnd();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDateTime getStart() {
		return start;
	}

	public void setStart(LocalDateTime start) {
		this.start = start;
	}

	public LocalDateTime getEnd() {
		return end;
	}

	public void setEnd(LocalDateTime end) {
		this.end = end;
	}

}
