package com.hopin.HopIn.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class ReviewDTO {
	
	@NotNull(message="is required")
	@Min(value=0, message="must be greater than 0")
	@Max(value=5, message="must be less or equal to 5")
	Integer rating;
	
	@NotEmpty(message="is required")
	String comment;
	
	public ReviewDTO() {}
	
	public ReviewDTO(Integer rating, String comment) {
		super();
		this.rating = rating;
		this.comment = comment;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@Override
	public String toString() {
		return "ReviewDTO [rating=" + rating + ", comment=" + comment + "]";
	}
	
	
}
