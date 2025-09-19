package com.mateusz.notes.notes_api.mapper;

import com.mateusz.notes.notes_api.dto.AuthorRequestDto;
import com.mateusz.notes.notes_api.dto.AuthorResponseDto;
import com.mateusz.notes.notes_api.model.Author;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthorMapper {

    AuthorResponseDto toDto(Author author);

    Author toEntity(AuthorRequestDto authorRequestDto);
}
