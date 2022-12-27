package com.hopin.HopIn.entities;

import java.io.UnsupportedEncodingException;

import com.hopin.HopIn.enums.DocumentOperationType;
import com.hopin.HopIn.enums.RequestStatus;
import com.hopin.HopIn.enums.RequestType;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "driver_account_update_vehicle_requests")
public class DriverAccountUpdateDocumentRequest extends DriverAccountUpdateRequest{

	@NotEmpty
	@Pattern(regexp = "^([a-zA-Zčćđžš ]*)$")
	private String name;
	
	private byte[] documentImage;
	
	@NotEmpty
	@NotNull
	private DocumentOperationType documentOperationType;

	public DriverAccountUpdateDocumentRequest(int id, RequestStatus status, String reason, Driver driver, Administrator admin,
			String name, byte[] documentImage, DocumentOperationType type) {
		super(id, status, reason, driver, admin, RequestType.DOCUMENT);
		this.name = name;
		this.documentImage = documentImage;
		this.documentOperationType = documentOperationType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDocumentImage() {
		String s;
		try {
			s = "data:image/jpeg;base64, ";
			s = s + new String(this.documentImage, "UTF-8");
			return s;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void setDocumentImage(String documentImage) {
		String[] picture = documentImage.split(",");
		if (picture.length >= 2) {
			byte[] decoded;
			try {
				decoded = picture[1].getBytes("UTF-8");
				this.documentImage = decoded;
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
	}
	public DocumentOperationType getDocumentOperationType() {
		return documentOperationType;
	}

	public void setDocumentOperationType(DocumentOperationType type) {
		this.documentOperationType = type;
	}

}
