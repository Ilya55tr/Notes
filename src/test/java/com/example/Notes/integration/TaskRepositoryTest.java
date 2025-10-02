package com.example.Notes.integration;

import com.example.Notes.annotation.RepositoryTest;
import com.example.Notes.entities.Note;
import com.example.Notes.entities.Task;
import com.example.Notes.repository.NoteRepository;
import com.example.Notes.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.TestConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RepositoryTest
@RequiredArgsConstructor
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class TaskRepositoryTest {


    private final NoteRepository noteRepository;
    private final TaskRepository taskRepository;

    @Test
    @Transactional
    void findAllTest() {
        // Проверить сохранение
        List<Task> tasks = taskRepository.findAll();
        assertThat(tasks).hasSize(15);
        assertThat(tasks.getFirst().getNote().getName()).isEqualTo("Рабочие задачи");
    }

    @Test
    @Transactional
    void saveTask(){
        Note note = noteRepository.findById(1).orElseThrow();

        Task task = Task.builder().taskName("hui").taskText("hui hui").note(note).build();
        Task taskSave =taskRepository.save(task);

        assertThat(taskSave.getNote().getId()).isEqualTo(note.getId());
    }
}
