package com.hopin.HopIn.dtos;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.hopin.HopIn.entities.Message;

public class AllMessagesDTO {
	
	private int totalCount;
	private Set<MessageReturnedDTO> results;
	
	public AllMessagesDTO() {}

	public AllMessagesDTO(Map<Integer, Message> allMessages) {
		super();
		this.totalCount = allMessages.size();
		
		this.results = new HashSet<MessageReturnedDTO>();
		for(Message message : allMessages.values()) {
			this.results.add(new MessageReturnedDTO(message));
		}
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public Set<MessageReturnedDTO> getResults() {
		return results;
	}

	public void setResults(Set<MessageReturnedDTO> results) {
		this.results = results;
	}

}
