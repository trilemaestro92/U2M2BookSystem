package com.trilogyed.noteservice.dao;

import com.trilogyed.noteservice.model.Note;

import java.util.List;

public interface NoteDao {
    public Note createNote(Note note);
    public Note getNote(int note_id);
    public List<Note> getNotesByBookId(int bookId);
    public List<Note> getAllNotes();
    public String updateBook(Note note);
    public String deleteBook(int id);
}
