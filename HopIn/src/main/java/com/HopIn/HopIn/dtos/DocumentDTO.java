package com.HopIn.HopIn.dtos;

public class DocumentDTO {

	private String name;
	private String documentImage;
	private int driverId;
	
	public DocumentDTO(String name, String documentImage, int driverId) {
		super();
		this.name = name;
		this.documentImage = documentImage;
		this.driverId = driverId;
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
