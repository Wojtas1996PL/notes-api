package com.mateusz.notes.notes_api.mapper;

import com.mateusz.notes.notes_api.dto.NoteRequestDto;
import com.mateusz.notes.notes_api.dto.NoteResponseDto;
import com.mateusz.notes.notes_api.model.Note;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface NoteMapper {

    @Mapping(source = "author.name", target = "authorName")
    NoteResponseDto toDto(Note note);

    Note toEntity(NoteRequestDto noteRequestDto);
}
