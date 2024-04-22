package com.hopin.HopIn.repositories;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.hopin.HopIn.entities.Note;

public interface NoteRepository extends JpaRepository<Note, Integer>, PagingAndSortingRepository<Note, Integer>{
	
	public List<Note> findAllByUserId(int id, Pageable pageable);
	
	public List<Note> findAllByUserId(int id);

}
