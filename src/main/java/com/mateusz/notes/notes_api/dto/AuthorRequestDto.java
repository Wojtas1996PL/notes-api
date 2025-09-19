package com.mateusz.notes.notes_api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthorRequestDto {
    @NotBlank
    private String name;
}
