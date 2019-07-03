package com.trilogyed.bookservice.service;

import com.trilogyed.bookservice.dao.BookDao;
import com.trilogyed.bookservice.exception.NotFoundException;
import com.trilogyed.bookservice.feignClient.NoteClient;
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
            BookViewModel bvm = buildBookViewModel(book);
            bvmList.add(bvm);
        }
        return bvmList;
    }

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



    public BookViewModel createBookWithNotes(BookViewModel bookViewModel){

        BookViewModel bvm = new BookViewModel();
        bvm = bookDao.addBook(bookViewModel);

        List<Note> noteList = bookViewModel.getNotes();
        System.out.println("Sending note list");
        //rabbitTemplate.convertAndSend(EXCHANGE, ROUTING_KEY, noteList);
        System.out.println("Note list sent");
        return bookViewModel;

    }

    public boolean updateBook(BookViewModel bookViewModel){

        BookViewModel bvm = new BookViewModel();
        bookDao.updatedBook(bookViewModel);

        boolean isUpdated = false;
        Book existingBook = bookDao.getBook(bookViewModel.getId());
        if (existingBook == null) {
            throw new NotFoundException("Book id " + bookViewModel.getId() + " not found");
        } else {
            try {
                bookDao.updatedBook(bookViewModel);
                isUpdated = true;
                List<Note> noteList = bookViewModel.getNotes();
                //rabbitTemplate.convertAndSend(EXCHANGE, ROUTING_KEY, noteList);
            } catch (Exception ex) {
                isUpdated = false;
            }
        }
        return isUpdated;

    }

    public BookViewModel findBook(int id) {
        Book book = bookDao.getBook(id);
        BookViewModel bvm = new BookViewModel();
        if (book == null){
            throw  new NotFoundException("Book id " + id + " not found!");
        } else {
            bvm.setId(book.getBookId());
            bvm.setAuthor(book.getAuthor());
            bvm.setTitle(book.getTitle());
//            List<Note> mynotes = noteClient.getNotesByBookId();
//            System.out.println(mynotes);
//            bvm.setNotes(noteClient.getNotesByBookId());

        }
        return bvm;
    }





}





