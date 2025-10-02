package com.example.Notes.dto;


public record TaskReadDto(Integer id, String taskName, String taskText, NoteReadDto noteDto) {

}
