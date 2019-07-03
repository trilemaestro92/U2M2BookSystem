package com.trilogyed.noteservice.dao;

import com.trilogyed.noteservice.model.Note;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class NoteDaoTest {

    @Autowired
    protected NoteDao noteDao;

    @Before
    public void setup() {
        List<Note> allNotes = noteDao.getAllNotes();

        allNotes.stream()
                .forEach(note -> noteDao.deleteBook(note.getNoteId()));

        Note note1 = new Note(4, "Note for book 4");
        Note note2 = new Note(4, "Note for book 4");
        Note note3 = new Note(5, "Note for book 5");
        Note note4 = new Note(6, "Note for book 6");

        noteDao.createNote(note1);
        noteDao.createNote(note2);
        noteDao.createNote(note3);
        noteDao.createNote(note4);

    }

    @Test
    public void createGetDeleteNote() {
        Note note1 = new Note(2, "this is a note for book_id 2");

        Note noteInDb = noteDao.createNote(note1);

        int id = noteInDb.getNoteId();
        note1.setNoteId(id);

        assertEquals(note1, noteInDb);

        Note noteFound = noteDao.getNote(id);
        assertEquals(note1, noteFound);

        String noteDeletedResponse = noteDao.deleteBook(id);
        assertEquals("Note with note_id " + id + " has been deleted.", noteDeletedResponse);

        String noteNotDeletedResponse = noteDao.deleteBook(0);
        assertEquals("No notes deleted. No note with note_id 0 was found.", noteNotDeletedResponse);
    }

    @Test
    public void getAllNotes() {
        List<Note> allNotes = noteDao.getAllNotes();

        int numAllNotes = allNotes.size();
        assertEquals(4, numAllNotes);
    }

    @Test
    public void getAllNotesByBookId() {
        List<Note> allNotesByBookId = noteDao.getNotesByBookId(4);

        int numAllNotes = allNotesByBookId.size();
        assertEquals(2, numAllNotes);
    }

    @Test
    public void updateNote() {
        Note note = new Note(6, "Note for book 6");

        Note noteAdded = noteDao.createNote(note);

        int id = noteAdded.getNoteId();

        note.setNoteId(id);
        note.setNote("Changing the note for book 6");

        String updatedResponse = noteDao.updateBook(note);
        Note updatedNote = noteDao.getNote(id);

        assertEquals(note, updatedNote);
        assertEquals("Note with note_id " + id + " has been updated.", updatedResponse);

        note.setNoteId(0);
        String noteNotFoundMsg = noteDao.updateBook(note);

        assertEquals("No notes updated. No note with note_id 0 was found.", noteNotFoundMsg);
    }

}
