package com.hopin.HopIn.dtos;

import java.time.LocalDateTime;

import com.hopin.HopIn.entities.DriverAccountUpdateRequest;
import com.hopin.HopIn.enums.RequestStatus;
import com.hopin.HopIn.enums.RequestType;

public class DriverAccountUpdateRequestDTO {

	private int id;
	private UserReturnedDTO driver;
	private RequestStatus status;
	private RequestType type;
	private LocalDateTime time;

	public DriverAccountUpdateRequestDTO() {

	}

	public DriverAccountUpdateRequestDTO(int id, UserReturnedDTO driver, RequestStatus status, RequestType type,
			LocalDateTime time) {
		super();
		this.id = id;
		this.driver = driver;
		this.status = status;
		this.setType(type);
		this.setTime(time);
	}

	public DriverAccountUpdateRequestDTO(int id, UserReturnedDTO driver, RequestStatus status) {
		super();
		this.id = id;
		this.driver = driver;
		this.status = status;
	}

	public DriverAccountUpdateRequestDTO(DriverAccountUpdateRequest request) {
		super();
		this.id = request.getId();
		this.driver = new UserReturnedDTO(request.getDriver());
		this.status = request.getStatus();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public UserReturnedDTO getDriver() {
		return driver;
	}

	public void setDriver(UserReturnedDTO driver) {
		this.driver = driver;
	}

	public RequestStatus getStatus() {
		return status;
	}

	public void setStatus(RequestStatus status) {
		this.status = status;
	}

	public RequestType getType() {
		return type;
	}

	public void setType(RequestType type) {
		this.type = type;
	}

	public LocalDateTime getTime() {
		return time;
	}

	public void setTime(LocalDateTime time) {
		this.time = time;
	}

}
