package com.hopin.HopIn.dtos;

import java.time.LocalDateTime;

import com.hopin.HopIn.entities.DriverAccountUpdateRequest;
import com.hopin.HopIn.enums.RequestStatus;
import com.hopin.HopIn.enums.RequestType;

public class RequestDTO {

	private int id;
	private UserReturnedDTO driver;
	private RequestStatus status;
	private RequestType type;
	private LocalDateTime time;
	private String reason;
	private UserReturnedDTO admin;

	public RequestDTO() {

	}

	public RequestDTO(int id, UserReturnedDTO driver, RequestStatus status, RequestType type,
			LocalDateTime time) {
		super();
		this.id = id;
		this.driver = driver;
		this.status = status;
		this.type = type;
		this.time = time;
	}

	public RequestDTO(int id, UserReturnedDTO driver, RequestStatus status) {
		super();
		this.id = id;
		this.driver = driver;
		this.status = status;
	}

	public RequestDTO(DriverAccountUpdateRequest request) {
		super();
		this.id = request.getId();
		this.driver = new UserReturnedDTO(request.getDriver());
		this.status = request.getStatus();
		this.reason = request.getReason();
		this.admin = new UserReturnedDTO(request.getAdmin());
		this.type = request.getType();
		this.time = request.getTime();
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

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public UserReturnedDTO getAdmin() {
		return admin;
	}

	public void setAdmin(UserReturnedDTO admin) {
		this.admin = admin;
	}

}
