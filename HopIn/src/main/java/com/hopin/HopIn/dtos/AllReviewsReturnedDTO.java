package com.hopin.HopIn.dtos;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.hopin.HopIn.entities.Review;

public class AllReviewsReturnedDTO {
	private int totalCount;
	private Set<ReviewReturnedDTO> results;
	
	public AllReviewsReturnedDTO() {}

	public AllReviewsReturnedDTO(List<Review> allReviews) {
		super();
		this.totalCount = allReviews.size();
		
		this.results = new HashSet<ReviewReturnedDTO>();
		for(Review review : allReviews) {
			this.results.add(new ReviewReturnedDTO(review));
		}
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public Set<ReviewReturnedDTO> getResults() {
		return results;
	}

	public void setResults(Set<ReviewReturnedDTO> results) {
		this.results = results;
	}
}
