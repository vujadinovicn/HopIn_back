package com.HopIn.HopIn.entities;

import java.util.ArrayList;
import java.util.List;

public class Driver extends User {
	
	private List<Document> documents;

	public List<Document> getDocuments() {
		if (this.documents == null)
			documents = new ArrayList<Document>();
		return documents;
	}

	public void setDocuments(List<Document> documents) {
		this.documents = documents;
	}
	
}
