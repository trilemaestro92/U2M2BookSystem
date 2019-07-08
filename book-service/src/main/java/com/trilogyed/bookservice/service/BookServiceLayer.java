package com.trilogyed.bookservice.service;

import com.trilogyed.bookservice.dao.BookDao;
import com.trilogyed.bookservice.exception.NotFoundException;
import com.trilogyed.bookservice.feignClient.NoteClient;
import com.trilogyed.bookservice.model.Book;
import com.trilogyed.bookservice.model.BookViewModel;
import com.trilogyed.bookservice.model.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

import org.springframework.amqp.rabbit.core.RabbitTemplate;


@Component
public class BookServiceLayer {

    public static final String EXCHANGE = "note-exchange";
    public static final String ROUTING_KEY = "note.list.add.controller";
//    public static final String UPDATE_ROUTING_KEY = "note.list.update.controller";



    @Autowired
    private RabbitTemplate rabbitTemplate;

    private NoteClient noteClient;

    private BookDao bookDao;

    @Autowired
    public BookServiceLayer(RabbitTemplate rabbitTemplate, NoteClient noteClient, BookDao bookDao) {
        this.rabbitTemplate = rabbitTemplate;
        this.noteClient = noteClient;
        this.bookDao = bookDao;
    }

    public List<BookViewModel> findAllBooks() {
        List<Book> bookList = bookDao.getAllBooks();
        List<BookViewModel> bvmList = new ArrayList<>();

        for (Book book : bookList) {
            BookViewModel bvm = buildViewModelFromBook(book);
            bvmList.add(bvm);
        }
        return bvmList;
    }

    public boolean removeBook(int id) {
        boolean isDeleted;
        Book existingBook= bookDao.getBook(id);
        if (existingBook == null) {
            throw new NotFoundException("Book id " + id + " not found");
        } else {
            try {
                bookDao.deleteBook(id);
                isDeleted = true;
            } catch (Exception ex) {
                isDeleted = false;
            }
        }
        return isDeleted;
    }


    public BookViewModel createBookWithNotes(BookViewModel bookViewModel) {

        Book book = buildBookFromViewModel(bookViewModel);
        book = bookDao.addBook(book);
        bookViewModel.setId(book.getBookId());

        List<Note> noteList = bookViewModel.getNotes();

        if(bookViewModel.getNotes() != null){
            System.out.println("Sending note list");

            for(Note note: noteList){
                note.setBookId(bookViewModel.getId());
                rabbitTemplate.convertAndSend(EXCHANGE, ROUTING_KEY, note);
            }
            System.out.println("Note list sent");
        }
        return this.findBook(bookViewModel.getId());

    }

    public boolean updateBook(BookViewModel bookViewModel) {

        Book book = buildBookFromViewModel(bookViewModel);
        Book existingBook = bookDao.getBook(bookViewModel.getId());
        boolean isUpdated = false;

        if (existingBook == null) {
            throw new NotFoundException("Book id " + bookViewModel.getId() + " not found");
        } else {
            try {
                bookDao.updateBook(book);
                List<Note> noteList = bookViewModel.getNotes();
                    System.out.println("Sending note list");
                    for(Note note: noteList){
                        if(note.getNoteId() == 0) {
                            note.setBookId(bookViewModel.getId());
                            rabbitTemplate.convertAndSend(EXCHANGE, ROUTING_KEY, note);
                        }
                        else {
                            note.setBookId(bookViewModel.getId());
//                            rabbitTemplate.convertAndSend(EXCHANGE, UPDATE_ROUTING_KEY, note);
                        }
                    }
                    System.out.println("Note list sent");
                isUpdated = true;

            } catch (Exception ex) {
                isUpdated = false;
            }
        }
        return isUpdated;
    }

    public BookViewModel findBook(int id) {
        Book book = bookDao.getBook(id);
        BookViewModel bvm;
        if (book == null) {
            throw new NotFoundException("Book id " + id + " not found!");
        } else {
            bvm = buildViewModelFromBook(book);

        }
        return bvm;
    }

    //Helper Methods

    private BookViewModel buildViewModelFromBook(Book book) {

        //Assemble the album view model
        BookViewModel bvm = new BookViewModel();
        bvm.setId(book.getBookId());
        bvm.setAuthor(book.getAuthor());
        bvm.setTitle(book.getTitle());
        if(noteClient.getNotesByBookId(book.getBookId()) != null)
            bvm.setNotes(noteClient.getNotesByBookId(book.getBookId()));
        else
            bvm.setNotes(null);

        return bvm;
    }

    private Book buildBookFromViewModel(BookViewModel bvm) {
        Book book = new Book();
        book.setBookId(bvm.getId());
        book.setAuthor(bvm.getAuthor());
        book.setTitle(bvm.getTitle());
        return book;
    }

}





