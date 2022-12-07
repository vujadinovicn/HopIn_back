package com.hopin.HopIn.dtos;

public class ReviewDTO {
	
	int rating;
	String comment;
	
	public ReviewDTO(int rating, String comment) {
		super();
		this.rating = rating;
		this.comment = comment;
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
}
