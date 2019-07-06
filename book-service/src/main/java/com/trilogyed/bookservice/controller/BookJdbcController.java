package com.trilogyed.bookservice.controller;

import com.trilogyed.bookservice.dao.BookDao;
import com.trilogyed.bookservice.model.Book;
import com.trilogyed.bookservice.model.BookViewModel;
import com.trilogyed.bookservice.service.BookServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/book")
public class BookJdbcController {

    @Autowired
    BookDao bookDao;
    @Autowired
    BookServiceLayer serviceLayer;



    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookViewModel createBook(@RequestBody @Valid BookViewModel bookViewModel){

        return serviceLayer.createBookWithNotes(bookViewModel);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BookViewModel getBook(@PathVariable("id") int bookId){
        return serviceLayer.findBook(bookId);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<BookViewModel> getAllBooks(){
        return serviceLayer.findAllBooks();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBook(@PathVariable("id") int bookId) {
        bookDao.deleteBook(bookId);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateBook(@PathVariable("id") int bookId, @RequestBody @Valid BookViewModel bvm) {
        if (bvm.getId() == 0)
            bvm.setId(bookId);
        if (bookId != bvm.getId()) {
            throw new IllegalArgumentException("Book ID on path must match the ID in the Book object");
        }
        serviceLayer.updateBook(bvm);
    }

}
