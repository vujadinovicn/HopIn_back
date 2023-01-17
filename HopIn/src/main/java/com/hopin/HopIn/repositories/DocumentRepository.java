package com.hopin.HopIn.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hopin.HopIn.entities.Document;

public interface DocumentRepository extends JpaRepository<Document, Integer> {

}
