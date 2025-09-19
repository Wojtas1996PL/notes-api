package com.mateusz.notes.notes_api.controller;

import com.mateusz.notes.notes_api.dto.AuthorRequestDto;
import com.mateusz.notes.notes_api.dto.AuthorResponseDto;
import com.mateusz.notes.notes_api.service.AuthorService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authors")
@RequiredArgsConstructor
public class AuthorController {
    private final AuthorService authorService;

    @PostMapping
    public ResponseEntity<AuthorResponseDto> createAuthor(@Valid @RequestBody AuthorRequestDto dto) {
        return ResponseEntity.ok(authorService.createAuthor(dto));
    }

    @GetMapping
    public List<AuthorResponseDto> getAllAuthors() {
        return authorService.getAllAuthors();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorResponseDto> getAuthorById(@PathVariable Long id) {
        return ResponseEntity.ok(authorService.getAuthorById(id));
    }
}
