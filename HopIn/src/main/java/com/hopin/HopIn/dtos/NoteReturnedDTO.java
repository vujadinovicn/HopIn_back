package com.hopin.HopIn.dtos;

import java.time.LocalDateTime;

import com.hopin.HopIn.entities.Note;

public class NoteReturnedDTO {

	int id;
	LocalDateTime date;
	String message;
	UserReturnedDTO admin;

	public NoteReturnedDTO(int id, LocalDateTime date, String message) {
		super();
		this.id = id;
		this.date = date;
		this.message = message;
	}

	public NoteReturnedDTO(Note note) {
		super();
		this.id = note.getId();
		this.date = note.getDate();
		this.message = note.getMessage();
		this.admin = new UserReturnedDTO(note.getAdmin());
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public UserReturnedDTO getAdmin() {
		return admin;
	}

	public void setAdmin(UserReturnedDTO admin) {
		this.admin = admin;
	}

	@Override
	public String toString() {
		return "NoteReturnedDTO [id=" + id + ", date=" + date + ", message=" + message + ", admin=" + admin + "]";
	}

}
