package com.hopin.HopIn.entities;

import java.util.ArrayList;
import java.util.List;

public class Driver extends User {

	private List<Document> documents = new ArrayList<Document>();
	private Vehicle vehicle;

	public Vehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	public List<Document> getDocuments() {
		return documents;
	}

	public void setDocuments(List<Document> documents) {
		this.documents = documents;
	}
}
