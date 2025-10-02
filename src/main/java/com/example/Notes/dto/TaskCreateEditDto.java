package com.example.Notes.dto;

import lombok.experimental.FieldNameConstants;

@FieldNameConstants
public record TaskCreateEditDto(String taskName, String taskText, Integer noteId) {
}
