package com.trilogyed.bookservice.dao;

import com.trilogyed.bookservice.model.Book;
import com.trilogyed.bookservice.model.BookViewModel;

import java.util.List;

public interface BookDao {

    BookViewModel addBook(BookViewModel bvm);
    Book getBook(int id);
    void updatedBook(Book book);
    void deletedBook(int id);
    List<Book> getAllBooks();

}
