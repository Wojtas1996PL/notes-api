package com.mateusz.notes.notes_api.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mateusz.notes.notes_api.dto.AuthorRequestDto;
import com.mateusz.notes.notes_api.dto.AuthorResponseDto;
import com.mateusz.notes.notes_api.exception.GlobalExceptionHandler;
import com.mateusz.notes.notes_api.exception.ResourceNotFoundException;
import com.mateusz.notes.notes_api.service.AuthorService;
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
class AuthorControllerTest {
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Mock
    private AuthorService authorService;

    @InjectMocks
    private AuthorController authorController;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        mockMvc = MockMvcBuilders.standaloneSetup(authorController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void createAuthor_shouldReturnAuthorResponseDto() throws Exception {
        AuthorResponseDto responseDto = new AuthorResponseDto();
        responseDto.setId(1L);
        responseDto.setName("Jan Kowalski");

        when(authorService.createAuthor(any(AuthorRequestDto.class))).thenReturn(responseDto);

        AuthorRequestDto requestDto = new AuthorRequestDto();
        requestDto.setName("Jan Kowalski");

        mockMvc.perform(MockMvcRequestBuilders.post("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Jan Kowalski"));
    }

    @Test
    void createAuthor_shouldReturnBadRequest_whenNameIsBlank() throws Exception {
        AuthorRequestDto requestDto = new AuthorRequestDto();
        requestDto.setName("");

        mockMvc.perform(MockMvcRequestBuilders.post("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void getAllAuthors_shouldReturnList() throws Exception {
        AuthorResponseDto dto1 = new AuthorResponseDto();
        dto1.setId(1L);
        dto1.setName("Jan");

        AuthorResponseDto dto2 = new AuthorResponseDto();
        dto2.setId(2L);
        dto2.setName("Anna");

        when(authorService.getAllAuthors()).thenReturn(List.of(dto1, dto2));

        mockMvc.perform(MockMvcRequestBuilders.get("/authors"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Jan"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Anna"));
    }

    @Test
    void getAuthorById_shouldReturnAuthor() throws Exception {
        AuthorResponseDto dto = new AuthorResponseDto();
        dto.setId(1L);
        dto.setName("Jan");

        when(authorService.getAuthorById(1L)).thenReturn(dto);

        mockMvc.perform(MockMvcRequestBuilders.get("/authors/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Jan"));
    }

    @Test
    void getAuthorById_shouldReturnNotFound_whenAuthorDoesNotExist() throws Exception {
        when(authorService.getAuthorById(eq(999L)))
                .thenThrow(new ResourceNotFoundException("Author not found"));

        mockMvc.perform(MockMvcRequestBuilders.get("/authors/999"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string("Author not found"));
    }
}
