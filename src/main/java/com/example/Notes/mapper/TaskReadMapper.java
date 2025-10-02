package com.example.Notes.mapper;

import com.example.Notes.dto.NoteReadDto;
import com.example.Notes.dto.TaskReadDto;
import com.example.Notes.entities.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class TaskReadMapper implements Mapper<Task, TaskReadDto>{

    private final NoteReadMapper noteReadMapper;

    @Override
    public TaskReadDto map(Task object) {
        return new TaskReadDto(object.getId(), object.getTaskName(), object.getTaskText(), getNoteDto(object));
    }

    private NoteReadDto getNoteDto(Task object){
        return noteReadMapper.map(object.getNote());
    }



}
