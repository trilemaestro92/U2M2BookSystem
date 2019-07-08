package com.trilogyed.bookservice.dao;

import com.trilogyed.bookservice.model.Book;
import com.trilogyed.bookservice.model.BookViewModel;

import java.util.List;

public interface BookDao {

    Book addBook(Book book);
    Book getBook(int id);
    List<Book> getAllBooks();
    void updateBook(Book book);
    void deleteBook(int id);

}
