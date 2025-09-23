package com.mateusz.notes.notes_api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.mateusz.notes.notes_api.dto.NoteRequestDto;
import com.mateusz.notes.notes_api.dto.NoteResponseDto;
import com.mateusz.notes.notes_api.exception.ResourceNotFoundException;
import com.mateusz.notes.notes_api.mapper.NoteMapper;
import com.mateusz.notes.notes_api.model.Author;
import com.mateusz.notes.notes_api.model.Note;
import com.mateusz.notes.notes_api.repository.NoteRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class NoteServiceTest {
    @Mock
    private NoteRepository noteRepository;

    @Mock
    private NoteMapper noteMapper;

    @Mock
    private AuthorService authorService;

    @InjectMocks
    private NoteService noteService;

    @Test
    void createNote_shouldReturnNoteResponseDto() {
        NoteRequestDto requestDto = new NoteRequestDto();
        requestDto.setTitle("Test title");
        requestDto.setContent("Test content");
        requestDto.setAuthorId(1L);

        Author author = new Author();
        author.setId(1L);
        author.setName("Jan Kowalski");

        Note noteEntity = new Note();
        noteEntity.setTitle("Test title");
        noteEntity.setContent("Test content");

        Note savedNote = new Note();
        savedNote.setId(1L);
        savedNote.setTitle("Test title");
        savedNote.setContent("Test content");
        savedNote.setCreatedAt(LocalDateTime.now());
        savedNote.setAuthor(author);

        NoteResponseDto responseDto = new NoteResponseDto();
        responseDto.setId(1L);
        responseDto.setTitle("Test title");
        responseDto.setContent("Test content");
        responseDto.setCreatedAt(savedNote.getCreatedAt());
        responseDto.setAuthorName("Jan Kowalski");

        when(authorService.getAuthorEntityById(1L)).thenReturn(author);
        when(noteMapper.toEntity(requestDto)).thenReturn(noteEntity);
        when(noteRepository.save(noteEntity)).thenReturn(savedNote);
        when(noteMapper.toDto(savedNote)).thenReturn(responseDto);

        NoteResponseDto result = noteService.createNote(requestDto);

        assertEquals(1L, result.getId());
        assertEquals("Test title", result.getTitle());
        assertEquals("Jan Kowalski", result.getAuthorName());
    }

    @Test
    void getAllNotes_shouldReturnListOfDtos() {
        Note note1 = new Note();
        note1.setId(1L);
        note1.setTitle("Note 1");

        Note note2 = new Note();
        note2.setId(2L);
        note2.setTitle("Note 2");

        NoteResponseDto dto1 = new NoteResponseDto();
        dto1.setId(1L);
        dto1.setTitle("Note 1");

        NoteResponseDto dto2 = new NoteResponseDto();
        dto2.setId(2L);
        dto2.setTitle("Note 2");

        when(noteRepository.findAll()).thenReturn(List.of(note1, note2));
        when(noteMapper.toDto(note1)).thenReturn(dto1);
        when(noteMapper.toDto(note2)).thenReturn(dto2);

        List<NoteResponseDto> result = noteService.getAllNotes();

        assertEquals(2, result.size());
        assertEquals("Note 1", result.get(0).getTitle());
        assertEquals("Note 2", result.get(1).getTitle());
    }

    @Test
    void getNoteById_shouldReturnDto() {
        Note note = new Note();
        note.setId(1L);
        note.setTitle("Note 1");

        NoteResponseDto dto = new NoteResponseDto();
        dto.setId(1L);
        dto.setTitle("Note 1");

        when(noteRepository.findById(1L)).thenReturn(Optional.of(note));
        when(noteMapper.toDto(note)).thenReturn(dto);

        NoteResponseDto result = noteService.getNoteById(1L);

        assertEquals(1L, result.getId());
        assertEquals("Note 1", result.getTitle());
    }

    @Test
    void getNoteById_shouldThrowExceptionWhenNotFound() {
        when(noteRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> noteService.getNoteById(999L));
    }

    @Test
    void deleteNote_shouldDeleteEntity() {
        Note note = new Note();
        note.setId(1L);
        note.setTitle("Note 1");

        when(noteRepository.findById(1L)).thenReturn(Optional.of(note));
        doNothing().when(noteRepository).delete(note);

        noteService.deleteNote(1L);

        verify(noteRepository).delete(note);
    }

    @Test
    void deleteNote_shouldThrowExceptionWhenNotFound() {
        when(noteRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> noteService.deleteNote(999L));
    }
}
