package com.hopin.HopIn.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "documents")
public class Document {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	private String documentImage;
	private int driverId;
	
	public Document() {}
	
	public Document(int id, String name, String documentImage, int driverId) {
		super();
		this.id = id;
		this.name = name;
		this.documentImage = documentImage;
		this.driverId = driverId;
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

	public int getDriverId() {
		return driverId;
	}

	public void setDriverId(int driverId) {
		this.driverId = driverId;
	}
}
