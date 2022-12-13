package com.hopin.HopIn.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hopin.HopIn.entities.Note;

public interface NoteRepository extends JpaRepository<Note, Integer>{

}
