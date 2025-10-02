package com.example.Notes.integration;

import com.example.Notes.dto.TaskCreateEditDto;
import com.example.Notes.dto.TaskReadDto;
import com.example.Notes.entities.Note;
import com.example.Notes.entities.Task;
import com.example.Notes.repository.NoteRepository;
import com.example.Notes.repository.TaskRepository;
import com.example.Notes.services.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest()
@ActiveProfiles("test")
@Transactional
@DisplayName("TaskService Integration Tests")
class TaskServiceTest {

    @Autowired
    private TaskService taskService;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private NoteRepository noteRepository;

    private Note testNote;

    @BeforeEach
    void setUp() {
        // Очищаем базу перед каждым тестом
        taskRepository.deleteAll();
        noteRepository.deleteAll();

        // Создаем тестовую заметку
        testNote = Note.builder()
                .name("Integration Test Note")
                .build();
        testNote = noteRepository.save(testNote);
    }

    @Test
    @DisplayName("Should create and retrieve task")
    void createAndRetrieveTask() {
        // Given
        TaskCreateEditDto createDto = new TaskCreateEditDto(
                "Integration Task",
                "Integration task description",
                testNote.getId()
        );

        // When
        TaskReadDto created = taskService.createTask(createDto);
        Optional<TaskReadDto> retrieved = taskService.findById(created.id());

        // Then
        assertThat(retrieved).isPresent();
        assertThat(retrieved.get().taskName()).isEqualTo("Integration Task");
        assertThat(retrieved.get().taskText()).isEqualTo("Integration task description");
        assertThat(retrieved.get().noteDto().id()).isEqualTo(testNote.getId());

        // Проверяем, что задача действительно добавлена в Note
        Note updatedNote = noteRepository.findById(testNote.getId()).orElseThrow();
        assertThat(updatedNote.getTasks()).hasSize(1);
        assertThat(updatedNote.getTasks().get(0).getTaskName()).isEqualTo("Integration Task");
    }

    @Test
    @DisplayName("Should update task successfully")
    void updateTask() {
        // Given - создаем задачу
        TaskCreateEditDto createDto = new TaskCreateEditDto(
                "Original Task",
                "Original description",
                testNote.getId()
        );
        TaskReadDto created = taskService.createTask(createDto);

        // When - обновляем задачу
        TaskCreateEditDto updateDto = new TaskCreateEditDto(
                "Updated Task",
                "Updated description",
                testNote.getId()
        );
        Optional<TaskReadDto> updated = taskService.updateTask(created.id(), updateDto);

        // Then
        assertThat(updated).isPresent();
        assertThat(updated.get().taskName()).isEqualTo("Updated Task");
        assertThat(updated.get().taskText()).isEqualTo("Updated description");

        // Проверяем, что в базе сохранены изменения
        Task taskInDb = taskRepository.findById(created.id()).orElseThrow();
        assertThat(taskInDb.getTaskName()).isEqualTo("Updated Task");
        assertThat(taskInDb.getTaskText()).isEqualTo("Updated description");
    }

    @Test
    @DisplayName("Should move task to different note")
    void moveTaskToDifferentNote() {
        // Given - создаем две заметки и задачу в первой
        Note secondNote = Note.builder()
                .name("Second Note")
                .build();
        secondNote = noteRepository.save(secondNote);

        TaskCreateEditDto createDto = new TaskCreateEditDto(
                "Task to move",
                "This task will be moved",
                testNote.getId()
        );
        TaskReadDto created = taskService.createTask(createDto);

        // When - перемещаем задачу во вторую заметку
        TaskCreateEditDto updateDto = new TaskCreateEditDto(
                "Task to move",
                "This task will be moved",
                secondNote.getId()
        );
        Optional<TaskReadDto> updated = taskService.updateTask(created.id(), updateDto);

        // Then
        assertThat(updated).isPresent();
        assertThat(updated.get().noteDto().id()).isEqualTo(secondNote.getId());

        // Проверяем состояние обеих заметок
        Note firstNoteUpdated = noteRepository.findById(testNote.getId()).orElseThrow();
        Note secondNoteUpdated = noteRepository.findById(secondNote.getId()).orElseThrow();

        assertThat(secondNoteUpdated.getTasks()).hasSize(1);
        assertThat(secondNoteUpdated.getTasks().get(0).getId()).isEqualTo(created.id());
    }

    @Test
    @DisplayName("Should delete task and remove from note")
    void deleteTask() {
        // Given
        TaskCreateEditDto createDto = new TaskCreateEditDto(
                "Task to delete",
                "This task will be deleted",
                testNote.getId()
        );
        TaskReadDto created = taskService.createTask(createDto);

        // When
        boolean deleted = taskService.deleteTask(created.id());

        // Then
        assertThat(deleted).isTrue();
        assertThat(taskRepository.findById(created.id())).isEmpty();

        // Проверяем, что задача удалена из Note
        Note updatedNote = noteRepository.findById(testNote.getId()).orElseThrow();
        assertThat(updatedNote.getTasks()).isEmpty();
    }

    @Test
    @DisplayName("Should handle cascade operations correctly")
    void cascadeOperations() {
        // Given - создаем заметку с несколькими задачами
        TaskCreateEditDto task1Dto = new TaskCreateEditDto(
                "Task 1",
                "Description 1",
                testNote.getId()
        );
        TaskCreateEditDto task2Dto = new TaskCreateEditDto(
                "Task 2",
                "Description 2",
                testNote.getId()
        );

        TaskReadDto task1 = taskService.createTask(task1Dto);
        TaskReadDto task2 = taskService.createTask(task2Dto);

        // When - удаляем заметку (это должно удалить все задачи из-за cascade)
        noteRepository.deleteById(testNote.getId());
        noteRepository.flush();

        // Then
        assertThat(taskRepository.findById(task1.id())).isEmpty();
        assertThat(taskRepository.findById(task2.id())).isEmpty();
    }

    @Test
    @DisplayName("Should maintain bidirectional relationship consistency")
    void bidirectionalRelationshipConsistency() {
        // Given & When
        TaskCreateEditDto createDto = new TaskCreateEditDto(
                "Relationship Test",
                "Testing bidirectional relationship",
                testNote.getId()
        );
        TaskReadDto created = taskService.createTask(createDto);

        // Then - проверяем двустороннюю связь
        Task task = taskRepository.findById(created.id()).orElseThrow();
        Note note = noteRepository.findById(testNote.getId()).orElseThrow();

        assertThat(task.getNote()).isEqualTo(note);
        assertThat(note.getTasks()).contains(task);
        assertThat(note.getTasks().get(0).getNote()).isEqualTo(note);
    }
}