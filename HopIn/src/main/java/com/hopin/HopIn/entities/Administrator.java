package com.hopin.HopIn.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name="admins")
public class Administrator extends User {

	public Administrator() {
		super();
	}

	public Administrator(int id, String name, String surname, String email, String password, String address,
			String telephoneNumber, byte[] profilePicture) {
		super(id, name, surname, email, password, address, telephoneNumber, profilePicture);
	}
	
}
