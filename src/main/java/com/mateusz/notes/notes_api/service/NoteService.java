package com.mateusz.notes.notes_api.service;

import com.mateusz.notes.notes_api.dto.NoteRequestDto;
import com.mateusz.notes.notes_api.dto.NoteResponseDto;
import com.mateusz.notes.notes_api.exception.ResourceNotFoundException;
import com.mateusz.notes.notes_api.mapper.NoteMapper;
import com.mateusz.notes.notes_api.model.Author;
import com.mateusz.notes.notes_api.model.Note;
import com.mateusz.notes.notes_api.repository.NoteRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NoteService {
    private final NoteRepository noteRepository;
    private final NoteMapper noteMapper;
    private final AuthorService authorService;

    public NoteResponseDto createNote(NoteRequestDto dto) {
        Author author = authorService.getAuthorEntityById(dto.getAuthorId());
        Note note = noteMapper.toEntity(dto);
        note.setAuthor(author);
        Note saved = noteRepository.save(note);
        return noteMapper.toDto(saved);
    }

    public List<NoteResponseDto> getAllNotes() {
        return noteRepository.findAll().stream()
                .map(noteMapper::toDto)
                .toList();
    }

    public NoteResponseDto getNoteById(Long id) {
        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Note not found"));
        return noteMapper.toDto(note);
    }

    public void deleteNote(Long id) {
        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Note not found"));
        noteRepository.delete(note);
    }
}
