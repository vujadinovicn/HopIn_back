package com.hopin.HopIn.entities;

import java.util.ArrayList;
import java.util.List;

public class Driver extends User{

	private List<Document> documents = new ArrayList<Document>();

	public List<Document> getDocuments() {
		return documents;
	}

	public void setDocuments(List<Document> documents) {
		this.documents = documents;
	}
}
