package com.trilogyed.notequeue.consumer.util.messages;


import javax.validation.constraints.Size;

public class NoteListEntry {
    private int noteId;
    private int bookId;
//    @Size(max = 255, min= 1, message = "note cannot exceed 255 characters")
    private String note;

    public NoteListEntry(){}

    public NoteListEntry(int noteId, int bookId, String note) {
        this.noteId = noteId;
        this.bookId = bookId;
        this.note = note;
    }

    public int getNoteId() {
        return noteId;
    }

    public void setNoteId(int noteId) {
        this.noteId = noteId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "NoteListEntry{" +
                "noteId=" + noteId +
                ", bookId=" + bookId +
                ", note='" + note + '\'' +
                '}';
    }
}
