package com.example.Notes.repository;

import com.example.Notes.entities.Note;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.util.Optional;

public interface NoteRepository extends JpaRepository<Note, Integer> {

//    // искать Notes по имени
//    List<Note> findByName(String name);
//
//    // искать Notes по имени Task
//    List<Note> findByTasks_TaskName(String taskName);
//
//    // искать Notes по тексту Task
//    List<Note> findByTasks_TaskText(String taskText);
//
//    @Modifying
//    @Transactional
//    @Query("UPDATE Note n SET n.name = :newName WHERE n.name = :oldName")
//    int updateName(@Param("oldName") String oldName, @Param("newName") String newName);

    @EntityGraph(attributePaths = {"tasks"})
    @NonNull
    Optional<Note> findById(@NonNull Integer id);


}
