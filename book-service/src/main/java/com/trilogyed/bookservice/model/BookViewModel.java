package com.trilogyed.bookservice.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BookViewModel {

    private int id;
    private String title;
    private String author;
    private List<Note> notes = new ArrayList<>();

    public BookViewModel(){

    }
    public BookViewModel(int id, String title, String author, List<Note> notes) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.notes = notes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookViewModel that = (BookViewModel) o;
        return id == that.id &&
                title.equals(that.title) &&
                author.equals(that.author) &&
                notes.equals(that.notes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, author, notes);
    }

    @Override
    public String toString() {
        return "BookViewModel{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", notes=" + notes +
                '}';
    }
}
