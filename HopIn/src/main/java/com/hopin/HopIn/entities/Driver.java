package com.hopin.HopIn.entities;

import java.util.ArrayList;
import java.util.List;

public class Driver extends User {

	private Vehicle vehicle;
	private List<Document> documents = new ArrayList<Document>();
	private List<WorkingHours> workingHours = new ArrayList<WorkingHours>();
	
	public Driver() {}
	
	public Driver(int id, String name, String surname, String email, String password, String address,
			String telephoneNumber, String profilePicture) {
		super(id, name, surname, email, password, address, telephoneNumber, profilePicture);
	}

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

	public List<WorkingHours> getWorkingHours() {
		return workingHours;
	}

	public void setWorkingHours(List<WorkingHours> workingHours) {
		this.workingHours = workingHours;
	}

}
