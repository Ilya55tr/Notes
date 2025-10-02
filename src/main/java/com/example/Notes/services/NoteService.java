package com.example.Notes.services;


import com.example.Notes.dto.NoteCreateEditDto;
import com.example.Notes.dto.NoteReadDto;
import com.example.Notes.mapper.NoteCreateEditMapper;
import com.example.Notes.mapper.NoteReadMapper;
import com.example.Notes.repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NoteService {

    private final NoteRepository noteRepository;
    private final NoteReadMapper noteReadMapper;
    private final NoteCreateEditMapper noteCreateEditMapper;

    public List<NoteReadDto> findAll(){
        return noteRepository.findAll().stream().map(noteReadMapper::map).toList();
    }

    public Optional<NoteReadDto> findById(Integer id){
        return noteRepository.findById(id).map(noteReadMapper::map);
    }

    @Transactional
    public NoteReadDto createNote(NoteCreateEditDto noteDto){
        return Optional.of(noteDto)
                .map(noteCreateEditMapper::map)
                .map(noteRepository::save)
                .map(noteReadMapper::map).orElseThrow();
    }


    @Transactional
    public Optional<NoteReadDto> updateNote(Integer id, NoteCreateEditDto noteDto){
        return noteRepository.findById(id)
                .map(entity -> noteCreateEditMapper.map(noteDto, entity))
                .map(noteRepository::saveAndFlush)
                .map(noteReadMapper::map);

    }

    @Transactional
    public boolean deleteNote(Integer id){
        return noteRepository.findById(id)
                .map(entity -> {
                    noteRepository.delete(entity);
                    noteRepository.flush();
                    return true;
                }).orElse(false);
    }
}
