package com.hopin.HopIn.entities;

import com.hopin.HopIn.enums.VehicleTypeName;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "vehicle_types")
public class VehicleType {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private VehicleTypeName name;
	private double pricePerKm;

	public VehicleType(int id, VehicleTypeName name, double pricePerKm) {
		super();
		this.id = id;
		this.name = name;
		this.pricePerKm = pricePerKm;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public VehicleTypeName getName() {
		return name;
	}

	public void setName(VehicleTypeName name) {
		this.name = name;
	}

	public double getPricePerKm() {
		return pricePerKm;
	}

	public void setPricePerKm(double pricePerKm) {
		this.pricePerKm = pricePerKm;
	}
}
