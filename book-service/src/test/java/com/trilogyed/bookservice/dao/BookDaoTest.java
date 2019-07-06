package com.trilogyed.bookservice.dao;

import com.trilogyed.bookservice.model.Book;
import com.trilogyed.bookservice.model.BookViewModel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class BookDaoTest {

    @Autowired
    BookDao bookDao;

    @Before
    public void setUp() throws Exception {
        List<Book> allBooks = bookDao.getAllBooks();
        allBooks.stream()
                .forEach(book -> bookDao.deleteBook(book.getBookId()));
    }
    @After
    public void tearDown() throws Exception {
        List<Book> allBooks = bookDao.getAllBooks();
        allBooks.stream()
                .forEach(book -> bookDao.deleteBook(book.getBookId()));
    }

    @Test
    public void addGetDeleteTask(){
        Book book = new Book("Harry Potter", "J.K Rowling");

        book = bookDao.addBook(book);


        Book book1 = bookDao.getBook(book.getBookId());
        assertEquals(book1, book);

        bookDao.deleteBook(book.getBookId());

        book1 = bookDao.getBook(book.getBookId());

        assertNull(book1);
    }

    @Test
    public void updateTask(){
        Book book = new Book("Harry Potter", "J.K Rowling");

        book = bookDao.addBook(book);

        book.setTitle("Harry Potter 2");
        book.setAuthor("J.S Rowling ");

        bookDao.updateBook(book);

        Book book1= bookDao.getBook(book.getBookId());

        assertEquals(book1, book);
    }

    @Test
    public void getAllTasks(){
        Book book = new Book("Harry Potter", "J.K Rowling");

        book = bookDao.addBook(book);

        book = new Book("Harry Potter 2", "J.S Rowling");

        book = bookDao.addBook(book);

        List<Book> bookList = bookDao.getAllBooks();
        assertEquals(2, bookList.size());
    }

}