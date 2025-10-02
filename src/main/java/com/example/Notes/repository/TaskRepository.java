package com.example.Notes.repository;

import com.example.Notes.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Integer> {

//    // искать Tasks по имени
//    List<Task> findByTaskName(String taskName);
//
//    // искать Tasks по тексту
//    List<Task> findByTaskText(String taskText);
//
//    @Modifying(clearAutomatically = true)
//    @Transactional
//    @Query("UPDATE Task t SET t.taskText = :taskText " +
//           "WHERE t.id = :taskId AND t.note.html.name = :noteName")
//    int updateTaskTextByNoteNameAndTaskName(@Param("taskText") String taskText,
//                                            @Param("taskID") Integer taskId,
//                                            @Param("noteName") String noteName);



}
