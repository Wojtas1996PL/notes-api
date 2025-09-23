package com.mateusz.notes.notes_api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.mateusz.notes.notes_api.dto.AuthorRequestDto;
import com.mateusz.notes.notes_api.dto.AuthorResponseDto;
import com.mateusz.notes.notes_api.exception.ResourceNotFoundException;
import com.mateusz.notes.notes_api.mapper.AuthorMapper;
import com.mateusz.notes.notes_api.model.Author;
import com.mateusz.notes.notes_api.repository.AuthorRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AuthorServiceTest {
    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private AuthorMapper authorMapper;

    @InjectMocks
    private AuthorService authorService;

    @Test
    void createAuthor_shouldReturnAuthorResponseDto() {
        AuthorRequestDto requestDto = new AuthorRequestDto();
        requestDto.setName("Jan");

        Author authorEntity = new Author();
        authorEntity.setName("Jan");

        Author savedAuthor = new Author();
        savedAuthor.setId(1L);
        savedAuthor.setName("Jan");

        AuthorResponseDto responseDto = new AuthorResponseDto();
        responseDto.setId(1L);
        responseDto.setName("Jan");

        when(authorMapper.toEntity(requestDto)).thenReturn(authorEntity);
        when(authorRepository.save(authorEntity)).thenReturn(savedAuthor);
        when(authorMapper.toDto(savedAuthor)).thenReturn(responseDto);

        AuthorResponseDto result = authorService.createAuthor(requestDto);

        assertEquals(1L, result.getId());
        assertEquals("Jan", result.getName());
    }

    @Test
    void getAllAuthors_shouldReturnListOfDtos() {
        Author author1 = new Author();
        author1.setId(1L);
        author1.setName("Jan");

        Author author2 = new Author();
        author2.setId(2L);
        author2.setName("Anna");

        AuthorResponseDto dto1 = new AuthorResponseDto();
        dto1.setId(1L);
        dto1.setName("Jan");

        AuthorResponseDto dto2 = new AuthorResponseDto();
        dto2.setId(2L);
        dto2.setName("Anna");

        when(authorRepository.findAll()).thenReturn(List.of(author1, author2));
        when(authorMapper.toDto(author1)).thenReturn(dto1);
        when(authorMapper.toDto(author2)).thenReturn(dto2);

        List<AuthorResponseDto> result = authorService.getAllAuthors();

        assertEquals(2, result.size());
        assertEquals("Jan", result.get(0).getName());
        assertEquals("Anna", result.get(1).getName());
    }

    @Test
    void getAuthorById_shouldReturnDto() {
        Author author = new Author();
        author.setId(1L);
        author.setName("Jan");

        AuthorResponseDto dto = new AuthorResponseDto();
        dto.setId(1L);
        dto.setName("Jan");

        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        when(authorMapper.toDto(author)).thenReturn(dto);

        AuthorResponseDto result = authorService.getAuthorById(1L);

        assertEquals(1L, result.getId());
        assertEquals("Jan", result.getName());
    }

    @Test
    void getAuthorById_shouldThrowExceptionWhenNotFound() {
        when(authorRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> authorService.getAuthorById(999L));
    }

    @Test
    void getAuthorEntityById_shouldReturnEntity() {
        Author author = new Author();
        author.setId(1L);
        author.setName("Jan");

        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));

        Author result = authorService.getAuthorEntityById(1L);

        assertEquals(1L, result.getId());
        assertEquals("Jan", result.getName());
    }

    @Test
    void getAuthorEntityById_shouldThrowExceptionWhenNotFound() {
        when(authorRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> authorService.getAuthorEntityById(999L));
    }
}
