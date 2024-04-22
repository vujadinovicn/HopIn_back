package com.hopin.HopIn.dtos;

import com.hopin.HopIn.entities.Review;

public class ReviewReturnedDTO {
	
	int id;
	int rating;
	String comment;
	UserReturnedDTO passenger;
	
	public ReviewReturnedDTO(Review review) {
		super();
		this.id = review.getId();
		this.rating = review.getRating();
		this.comment = review.getComment();
		this.passenger = new UserReturnedDTO(review.getPassenger());
	}

	public ReviewReturnedDTO(int id, int rating, String comment, UserReturnedDTO passenger) {
		super();
		this.id = id;
		this.rating = rating;
		this.comment = comment;
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

	public UserReturnedDTO getPassenger() {
		return passenger;
	}

	public void setPassenger(UserReturnedDTO passenger) {
		this.passenger = passenger;
	}

	@Override
	public String toString() {
		return "ReviewReturnedDTO [id=" + id + ", rating=" + rating + ", comment=" + comment + ", passenger="
				+ passenger.getId() + "]";
	}

	

}
