package com.example.Notes.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "note")
@Builder
@Data
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "task_seq")
    @SequenceGenerator(name = "task_seq", sequenceName = "task_seq", allocationSize = 1)
    Integer id;

    String taskName;

    @Column(name = "text")
    String taskText;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false, name = "note_id")
    Note note;

//    public void addTask(){
//        note.html.addTask(this);
//    }
//
//    public void removeTask(){
//        note.html.removeTask(this);
//    }







}
