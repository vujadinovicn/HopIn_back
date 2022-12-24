package com.hopin.HopIn.dtos;

public class DocumentDTO {

	private String name;
	private String documentImage;
	
	public DocumentDTO(String name, String documentImage) {
		super();
		this.name = name;
		this.documentImage = documentImage;
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
}
