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
import org.springframework.transaction.annotation.Transactional;



@Component
public class BookServiceLayer {

    public static final String EXCHANGE = "note-exchange";
    public static final String ROUTING_KEY = "note.controller";


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

    @Transactional
    public BookViewModel createBookWithNotes(BookViewModel bookViewModel) {
        Book book = buildBookFromViewModel(bookViewModel);
        BookViewModel returnedBook;
        book = bookDao.addBook(book);
        bookViewModel.setId(book.getBookId());
        sendNotesToQueue(bookViewModel);
        returnedBook = findBook(bookViewModel.getId());
        return returnedBook;
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

    public List<BookViewModel> findAllBooks() {
        List<Book> bookList = bookDao.getAllBooks();
        List<BookViewModel> bvmList = new ArrayList<>();

        for (Book book : bookList) {
            BookViewModel bvm = buildViewModelFromBook(book);
            bvmList.add(bvm);
        }
        return bvmList;
    }

    @Transactional
    public boolean updateBook(BookViewModel bookViewModel) {
        Book book = buildBookFromViewModel(bookViewModel);
        Book existingBook = bookDao.getBook(bookViewModel.getId());

        boolean isUpdated = false;

        if (existingBook == null) {
            isUpdated = false;
            throw new NotFoundException("Book id " + bookViewModel.getId() + " not found");
        } else {
                bookDao.updateBook(book);
                sendNotesToQueue(bookViewModel);
                isUpdated = true;
        }
        return isUpdated;
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

    private BookViewModel buildViewModelFromBook(Book book) {
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

    private void sendNotesToQueue(BookViewModel bookViewModel) {
        List<Note> noteList = bookViewModel.getNotes();
        List<Note> existingNotes= noteClient.getNotesByBookId(bookViewModel.getId());
        if(bookViewModel.getNotes() != null){
            System.out.println("Sending note list");
            for(Note note: noteList){
                //Checking if note id that is being updated is already associated with the book
                if(existingNotes.stream().anyMatch(existingNote -> existingNote.getNoteId() == note.getNoteId() || note.getNoteId() == 0)){
                    note.setBookId(bookViewModel.getId());
                    rabbitTemplate.convertAndSend(EXCHANGE, ROUTING_KEY, note);

                }else {
                    throw new NotFoundException("Note id " + note.getNoteId() + " not associated with book " + bookViewModel.getId());
                }
            }
            System.out.println("Note list sent");
        }
    }
}





