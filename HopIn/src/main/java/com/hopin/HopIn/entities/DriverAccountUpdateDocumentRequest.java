package com.hopin.HopIn.entities;

import com.hopin.HopIn.enums.DocumentOperationType;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "driver_account_update_vehicle_requests")
public class DriverAccountUpdateDocumentRequest {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String name;
	private String documentImage;
	private DocumentOperationType type;

	public DriverAccountUpdateDocumentRequest(int id, String name, String documentImage, DocumentOperationType type) {
		super();
		this.id = id;
		this.name = name;
		this.documentImage = documentImage;
		this.type = type;
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

	public DocumentOperationType getType() {
		return type;
	}

	public void setType(DocumentOperationType type) {
		this.type = type;
	}

}
