package com.hopin.HopIn.dtos;

import com.hopin.HopIn.entities.Document;

public class DocumentReturnedDTO {
	
	private int id;
	private String documentImage;
	private int driverId;
	private String name;
	
	public DocumentReturnedDTO() {
		
	}
	
	public DocumentReturnedDTO(int id, String documentImage, int driverId, String name) {
		super();
		this.id = id;
		this.documentImage = documentImage;
		this.driverId = driverId;
		this.name = name;
	}
	
	public DocumentReturnedDTO(Document document) {
		this.id = document.getId();
		this.documentImage = document.getDocumentImage();
		this.driverId = document.getDriverId();
		this.name = document.getName();
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
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
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}
