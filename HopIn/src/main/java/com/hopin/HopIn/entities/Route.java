package com.hopin.HopIn.entities;

import com.hopin.HopIn.dtos.RouteDTO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="routes")
public class Route {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private double distance;
	
	@ManyToOne(cascade = CascadeType.ALL)
	private Location departure;
	
	@ManyToOne(cascade = CascadeType.ALL)
	private Location destination;
	
	public Route() {}
	
	
	public Route(int id, Location departure, Location destination, double distance) {
		super();
		this.id = id;
		this.departure = departure;
		this.destination = destination;
		this.distance = distance;
	}
	
	public Route(Location departure, Location destination) {
		this.distance = 0;
		this.departure = departure;
		this.destination = destination;
	}
	
	public Route(RouteDTO dto) {
		this.distance = dto.getDistance();
		this.departure = new Location(dto.getDeparture());
		this.destination = new Location(dto.getDestination());
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Location getDeparture() {
		return departure;
	}
	public void setDeparture(Location departure) {
		this.departure = departure;
	}
	public Location getDestination() {
		return destination;
	}
	public void setDestination(Location destination) {
		this.destination = destination;
	}
	public double getDistance() {
		return distance;
	}
	public void setDistance(double distance) {
		this.distance = distance;
	}

	@Override
	public String toString() {
		return "Route [id=" + id + ", distance=" + distance + ", departure=" + departure + ", destination="
				+ destination + "]";
	}
	
	
	
	
}
