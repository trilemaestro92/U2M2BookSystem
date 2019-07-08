package com.trilogyed.bookservice.model;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

public class Note {

    private int noteId;
    @NotNull(message = "book id can not be null")
    private int bookId;
    @Size(max = 255, min = 1, message = "note cannot be empty and not exceed 255 character")
    private String note;

    public Note() {
    }

    public Note(int book_id, String note) {
        this.bookId = book_id;
        this.note = note;
    }

    public int getNoteId() {
        return noteId;
    }

    public void setNoteId(int note_id) {
        this.noteId = note_id;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int book_id) {
        this.bookId = book_id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Note note1 = (Note) o;
        return noteId == note1.noteId &&
                bookId == note1.bookId &&
                note.equals(note1.note);
    }

    @Override
    public int hashCode() {
        return Objects.hash(noteId, bookId, note);
    }

    @Override
    public String toString() {
        return "Note{" +
                "note_id=" + noteId +
                ", book_id=" + bookId +
                ", note='" + note + '\'' +
                '}';
    }
}
