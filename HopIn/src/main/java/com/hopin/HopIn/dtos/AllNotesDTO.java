package com.hopin.HopIn.dtos;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.hopin.HopIn.entities.Note;

public class AllNotesDTO {
	private int totalCount;
	private Set<NoteReturnedDTO> results;

	public AllNotesDTO() {
	}

	public AllNotesDTO(List<Note> notes) {
		super();
		this.totalCount = notes.size();

		this.results = new HashSet<NoteReturnedDTO>();
		for (Note note : notes)
			this.results.add(new NoteReturnedDTO(note));
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

	@Override
	public String toString() {
		return "AllNotesDTO [totalCount=" + totalCount + ", results=" + results + "]";
	}

}
