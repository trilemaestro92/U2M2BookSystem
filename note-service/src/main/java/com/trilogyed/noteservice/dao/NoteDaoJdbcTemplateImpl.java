package com.trilogyed.noteservice.dao;

import com.trilogyed.noteservice.model.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.RowSet;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class NoteDaoJdbcTemplateImpl implements NoteDao {

    @Autowired
    private JdbcTemplate noteJdbc;


    public NoteDaoJdbcTemplateImpl(JdbcTemplate jdbcTemplate) {
        this.noteJdbc = jdbcTemplate;
    }

    private static final String INSERT_NOTE =
            "insert into note (book_id, note) values (?, ?)";

    private static final String SELECT_NOTE =
            "select * from note where note_id = ?";

    private static final String SELECT_ALL_NOTES =
            "select * from note";

    private static final String SELECT_ALL_NOTES_BY_BOOK_ID =
            "select * from note where book_id = ?";

    private static final String UPDATE_BOOK =
            "update note set book_id = ?, note = ? where note_id = ?";

    private static final String DELETE_BOOK =
            "delete from note where note_id = ?";

    @Override
    public Note createNote(Note note) {
        noteJdbc.update(INSERT_NOTE, note.getBookId(), note.getNote());

        int id = noteJdbc.queryForObject("select last_insert_id()", Integer.class);
        note.setNoteId(id);

        return note;
    }

    @Override
    public Note getNote(int noteId) {
        try {
            return noteJdbc.queryForObject(SELECT_NOTE, this::mapRowToNote, noteId);
        } catch(EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Note> getNotesByBookId(int bookId) {
        List<Note> notes = noteJdbc.query(SELECT_ALL_NOTES_BY_BOOK_ID, this::mapRowToNote, bookId);
        return notes;
    }

    @Override
    public List<Note> getAllNotes() {
        List<Note> notes = noteJdbc.query(SELECT_ALL_NOTES, this::mapRowToNote);
        return notes;
    }

    @Override
    public String updateBook(Note note) {
        int numRowUpdated = noteJdbc.update(UPDATE_BOOK, note.getBookId(), note.getNote(), note.getNoteId());

        int id = note.getNoteId();
        String updateSuccessfulMsg = "Note with note_id " + id + " has been updated.";
        String updateFailedMsg = "No notes updated. No note with note_id " + id + " was found.";

        return numRowUpdated == 1 ? updateSuccessfulMsg : updateFailedMsg;
    }

    @Override
    public String deleteBook(int id) {
        int numRowDeleted = noteJdbc.update(DELETE_BOOK, id);

        String deleteSuccessfulMsg = "Note with note_id " + id + " has been deleted.";
        String deleteFailedMsg = "No notes deleted. No note with note_id " + id + " was found.";

        return numRowDeleted == 1 ? deleteSuccessfulMsg : deleteFailedMsg;
    }

    public Note mapRowToNote(ResultSet rs, int rowNum) throws SQLException {
        Note note = new Note( rs.getInt("book_id"), rs.getString("note"));
        note.setNoteId(rs.getInt("note_id"));

        return note;
    }
}
