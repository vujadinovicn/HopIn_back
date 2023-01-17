package com.hopin.HopIn.dtos;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class DocumentDTO {
	
	@NotNull
	@NotEmpty(message="is required")
	@Length(max=100)
	private String name;
	
	@NotNull
	@NotEmpty(message="is required")
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
