package com.mateusz.notes.notes_api.service;

import com.mateusz.notes.notes_api.dto.AuthorRequestDto;
import com.mateusz.notes.notes_api.dto.AuthorResponseDto;
import com.mateusz.notes.notes_api.exception.ResourceNotFoundException;
import com.mateusz.notes.notes_api.mapper.AuthorMapper;
import com.mateusz.notes.notes_api.model.Author;
import com.mateusz.notes.notes_api.repository.AuthorRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorService {
    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    public AuthorResponseDto createAuthor(AuthorRequestDto dto) {
        Author author = authorMapper.toEntity(dto);
        Author saved = authorRepository.save(author);
        return authorMapper.toDto(saved);
    }

    public List<AuthorResponseDto> getAllAuthors() {
        return authorRepository.findAll().stream()
                .map(authorMapper::toDto)
                .toList();
    }

    public AuthorResponseDto getAuthorById(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found"));
        return authorMapper.toDto(author);
    }

    public Author getAuthorEntityById(Long id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found"));
    }
}
