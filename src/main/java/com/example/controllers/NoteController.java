package com.example.controllers;

import com.example.exception.NoteInformationException;
import com.example.exception.NoteNotFoundException;
import com.example.model.Note;
import com.example.service.NoteService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@RequiredArgsConstructor
@RequestMapping(value="/note")
@RestController
public class NoteController {
    private final NoteService noteService;
    @GetMapping(value = "/list")
    public ModelAndView listAll() {
        ModelAndView modelAndView = new ModelAndView("list");
        modelAndView.addObject("notes", noteService.listAll());
        return modelAndView;
    }
    @GetMapping(value = "/create")
    public ModelAndView createNote(){
        return new ModelAndView("create");
    }
    @PostMapping(value = "/create")
    public void createNote(@RequestParam(name = "title") String title,
                           @RequestParam(name = "content") String content, HttpServletResponse http ) throws IOException {
        Note note = new Note();
        note.setTitle(title);
        note.setContentNote(content);
        try {
            noteService.add(note);
        } catch (NoteInformationException e) {
            http.sendRedirect("/note/createBadResponse");
            return;
        }
        http.sendRedirect("/note/list");
    }
    @GetMapping(value = "/delete/{id}")
    public void deleteNote(@PathVariable ("id") long id, HttpServletResponse http) throws IOException, NoteNotFoundException {
        noteService.deleteById(id);
        http.sendRedirect("/note/list");
    }

    @GetMapping(value = "/edit")
    public ModelAndView editNote(@RequestParam ("id") long id) throws NoteNotFoundException {
        Note byId = noteService.getById(id);
        ModelAndView modelAndView = new ModelAndView("edit");
        modelAndView.addObject(byId);
        return modelAndView;

    }
    @PostMapping(value ="/edit")
    public void editNote(@RequestParam(name = "id") long id,
                         @RequestParam(name = "title") String title,
                         @RequestParam(name = "content") String content, HttpServletResponse http)
            throws IOException, NoteNotFoundException {
        Note note = new Note();
        note.setId(id);
        note.setContentNote(content);
        note.setTitle(title);
        try {
            noteService.update(note);
        } catch (NoteInformationException e) {
            http.sendRedirect("/note/createBadResponse");
            return;
        }
        http.sendRedirect("/note/list");
    }
    @GetMapping(value ="/createBadResponse")
    public ModelAndView getCreateBadResponse() {
        ModelAndView modelAndView = new ModelAndView("createBadResponse");
        return modelAndView;
    }
}
