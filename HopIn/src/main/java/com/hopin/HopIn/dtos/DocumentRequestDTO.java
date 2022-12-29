package com.hopin.HopIn.dtos;

import com.hopin.HopIn.entities.DriverAccountUpdateDocumentRequest;
import com.hopin.HopIn.enums.DocumentOperationType;
import com.hopin.HopIn.enums.RequestStatus;

public class DocumentRequestDTO {

	private int id;

	private int documentId;
	private String name;
	private String documentImage;
	private DocumentOperationType documentOperationType;
	private RequestStatus status;

	public DocumentRequestDTO() {

	}

	public DocumentRequestDTO(DriverAccountUpdateDocumentRequest request) {
		this.id = request.getId();
		this.documentId = request.getDocumentId();
		this.name = request.getName();
		this.documentImage = request.getDocumentImage();
		this.documentOperationType = request.getDocumentOperationType();
		this.status = request.getStatus();
	}

	public DocumentRequestDTO(int id, String name, int documentId, String documentImage, DocumentOperationType documentOperationType,
			RequestStatus status) {
		super();
		this.id = id;
		this.documentId = documentId;
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

	public int getDocumentId() {
		return documentId;
	}

	public void setDocumentId(int documentId) {
		this.documentId = documentId;
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

}
