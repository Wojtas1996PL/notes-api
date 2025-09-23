package com.mateusz.notes.notes_api.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mateusz.notes.notes_api.dto.NoteRequestDto;
import com.mateusz.notes.notes_api.dto.NoteResponseDto;
import com.mateusz.notes.notes_api.exception.GlobalExceptionHandler;
import com.mateusz.notes.notes_api.exception.ResourceNotFoundException;
import com.mateusz.notes.notes_api.service.NoteService;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
class NoteControllerTest {
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Mock
    private NoteService noteService;

    @InjectMocks
    private NoteController noteController;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        mockMvc = MockMvcBuilders.standaloneSetup(noteController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void createNote_shouldReturnNoteResponseDto() throws Exception {
        NoteResponseDto responseDto = new NoteResponseDto();
        responseDto.setId(1L);
        responseDto.setTitle("Test title");
        responseDto.setContent("Test content");
        responseDto.setCreatedAt(LocalDateTime.now());
        responseDto.setAuthorName("Jan Kowalski");

        when(noteService.createNote(any(NoteRequestDto.class))).thenReturn(responseDto);

        NoteRequestDto requestDto = new NoteRequestDto();
        requestDto.setTitle("Test title");
        requestDto.setContent("Test content");
        requestDto.setAuthorId(1L);

        mockMvc.perform(MockMvcRequestBuilders.post("/notes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Test title"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.authorName").value("Jan Kowalski"));
    }

    @Test
    void createNote_shouldReturnBadRequest_whenTitleIsBlank() throws Exception {
        NoteRequestDto requestDto = new NoteRequestDto();
        requestDto.setTitle("");
        requestDto.setAuthorId(1L);

        mockMvc.perform(MockMvcRequestBuilders.post("/notes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void createNote_shouldReturnBadRequest_whenAuthorIdIsNull() throws Exception {
        NoteRequestDto requestDto = new NoteRequestDto();
        requestDto.setTitle("Valid title");
        requestDto.setAuthorId(null);

        mockMvc.perform(MockMvcRequestBuilders.post("/notes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void getAllNotes_shouldReturnList() throws Exception {
        NoteResponseDto dto1 = new NoteResponseDto();
        dto1.setId(1L);
        dto1.setTitle("Note 1");
        dto1.setAuthorName("Jan");

        NoteResponseDto dto2 = new NoteResponseDto();
        dto2.setId(2L);
        dto2.setTitle("Note 2");
        dto2.setAuthorName("Anna");

        when(noteService.getAllNotes()).thenReturn(List.of(dto1, dto2));

        mockMvc.perform(MockMvcRequestBuilders.get("/notes"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("Note 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].title").value("Note 2"));
    }

    @Test
    void getNoteById_shouldReturnNote() throws Exception {
        NoteResponseDto dto = new NoteResponseDto();
        dto.setId(1L);
        dto.setTitle("Note 1");
        dto.setAuthorName("Jan");

        when(noteService.getNoteById(1L)).thenReturn(dto);

        mockMvc.perform(MockMvcRequestBuilders.get("/notes/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Note 1"));
    }

    @Test
    void getNoteById_shouldReturnNotFound_whenNoteDoesNotExist() throws Exception {
        when(noteService.getNoteById(eq(999L)))
                .thenThrow(new ResourceNotFoundException("Note not found"));

        mockMvc.perform(MockMvcRequestBuilders.get("/notes/999"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string("Note not found"));
    }

    @Test
    void deleteNote_shouldReturnOk() throws Exception {
        doNothing().when(noteService).deleteNote(1L);

        mockMvc.perform(MockMvcRequestBuilders.delete("/notes/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
