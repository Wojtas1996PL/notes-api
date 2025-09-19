package com.mateusz.notes.notes_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoteRequestDto {
    @NotBlank
    private String title;

    private String content;

    @NotNull
    private Long authorId;
}
