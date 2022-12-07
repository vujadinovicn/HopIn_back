package com.hopin.HopIn.dtos;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.hopin.HopIn.entities.Note;

public class AllNotesDTO {
	private int totalCount;
	private Set<NoteReturnedDTO> results;
	
	public AllNotesDTO() {}

	public AllNotesDTO(Map<Integer, Note> allNotes) {
		super();
		this.totalCount = allNotes.size();
		
		this.results = new HashSet<NoteReturnedDTO>();
		for(Note note : allNotes.values()) {
			this.results.add(new NoteReturnedDTO(note));
		}
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public Set<NoteReturnedDTO> getResults() {
		return results;
	}

	public void setResults(Set<NoteReturnedDTO> results) {
		this.results = results;
	}
	

}
