package com.trilogyed.bookservice.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

public class Book {

    private int bookId;
    @NotNull(message = "title can not be null")
    @Size(max = 50, message = "title cannot exceed 255 characters")
    private String title;
    @NotNull(message = "author can not be null")
    @Size(max = 50, message = "author cannot exceed 255 characters")
    private String author;

    public Book(){ }

    public Book(int bookId, String title, String author) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
    }

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
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

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return bookId == book.bookId &&
                title.equals(book.title) &&
                author.equals(book.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookId, title, author);
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookId=" + bookId +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                '}';
    }
}
