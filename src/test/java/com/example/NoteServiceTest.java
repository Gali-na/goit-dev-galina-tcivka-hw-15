package com.example;

import com.example.exception.NoteInformationException;
import com.example.exception.NoteNotFoundException;
import com.example.model.Note;
import com.example.service.NoteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest

public class NoteServiceTest {
    @Autowired
    private NoteService noteService;

    @Test
    void listAll_AddOneNote_GetRightResult() throws NoteInformationException {
        int countNotBeforeAdding = noteService.listAll().size();
        Note note = new Note();
        note.setTitle("gym");
        note.setContentNote("I'm going to the gym");
        noteService.add(note);
        int countNoteAfterAdding = noteService.listAll().size();
        assertEquals(countNotBeforeAdding + 1, countNoteAfterAdding);
    }

    @Test
    void add_NoteNull_ThrowException() {

        assertThrows(NoteInformationException.class, () -> noteService.add(null));
    }

    @Test
    void add_NoteTitleEmpty_ThrowException() {
        Note note = new Note();
        note.setTitle("");
        assertThrows(NoteInformationException.class, () -> noteService.add(note));
    }

    @Test
    void add_NoteValid_PositiveResult() throws NoteInformationException {
        int countNotBeforeAdding = noteService.listAll().size();
        Note note = new Note();
        note.setTitle("housework");
        note.setContentNote("I'm doing homework now");
        noteService.add(note);
        int countNotAfterAdding = noteService.listAll().size();
        assertEquals(countNotBeforeAdding + 1, countNotAfterAdding);
    }

    @Test
    void add_NoteValid_CheckProperties() throws  NoteInformationException {
        int countNotBeforeAdding = noteService.listAll().size();
        Note note = new Note();
        note.setTitle("car wash");
        note.setContentNote("taking cars to the car wash");
        Note newNote = noteService.add(note);
        assertEquals("car wash", newNote.getTitle());
        assertEquals("taking cars to the car wash", newNote.getContentNote());
    }

    @Test
    void deleteById_IdNotExist_ThrowException() {
        assertThrows(NoteNotFoundException.class, () -> noteService.deleteById(0));
    }

    @Test
    void deleteById_IdExist_PositiveResult() throws NoteNotFoundException, NoteInformationException {
        Note note = new Note();
        note.setTitle("English language");
        note.setContentNote("learn English");
        Note newNote = noteService.add(note);
        int countNoteAfterAdding = noteService.listAll().size();
        noteService.deleteById(newNote.getId());
        int countNoteAfterDelete = noteService.listAll().size();
        assertEquals(countNoteAfterAdding - 1, countNoteAfterDelete);

    }

    @Test
    void update_NoteExist_PositiveResult() throws NoteNotFoundException, NoteInformationException {
        Note note = new Note();
        note.setTitle("rest");
        note.setContentNote("invite friends");
        Note newNote = noteService.add(note);
        newNote.setTitle("holiday");
        noteService.update(newNote);
        assertEquals(noteService.getById(newNote.getId()).getTitle(), "holiday");
    }

    @Test
    void update_NoteNotExist_ThrowException() {
        Note note = new Note();
        note.setId(0);
        note.setTitle("rest");
        note.setContentNote("invite friends");
        assertThrows(NoteNotFoundException.class, () -> noteService.update(note));
    }

    @Test
    void update_NoteTitleEmpty_ThrowException() throws NoteInformationException, NoteNotFoundException {
        Note note = new Note();
        note.setTitle("rest");
        note.setContentNote("invite friends");
        Note newNote = noteService.add(note);
        newNote.setTitle("");
        assertThrows(NoteInformationException.class, () ->  noteService.update(newNote));
    }
    @Test
    void getById_IdNotExist_ThrowException() {
        assertThrows(NoteNotFoundException.class, () -> noteService.getById(0));
    }

    @Test
    void getById_IdExist_PositiveResult() throws NoteNotFoundException, NoteInformationException {
        Note note = new Note();
        note.setTitle("dog");
        note.setContentNote("walk with the dog");
        Note newNote = noteService.add(note);
        assertEquals(noteService.getById(newNote.getId()).getTitle(), "dog");
        assertEquals(noteService.getById(newNote.getId()).getContentNote(), "walk with the dog");
    }

}