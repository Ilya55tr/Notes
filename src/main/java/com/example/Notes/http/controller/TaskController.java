package com.example.Notes.http.controller;

import com.example.Notes.dto.NoteReadDto;
import com.example.Notes.dto.TaskCreateEditDto;
import com.example.Notes.dto.TaskReadDto;
import com.example.Notes.mapper.NoteReadMapper;
import com.example.Notes.services.NoteService;
import com.example.Notes.services.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;
    private final NoteService noteService;
    private final NoteReadMapper noteReadMapper;

    @GetMapping("/{id}")
    public String findById(@PathVariable Integer id, Model model){
        model.addAttribute("notes", noteService.findAll());
        return taskService.findById(id).map(task ->{
            model.addAttribute("task",task);
            return "task/task";
        }).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable Integer id, @ModelAttribute TaskCreateEditDto task){
        return taskService.updateTask(id, task)
                .map(it -> "redirect:/notes/"+task.noteId())
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public String create(@ModelAttribute TaskCreateEditDto task, RedirectAttributes redirectAttributes){
        TaskReadDto dto= taskService.createTask(task);
//        if (true){
//            redirectAttributes.addFlashAttribute("task", task);
//            return "redirect:/tasks/create";
//        }
        return "redirect:/notes/"+dto.noteDto().id();
    }

    @GetMapping("/create")
    public String create(@ModelAttribute TaskCreateEditDto task, Model model){
        Object noteObj = model.getAttribute("note");
        if (noteObj instanceof NoteReadDto note) {
            task = new TaskCreateEditDto(
                    task.taskName(),
                    task.taskText(),
                    note.id()
            );
        }
        model.addAttribute("task", task);
        model.addAttribute("notes", noteService.findAll());
        return "task/create";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Integer id){
        TaskReadDto dto= taskService.findById(id).orElseThrow();

        if (!taskService.deleteTask(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return "redirect:/notes/"+dto.noteDto().id();
    }
}
