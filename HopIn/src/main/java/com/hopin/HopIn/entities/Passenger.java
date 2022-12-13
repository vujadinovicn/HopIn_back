package com.hopin.HopIn.entities;

import java.util.HashSet;
import java.util.Set;

import com.hopin.HopIn.dtos.UserDTO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="passengers")
public class Passenger extends User {
	
	@ManyToMany(cascade = CascadeType.REFRESH)
	private Set<Ride> rides = new HashSet<Ride>();
	
	@ManyToMany(cascade = CascadeType.REFRESH)
	private Set<Route> favouriteRoutes = new HashSet<Route>();
	
	public Passenger() {}
	
	public Passenger(int id, String name, String surname, String email, String password, String address,
			String telephoneNumber, String profilePicture, Set<Ride> rides, Set<Route> favouriteRoutes) {
		super(id, name, surname, email, password, address, telephoneNumber, profilePicture);
		this.rides = rides;
		this.favouriteRoutes = favouriteRoutes;
	}
	
	public Passenger(Set<Ride> rides, Set<Route> favouriteRoutes) {
		super();
		this.rides = rides;
		this.favouriteRoutes = favouriteRoutes;
	}

	public Passenger(UserDTO dto) {
		super(dto);
	}
	
	public Set<Ride> getRides() {
		return rides;
	}

	public void setRides(Set<Ride> rides) {
		this.rides = rides;
	}

	public Set<Route> getFavouriteRoutes() {
		return favouriteRoutes;
	}

	public void setFavouriteRoutes(Set<Route> favouriteRoutes) {
		this.favouriteRoutes = favouriteRoutes;
	}

}
