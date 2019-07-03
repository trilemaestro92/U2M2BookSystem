package com.trilogyed.bookservice.service;

import com.trilogyed.bookservice.dao.BookDao;
import com.trilogyed.bookservice.model.Book;
import com.trilogyed.bookservice.model.BookViewModel;
import com.trilogyed.bookservice.model.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Component
public class BookServiceLayer {

    private BookDao bookDao;

    @Autowired
    public BookServiceLayer(BookDao bookDao) {

        this.bookDao = bookDao;


    }
    @Transactional
    public BookViewModel saveBook(BookViewModel viewModel) {
        Book bookmdl = new Book();
        bookmdl.setTitle(viewModel.getTitle());
        bookmdl.setAuthor(viewModel.getAuthor());
        bookmdl.setBookId(viewModel.getId());
        bookmdl = bookDao.addBook(bookmdl);
        viewModel.setId(bookmdl.getBookId());

        viewModel.setNotes(null);
        return  viewModel;


    }

    public BookViewModel findBook(int id) {
        Book book = bookDao.getBook(id);
        return buildBookViewModel(book);
    }

    public List<BookViewModel> findAllBooks() {
        List<Book> bookList = bookDao.getAllBooks();
        List<BookViewModel> bvmList = new ArrayList<>();

        for (Book book : bookList) {
            BookViewModel bvm = buildBookViewModel(book);
            bvmList.add(bvm);
        }
        return bvmList;
    }

    @Transactional
    public void updateBook(BookViewModel viewModel) {

        Book book = new Book();
        book.setBookId(viewModel.getId());
        book.setTitle(viewModel.getTitle());
        book.setAuthor(viewModel.getAuthor());

        bookDao.updatedBook(book);
    }

    @Transactional
    public void removeBook(int id) {

        bookDao.deletedBook(id);
    }

    //Helper Methods
    private BookViewModel buildBookViewModel(Book book) {

        //Assemble the album view model
        BookViewModel bvm = new BookViewModel();
        bvm.setId(book.getBookId());

    }

}





