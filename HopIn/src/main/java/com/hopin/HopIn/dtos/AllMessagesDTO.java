package com.hopin.HopIn.dtos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.hopin.HopIn.entities.Message;

public class AllMessagesDTO {

	private int totalCount;
	private List<MessageReturnedDTO> results;

	public AllMessagesDTO() {
	}

	public AllMessagesDTO(List<Message> allMessages) {
		super();
//		this.totalCount = allMessages.size();
//
//		this.results = new ArrayList<MessageReturnedDTO>();
//		for (Message message : allMessages) {
//			this.results.add(new MessageReturnedDTO(message));
//		}

	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public List<MessageReturnedDTO> getResults() {
		return results;
	}

	public void setResults(List<MessageReturnedDTO> results) {
		this.results = results;
	}

	@Override
	public String toString() {
		return "AllMessagesDTO [totalCount=" + totalCount + ", results=" + results + "]";
	}

}
