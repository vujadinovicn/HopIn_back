package com.hopin.HopIn.entities;

import java.time.LocalDateTime;

public class WorkingHours {

	private int id;
	private LocalDateTime start;
	private LocalDateTime end;
	private int driverId;

	public WorkingHours(int id, LocalDateTime start, LocalDateTime end, int driverId) {
		super();
		this.id = id;
		this.start = start;
		this.end = end;
		this.driverId = driverId;
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

	public int getDriverId() {
		return driverId;
	}

	public void setDriverId(int driverId) {
		this.driverId = driverId;
	}

}
