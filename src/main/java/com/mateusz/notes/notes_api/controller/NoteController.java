package com.mateusz.notes.notes_api.controller;

import com.mateusz.notes.notes_api.dto.NoteRequestDto;
import com.mateusz.notes.notes_api.dto.NoteResponseDto;
import com.mateusz.notes.notes_api.service.NoteService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notes")
@RequiredArgsConstructor
public class NoteController {
    private final NoteService noteService;

    @PostMapping
    public NoteResponseDto createNote(@Valid @RequestBody NoteRequestDto dto) {
        return noteService.createNote(dto);
    }

    @GetMapping
    public List<NoteResponseDto> getAllNotes() {
        return noteService.getAllNotes();
    }

    @GetMapping("/{id}")
    public NoteResponseDto getNoteById(@PathVariable Long id) {
        return noteService.getNoteById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteNote(@PathVariable Long id) {
        noteService.deleteNote(id);
    }
}
