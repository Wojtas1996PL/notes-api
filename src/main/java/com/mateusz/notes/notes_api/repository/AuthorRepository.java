package com.mateusz.notes.notes_api.repository;

import com.mateusz.notes.notes_api.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
