package com.hopin.HopIn.entities;

import java.io.UnsupportedEncodingException;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

@Entity
@Table(name = "documents")
public class Document {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	@Lob
	private byte[] documentImage;
	private int driverId;
	
	public Document() {}
	
	public Document(int id, String name, byte[] documentImage, int driverId) {
		super();
		this.id = id;
		this.name = name;
		this.documentImage = documentImage;
		this.driverId = driverId;
	}
	
	public Document(String name, byte[] documentImage, int driverId) {
		super();
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
		if (this.documentImage == null)
			return null;
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
		if (documentImage == null)
			documentImage = "s";
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

	public int getDriverId() {
		return driverId;
	}

	public void setDriverId(int driverId) {
		this.driverId = driverId;
	}
}
