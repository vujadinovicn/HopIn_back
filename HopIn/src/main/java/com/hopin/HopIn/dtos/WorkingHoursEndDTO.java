package com.hopin.HopIn.dtos;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class WorkingHoursEndDTO {
	
	@NotNull
//	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss[.SSS][.SS][.S]")
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
