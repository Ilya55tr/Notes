package com.example.Notes.mapper;

import com.example.Notes.dto.NoteCreateEditDto;
import com.example.Notes.entities.Note;
import org.springframework.stereotype.Component;

@Component
public class NoteCreateEditMapper implements Mapper<NoteCreateEditDto, Note>{
    @Override
    public Note map(NoteCreateEditDto object) {
        Note note = new Note();
        copy(object, note);
        return note;
    }

    private static void copy(NoteCreateEditDto object, Note toObject) {
        toObject.setName(object.noteName());
    }


    @Override
    public Note map(NoteCreateEditDto object, Note toObject) {
        copy(object, toObject);
        return toObject;
    }


}
