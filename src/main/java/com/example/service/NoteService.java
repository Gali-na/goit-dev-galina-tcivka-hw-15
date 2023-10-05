package com.example.service;


import com.example.exception.NoteInformationException;
import com.example.exception.NoteNotFoundException;
import com.example.model.Note;
import com.example.repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NoteService {
    private final NoteRepository noteRepository;

    public List<Note> listAll() {

        return noteRepository.findAll();
    }


    public Note add(Note note) throws NoteInformationException {
        if(note==null) {
            throw new NoteInformationException("note can't be null");
        }
        if ( (note.getTitle() == null || note.getTitle().equals(""))){
            throw new NoteInformationException("title of note is incorrect");
        }
        return noteRepository.save(note);
    }


    public void deleteById(long id) throws NoteNotFoundException {
        if (!noteRepository.existsById(id)){
            throw new NoteNotFoundException(id);
        }
        noteRepository.deleteById(id);
    }


    public Note update(Note note) throws NoteNotFoundException, NoteInformationException {
        if (note == null) {
            throw new NoteNotFoundException();
        }
        if (note.getTitle() == null || note.getTitle().equals("")) {
            throw new NoteInformationException("title of note is incorrect");
        }
        Optional<Note> noteById = noteRepository.findById(note.getId());
        Note currentNote = noteById.orElseThrow(() -> new NoteNotFoundException(note.getId()));
        currentNote.setTitle(note.getTitle());
        currentNote.setContentNote(note.getContentNote());
        noteRepository.save(currentNote);
        return currentNote;

    }

    public Note getById(long id) throws NoteNotFoundException {
        Optional<Note> noteById = noteRepository.findById(id);
        return noteById.orElseThrow(() -> new NoteNotFoundException(id));
    }
}
