package com.trilogyed.noteservice.dao;

import com.trilogyed.noteservice.model.Note;

import java.util.List;

public interface NoteDao {
    Note createNote(Note note);
    Note getNote(int note_id);
    List<Note> getNotesByBookId(int bookId);
    List<Note> getAllNotes();
    String updateBook(Note note);
    String deleteBook(int id);
}
