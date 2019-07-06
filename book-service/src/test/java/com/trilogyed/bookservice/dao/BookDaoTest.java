//package com.trilogyed.bookservice.dao;
//
//import com.trilogyed.bookservice.model.Book;
//import com.trilogyed.bookservice.model.BookViewModel;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import java.util.List;
//
//import static org.junit.Assert.*;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest
//public class BookDaoTest {
//
//    @Autowired
//    BookDao bookDao;
//
//    @Before
//    public void setUp() throws Exception {
//        List<Book> allBooks = bookDao.getAllBooks();
//        allBooks.stream()
//                .forEach(book -> bookDao.deleteBook(book.getBookId()));
//    }
//    @After
//    public void tearDown() throws Exception {
//        List<Book> allBooks = bookDao.getAllBooks();
//        allBooks.stream()
//                .forEach(book -> bookDao.deleteBook(book.getBookId()));
//    }
//
//    @Test
//    public void addGetDeleteTask(){
//        BookViewModel bvm = new BookViewModel("Harry Potter", "J.K Rowling",null);
//
//        bvm = bookDao.addBook(bvm);
//
//        Book book = new Book();
//        book.setBookId(bvm.getId());
//        book.setAuthor(bvm.getAuthor());
//        book.setTitle(bvm.getTitle());
//
//        Book book1 = bookDao.getBook(bvm.getId());
//        assertEquals(book1, book);
//
//        bookDao.deleteBook(bvm.getId());
//
//        book1 = bookDao.getBook(bvm.getId());
//
//        assertNull(book1);
//    }
//
//    @Test
//    public void updateTask(){
//        BookViewModel bvm = new BookViewModel("Harry Potter", "J.K Rowling",null);
//
//        bvm = bookDao.addBook(bvm);
//
//        bvm.setTitle("Harry Potter 2");
//        bvm.setAuthor("J.S Rowling ");
//
//        bookDao.updateBook(bvm);
//
//        Book updatedBook = new Book();
//        updatedBook.setBookId(bvm.getId());
//        updatedBook.setTitle(bvm.getTitle());
//        updatedBook.setAuthor(bvm.getAuthor());
//        Book book1= bookDao.getBook(updatedBook.getBookId());
//
//        assertEquals(book1, updatedBook);
//    }
//
//    @Test
//    public void getAllTasks(){
//        BookViewModel book = new BookViewModel("Harry Potter", "J.K Rowling",null);
//
//        book = bookDao.addBook(book);
//
//        book = new BookViewModel("Harry Potter 2", "J.S Rowling",null);
//
//        book = bookDao.addBook(book);
//
//        List<Book> bookList = bookDao.getAllBooks();
//        assertEquals(2, bookList.size());
//    }
//
//}