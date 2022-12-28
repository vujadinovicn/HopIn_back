package com.hopin.HopIn.entities;

import java.time.LocalDateTime;

import com.hopin.HopIn.enums.RequestStatus;
import com.hopin.HopIn.enums.RequestType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "driver_account_update_requests")
@Inheritance(strategy = InheritanceType.JOINED)
public class DriverAccountUpdateRequest {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@NotNull
	private RequestStatus status;
	
	@NotEmpty
	private String reason;

	@ManyToOne(cascade = CascadeType.REFRESH)
	@NotNull
	private Driver driver;

	@ManyToOne(cascade = CascadeType.REFRESH)
	private Administrator admin;
	
	@NotNull
	private RequestType type;
	
	private LocalDateTime time;
	
	public DriverAccountUpdateRequest() {}

	public DriverAccountUpdateRequest(int id, RequestStatus status, String reason, Driver driver, Administrator admin, RequestType type) {
		super();
		this.id = id;
		this.status = status;
		this.reason = reason;
		this.driver = driver;
		this.admin = admin;
		this.type = type;
		this.time = LocalDateTime.now();
	}
	
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public RequestStatus getStatus() {
		return status;
	}

	public void setStatus(RequestStatus status) {
		this.status = status;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Driver getDriver() {
		return driver;
	}

	public void setDriver(Driver driver) {
		this.driver = driver;
	}

	public Administrator getAdmin() {
		return admin;
	}

	public void setAdmin(Administrator admin) {
		this.admin = admin;
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
