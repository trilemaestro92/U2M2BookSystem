package com.trilogyed.bookservice.service;

import com.trilogyed.bookservice.dao.BookDao;
import com.trilogyed.bookservice.feignClient.NoteClient;
import com.trilogyed.bookservice.model.Book;
import com.trilogyed.bookservice.model.BookViewModel;
import com.trilogyed.bookservice.model.Note;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class BookServiceLayerTest {

    BookServiceLayer serviceLayer;
    @Mock
    RabbitTemplate rabbitTemplate;
    @Mock
    BookDao bookDao;
    @Mock
    NoteClient noteClient;


    @Before
    public void setUp() throws Exception {
        setUpBookServiceDaoMock();
        serviceLayer = new BookServiceLayer(rabbitTemplate, noteClient, bookDao);
    }

    private void setUpBookServiceDaoMock() {
        List<Note> noteList = new ArrayList<>();
        noteList.add(new Note(1,"Magical!"));
        noteList.add(new Note(1,"Way better than the movies !"));

        Book book = new Book();
        book.setBookId(1);
        book.setAuthor("J.K Rowling");
        book.setTitle("Harry Potter");

        Book book1= new Book();
        book1.setAuthor("J.K Rowling");
        book1.setTitle("Harry Potter");


        Book bookA = new Book();
        book.setBookId(1);
        book.setAuthor("LOTR");
        book.setTitle("JRR Tolkien");

        Book bookA1= new Book();
        book.setAuthor("LOTR");
        book.setTitle("JRR Tolkien");

        BookViewModel bvm = new BookViewModel();
        bvm.setId(1);
        bvm.setTitle("Harry Potter");
        bvm.setNotes(noteList);

        List<Book> bookList= new ArrayList<>();
        bookList.add(book);

        doReturn(book).when(bookDao).addBook(book1);
        doReturn(book).when(bookDao).getBook(1);

        doReturn(noteList).when(noteClient).getNotesByBookId(1);
        doReturn(bookList).when(bookDao).getAllBooks();
    }

//
    @Test
    public void saveFindFindAllBook() {
        List<Note> noteList = new ArrayList<>();
        noteList.add(new Note(1,"Magical!"));
        noteList.add(new Note(1,"Way better than the movies !"));

        BookViewModel book = new BookViewModel();
        book.setAuthor("J.K Rowling");
        book.setTitle("Harry Potter");
        book.setNotes(noteList);

        book = serviceLayer.createBookWithNotes(book);

        BookViewModel fromService = serviceLayer.findBook(book.getId());
        assertEquals(fromService, book);

        List<BookViewModel> bookViewModelList = new ArrayList<>();
        bookViewModelList.add(book);

        List<BookViewModel> fromServiceList = serviceLayer.findAllBooks();
        assertEquals(fromServiceList.size(), bookViewModelList.size());
    }
    @Test
    public void saveBookWithoutNotes(){
        BookViewModel book = new BookViewModel();
        book.setAuthor("J.K Rowling");
        book.setTitle("Harry Potter");
        book.setNotes(null);

        book = serviceLayer.createBookWithNotes(book);

        BookViewModel fromService = serviceLayer.findBook(book.getId());
        assertEquals(fromService, book);
    }

    @Test
    public void removeBook() {
        List<Note> noteList = new ArrayList<>();
        noteList.add(new Note(1,"Magical!"));
        noteList.add(new Note(1,"Way better than the movies !"));

        BookViewModel book = new BookViewModel();
        book.setAuthor("J.K Rowling");
        book.setTitle("Harry Potter");
        book.setNotes(noteList);

        book = serviceLayer.createBookWithNotes(book);

        boolean isDeleted = serviceLayer.removeBook(book.getId());

        assertEquals(true, isDeleted);
    }

    @Test
    public void updateBook() {
        List<Note> noteList = new ArrayList<>();
        noteList.add(new Note(1,"Magical!"));
        noteList.add(new Note(1,"Way better than the movies !"));

        BookViewModel book = new BookViewModel();
        book.setAuthor("J.K Rowling");
        book.setTitle("Harry Potter");
        book.setNotes(noteList);

        book = serviceLayer.createBookWithNotes(book);

        book.setAuthor("T.J Rowling");
        book.setTitle("Harry Potter 3");
        book.setNotes(noteList);

        boolean isUpdated= serviceLayer.updateBook(book);
        assertEquals(true, isUpdated);

    }
}