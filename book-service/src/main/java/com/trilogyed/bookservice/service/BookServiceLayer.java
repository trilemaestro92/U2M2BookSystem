package com.trilogyed.bookservice.service;

import com.trilogyed.bookservice.dao.BookDao;
import com.trilogyed.bookservice.model.Book;
import com.trilogyed.bookservice.model.BookViewModel;
import com.trilogyed.bookservice.model.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;


@Component
public class BookServiceLayer {

    public static final String EXCHANGE = "note-exchange";
    public static final String ROUTING_KEY = "note.list.add.note";

    @Autowired
    private RabbitTemplate rabbitTemplate;


    private BookDao bookDao;

    public BookServiceLayer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Autowired
    public BookServiceLayer(BookDao bookDao) {
        this.bookDao = bookDao;


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
        this.bookDao = bookDao;
        return bvm;
    }

    @Transactional
    public BookViewModel createBookWithNotes(BookViewModel bookViewModel){
        BookViewModel bvm = new BookViewModel();

        bvm = bookDao.addBook(bvm);

        List<Note> noteList = bookViewModel.getNotes();
        System.out.println("Sending note list");
        rabbitTemplate.convertAndSend(EXCHANGE, ROUTING_KEY, noteList);
        System.out.println("Note list sent");
        return bvm;

    }

}





