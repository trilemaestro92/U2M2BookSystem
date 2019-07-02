package com.trilogyed.bookservice.dao;

import com.trilogyed.bookservice.model.Book;

import java.util.List;

public interface BookDao {

    Book addBook(Book book);
    Book getBook(int id);
    void updatedBook(Book book);
    void deletedBook(int id);
    List<Book> getAllBooks();
}
