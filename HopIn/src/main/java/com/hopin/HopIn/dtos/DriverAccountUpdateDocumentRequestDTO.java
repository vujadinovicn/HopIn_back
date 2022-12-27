package com.hopin.HopIn.dtos;

import com.hopin.HopIn.entities.DriverAccountUpdateDocumentRequest;
import com.hopin.HopIn.enums.DocumentOperationType;
import com.hopin.HopIn.enums.RequestStatus;

public class DriverAccountUpdateDocumentRequestDTO {
	
	private int id;

	private String name;
	private String documentImage;
	private DocumentOperationType documentOperationType;
	private RequestStatus status;
	private String reason;
	private String adminName;
	private String adminSurname;
	
	public DriverAccountUpdateDocumentRequestDTO() {
		
	}
	
	public DriverAccountUpdateDocumentRequestDTO(DriverAccountUpdateDocumentRequest request) {
		this.id = request.getId();
		this.name = request.getName();
		this.documentImage = request.getDocumentImage();
		this.documentOperationType = request.getDocumentOperationType();
		this.status = request.getStatus();
		this.reason = request.getReason();
		this.adminName = request.getAdmin().getName();
		this.adminSurname = request.getAdmin().getSurname();
	}
	
	
	public DriverAccountUpdateDocumentRequestDTO(int id, String name, String documentImage,
			DocumentOperationType documentOperationType, RequestStatus status) {
		super();
		this.id = id;
		this.name = name;
		this.documentImage = documentImage;
		this.documentOperationType = documentOperationType;
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDocumentImage() {
		return documentImage;
	}
	
	public void setDocumentImage(String documentImage) {
		this.documentImage = documentImage;
	}
	
	public DocumentOperationType getDocumentOperationType() {
		return documentOperationType;
	}
	

	
	public RequestStatus getStatus() {
		return status;
	}

	public void setStatus(RequestStatus status) {
		this.status = status;
	}

	public void setDocumentOperationType(DocumentOperationType documentOperationType) {
		this.documentOperationType = documentOperationType;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getAdminName() {
		return adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	public String getAdminSurname() {
		return adminSurname;
	}

	public void setAdminSurname(String adminSurname) {
		this.adminSurname = adminSurname;
	}
	
	

}
