package com.trilogyed.noteservice.controller;

import com.trilogyed.noteservice.dao.NoteDaoJdbcTemplateImpl;
import com.trilogyed.noteservice.exception.NotFoundException;
import com.trilogyed.noteservice.model.Note;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/note")
public class NoteController {

    @Autowired
    NoteDaoJdbcTemplateImpl noteDao;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Note createNote(@RequestBody @Valid Note note) {
        return noteDao.createNote(note);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Note getNoteById(@PathVariable("id") int id) {
        Note note = noteDao.getNote(id);

        if (note == null) {
            throw new NotFoundException("No note was found with note_id " + id);
        }
        return note;
    }

    @GetMapping("/book/{book_id}")
    @ResponseStatus(HttpStatus.OK)
    public List<Note> getNotesForBookId(@PathVariable("book_id") int book_id) {
        List<Note> notesForBookId = noteDao.getNotesByBookId(book_id);

        if (notesForBookId.size() == 0) {
            throw new NotFoundException("No notes found for book_id "  + book_id);
        }

        return notesForBookId;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Note> getAllNotes() {
        return noteDao.getAllNotes();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String updateNote(@PathVariable("id") int id, @RequestBody @Valid Note note) {
        if (note.getNoteId() == 0) {
            note.setNoteId(id);
        }
        if (id != note.getNoteId()) {
            throw new IllegalArgumentException("Note ID on path must match the ID in the Note object");
        }

        String updateResponse = noteDao.updateBook(note);
        String response = updateResponse;

        return response;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String deleteNote(@PathVariable("id") int id) {
        return noteDao.deleteBook(id);
    }


}
