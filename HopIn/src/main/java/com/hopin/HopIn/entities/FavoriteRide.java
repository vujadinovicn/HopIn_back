package com.hopin.HopIn.entities;

import java.util.HashSet;
import java.util.Set;

import com.hopin.HopIn.dtos.FavoriteRideDTO;
import com.hopin.HopIn.dtos.RouteLocsDTO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "favoriteRides")
public class FavoriteRide {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String favoriteName;

	@ManyToMany(cascade = {CascadeType.REFRESH})
	private Set<Route> routes = new HashSet<Route>();

	@ManyToMany(cascade = {})
	private Set<Passenger> passengers = new HashSet<Passenger>();

	@ManyToOne(cascade = {})
	private VehicleType vehicleType;

	private boolean petTransport;
	private boolean babyTransport;

	public FavoriteRide() {
	}

	public FavoriteRide(int id, String favoriteName, Set<Route> routes, Set<Passenger> passengers,
			VehicleType vehicleType, boolean petTransport, boolean babyTransport) {
		super();
		this.id = id;
		this.favoriteName = favoriteName;
		this.routes = routes;
		this.passengers = passengers;
		this.vehicleType = vehicleType;
		this.petTransport = petTransport;
		this.babyTransport = babyTransport;
	}
	
	public FavoriteRide(FavoriteRideDTO dto, Set<Passenger> passengers, VehicleType vehicleType) {
		this.favoriteName = dto.getFavoriteName();
		this.vehicleType = vehicleType;
		this.petTransport = dto.isPetTransport();
		this.babyTransport = dto.isBabyTransport();
		this.passengers = passengers;
		
		for (RouteLocsDTO rt : dto.getLocations()) {
			this.routes.add(new Route(new Location(rt.getDeparture()), new Location(rt.getDestination())));
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFavoriteName() {
		return favoriteName;
	}

	public void setFavoriteName(String favoriteName) {
		this.favoriteName = favoriteName;
	}

	public Set<Route> getRoutes() {
		return routes;
	}

	public void setRoutes(Set<Route> routes) {
		this.routes = routes;
	}

	public Set<Passenger> getPassengers() {
		return passengers;
	}

	public void setPassengers(Set<Passenger> passengers) {
		this.passengers = passengers;
	}

	public VehicleType getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(VehicleType vehicleType) {
		this.vehicleType = vehicleType;
	}

	public boolean isPetTransport() {
		return petTransport;
	}

	public void setPetTransport(boolean petTransport) {
		this.petTransport = petTransport;
	}

	public boolean isBabyTransport() {
		return babyTransport;
	}

	public void setBabyTransport(boolean babyTransport) {
		this.babyTransport = babyTransport;
	}
}
