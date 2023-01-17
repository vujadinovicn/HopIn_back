package com.hopin.HopIn.dtos;

import java.util.ArrayList;
import java.util.List;

import com.hopin.HopIn.entities.FavoriteRide;
import com.hopin.HopIn.entities.Passenger;
import com.hopin.HopIn.entities.Route;
import com.hopin.HopIn.enums.VehicleTypeName;

public class FavoriteRideReturnedDTO {
	private int id;
	private String favoriteName;
	private List<RouteLocsDTO> locations = new ArrayList<RouteLocsDTO>();
	private List<UserInRideDTO> passengers = new ArrayList<UserInRideDTO>();
	private VehicleTypeName vehicleType;
	private boolean petTransport;
	private boolean babyTransport;

	public FavoriteRideReturnedDTO(int id, String favoriteName, List<RouteLocsDTO> locations, List<UserInRideDTO> passengers,
			VehicleTypeName vehicleType, boolean petTransport, boolean babyTransport) {
		super();
		this.id = id;
		this.favoriteName = favoriteName;
		this.locations = locations;
		this.passengers = passengers;
		this.vehicleType = vehicleType;
		this.petTransport = petTransport;
		this.babyTransport = babyTransport;
	}
	
	public FavoriteRideReturnedDTO(FavoriteRide ride) {
		this.id = ride.getId();
		this.favoriteName = ride.getFavoriteName();
		this.vehicleType = ride.getVehicleType().getName();
		this.petTransport = ride.isPetTransport();
		this.babyTransport = ride.isBabyTransport();
		
		for (Passenger passenger : ride.getPassengers()) {
			this.passengers.add(new UserInRideDTO(passenger));
		}
		
		for (Route route : ride.getRoutes()) {
			this.locations.add(new RouteLocsDTO(new LocationNoIdDTO(route.getDeparture()), new LocationNoIdDTO(route.getDestination())));
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

	public List<RouteLocsDTO> getLocations() {
		return locations;
	}

	public void setLocations(List<RouteLocsDTO> locations) {
		this.locations = locations;
	}

	public List<UserInRideDTO> getPassengers() {
		return passengers;
	}

	public void setPassengers(List<UserInRideDTO> passengers) {
		this.passengers = passengers;
	}

	public VehicleTypeName getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(VehicleTypeName vehicleType) {
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
