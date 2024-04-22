package com.hopin.HopIn.entities;

import com.hopin.HopIn.enums.ReviewType;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "reviews")
public class Review {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private int rating;
	private String comment;

	private ReviewType type;

	@ManyToOne
	private Passenger passenger;

	@ManyToOne
	private Ride ride;

	public Review() {
	}

	public Review(int id, int rating, String comment, ReviewType type, Passenger passenger) {
		super();
		this.id = id;
		this.rating = rating;
		this.comment = comment;
		this.type = type;
		this.passenger = passenger;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Passenger getPassenger() {
		return passenger;
	}

	public void setPassenger(Passenger passenger) {
		this.passenger = passenger;
	}

	public ReviewType getType() { 
		return type;
	}

	public void setType(ReviewType type) {
		this.type = type;
	}

	public Ride getRide() {
		return ride;
	}

	public void setRide(Ride ride) {
		this.ride = ride;
	}

	@Override
	public String toString() {
		return "Review [id=" + id + ", rating=" + rating + ", comment=" + comment + ", type=" + type + ", passenger="
				+ passenger.getId() + ", ride=" + ride.getId() + "]";
	}
	
	

}
