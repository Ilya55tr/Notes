package com.example.Notes.mapper;


import com.example.Notes.dto.TaskCreateEditDto;
import com.example.Notes.entities.Task;
import com.example.Notes.repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TaskCreateEditMapper implements Mapper<TaskCreateEditDto, Task>{

    private final NoteRepository noteRepository;


    @Override
    public Task map(TaskCreateEditDto object) {
        Task toObject = new Task();
        copy(object, toObject);
        return toObject;
    }

    private void copy(TaskCreateEditDto object, Task toObject) {
        toObject.setTaskName(object.taskName());
        toObject.setTaskText(object.taskText());
        toObject.setNote(noteRepository.findById(object.noteId()).orElseThrow());
    }

    @Override
    public Task map(TaskCreateEditDto object, Task toObject) {
        copy(object, toObject);
        return toObject;
    }
}
