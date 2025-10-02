package com.example.Notes.http.controller;

import com.example.Notes.dto.NoteCreateEditDto;
import com.example.Notes.dto.NoteReadDto;
import com.example.Notes.services.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/notes")
@RequiredArgsConstructor
public class NoteController {
    private final NoteService noteService;

    @GetMapping
    public String findAll(Model model){
        model.addAttribute("notes", noteService.findAll());
        return "note/notes";
    }


    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Integer id, Model model){
//        model.addAttribute("note.html", noteService.findById(id));
//        return "note.html/note.html";
        return noteService.findById(id).map(note ->{
            model.addAttribute("note", note);
            return "note/note";
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable("id") Integer id, @ModelAttribute NoteCreateEditDto note){
        return  noteService.updateNote(id, note)
                .map(it -> "redirect:/notes/{id}")
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping()
    public String create(@ModelAttribute NoteCreateEditDto note, RedirectAttributes redirectAttributes){
        NoteReadDto noteDto = noteService.createNote(note);
        if (true){
            redirectAttributes.addFlashAttribute("note", note);
            return "redirect:/notes/create";
        }
        return "redirect:/notes/"+ noteDto.id();
    }

    @GetMapping("/create")
    public String create(Model model, @ModelAttribute NoteCreateEditDto note){
       Object noteObj = model.getAttribute("note");

       if (noteObj instanceof NoteCreateEditDto noteDto){
           model.addAttribute("note", noteObj);
       }else {
           model.addAttribute("note", note);
       }
        return  "note/create";
    }

    @GetMapping("/{id}/createTask")
    public String createTask(@PathVariable Integer id, RedirectAttributes redirectAttributes){
        return noteService.findById(id).map(note ->{
            redirectAttributes.addFlashAttribute("note", note);
            return "redirect:/tasks/create";
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Integer id){
        if (!noteService.deleteNote(id)){
            throw  new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return "redirect:/notes";
    }
}
