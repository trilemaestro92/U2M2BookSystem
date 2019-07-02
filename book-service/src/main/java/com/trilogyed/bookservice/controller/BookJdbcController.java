package com.trilogyed.bookservice.controller;

import com.trilogyed.bookservice.dao.BookDao;
import com.trilogyed.bookservice.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/book")
public class BookJdbcController {

    @Autowired
    BookDao bookDao;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Book createBook(@RequestBody @Valid Book book){
        return bookDao.addBook(book);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Book getBook(@PathVariable("id") int bookId){
        return bookDao.getBook(bookId);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<Book> getAllBooks(){
        return bookDao.getAllBooks();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBook(@PathVariable("id") int bookId) {
        bookDao.deletedBook(bookId);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateBook(@PathVariable("id") int bookId, @RequestBody @Valid Book book) {
        if (book.getBookId() == 0)
            book.setBookId(bookId);
        if (bookId != book.getBookId()) {
            throw new IllegalArgumentException("Book ID on path must match the ID in the Book object");
        }
        bookDao.updatedBook(book);
    }

}
