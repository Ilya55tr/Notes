package com.example.Notes.mapper;

import com.example.Notes.dto.NoteReadDto;
import com.example.Notes.entities.Note;
import org.springframework.stereotype.Component;

@Component

public class NoteReadMapper implements Mapper<Note, NoteReadDto>{

    @Override
    public NoteReadDto map(Note object) {
        return new NoteReadDto(object.getId(), object.getName(), object.getTasks());
    }
}
