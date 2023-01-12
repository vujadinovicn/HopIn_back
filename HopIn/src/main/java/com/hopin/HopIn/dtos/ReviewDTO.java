package com.hopin.HopIn.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class ReviewDTO {
	
	@NotNull(message="is required")
	@Min(value=0, message="must be greater than 0")
	@Max(value=5, message="must be less or equal to 5")
	int rating;
	
	@NotEmpty(message="is required")
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
