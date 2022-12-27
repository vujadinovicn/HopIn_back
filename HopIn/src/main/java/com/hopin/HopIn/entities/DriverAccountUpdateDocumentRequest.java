package com.hopin.HopIn.entities;

import java.io.UnsupportedEncodingException;

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
	private byte[] documentImage;
	private DocumentOperationType type;

	public DriverAccountUpdateDocumentRequest(int id, String name, byte[] documentImage, DocumentOperationType type) {
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
	public DocumentOperationType getType() {
		return type;
	}

	public void setType(DocumentOperationType type) {
		this.type = type;
	}

}
