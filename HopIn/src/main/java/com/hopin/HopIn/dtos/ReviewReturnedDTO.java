package com.hopin.HopIn.dtos;

import com.hopin.HopIn.entities.Review;

public class ReviewReturnedDTO {
	
	int id;
	int rating;
	String comment;
	UserInRideDTO passenger;
	
	public ReviewReturnedDTO(Review review) {
		super();
		this.id = review.getId();
		this.rating = review.getRating();
		this.comment = review.getComment();
		this.passenger = new UserInRideDTO(123, "pera@peric.com");
	}

	public ReviewReturnedDTO(int id, int rating, String comment, UserInRideDTO passenger) {
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

	public UserInRideDTO getPassenger() {
		return passenger;
	}

	public void setPassenger(UserInRideDTO passenger) {
		this.passenger = passenger;
	}

}
