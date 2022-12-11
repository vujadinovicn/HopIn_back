package com.hopin.HopIn.entities;

public class Review {
	int id;
	int rating;
	String comment;
	User user;
	
	public Review() {}

	public Review(int id, int rating, String comment, User user) {
		super();
		this.id = id;
		this.rating = rating;
		this.comment = comment;
		this.user = user;
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
}
