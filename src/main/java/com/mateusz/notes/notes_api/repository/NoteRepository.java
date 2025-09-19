package com.mateusz.notes.notes_api.repository;

import com.mateusz.notes.notes_api.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<Note, Long> {
}
