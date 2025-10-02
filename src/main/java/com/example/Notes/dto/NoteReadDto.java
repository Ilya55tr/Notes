package com.example.Notes.dto;


import com.example.Notes.entities.Task;

import java.util.List;

public record NoteReadDto(Integer id, String noteName, List<Task> tasks) {

}
