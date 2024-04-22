package com.hopin.HopIn.entities;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.hopin.HopIn.dtos.LocationNoIdDTO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "drivers")
public class Driver extends User {
	
	private boolean isActive;

	@OneToOne(cascade = CascadeType.ALL)
	private Vehicle vehicle;

	@OneToMany(cascade = CascadeType.REFRESH, orphanRemoval = true)
	private Set<Document> documents = new HashSet<Document>();

	@OneToMany(cascade = CascadeType.ALL)
	private Set<WorkingHours> workingHours = new HashSet<WorkingHours>();

	
	public Driver() {
	}

	public Driver(int id, String name, String surname, String email, String password, String address,
			String telephoneNumber, byte[] profilePicture) {
		super(id, name, surname, email, password, address, telephoneNumber, profilePicture);
		this.isActive = true;
	}
	
	public void setInfoByRequest(DriverAccountUpdateInfoRequest request) {
		this.setName(request.getName());
		this.setSurname(request.getSurname());
		this.setAddress(request.getAddress());
		this.setEmail(request.getEmail());
		this.setProfilePicture(request.getProfilePicture());
		this.setTelephoneNumber(request.getTelephoneNumber());
	}

	public Vehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	public Set<Document> getDocuments() {
		return documents;
	}

	public void setDocuments(Set<Document> documents) {
		this.documents = documents;
	}

	public Set<WorkingHours> getWorkingHours() {
		return workingHours;
	}

	public void setWorkingHours(Set<WorkingHours> workingHours) {
		this.workingHours = workingHours;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	
	public LocationNoIdDTO getVehicleLocation() {
		return new LocationNoIdDTO(this.getVehicle().getCurrentLocation());
	}
	
	public String getVehicleLocationLat() {
		return Double.toString(this.getVehicle().getCurrentLocation().getLatitude());
	}
	
	public String getVehicleLocationLng() {
		return Double.toString(this.getVehicle().getCurrentLocation().getLongitude());
	}

}
