package com.mateusz.notes.notes_api.mapper;

import com.mateusz.notes.notes_api.dto.NoteRequestDto;
import com.mateusz.notes.notes_api.dto.NoteResponseDto;
import com.mateusz.notes.notes_api.model.Note;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NoteMapper {

    NoteResponseDto toDto(Note note);

    Note toEntity(NoteRequestDto noteRequestDto);
}
