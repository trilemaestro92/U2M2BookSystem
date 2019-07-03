package com.trilogyed.bookservice.controller;

import com.trilogyed.bookservice.dao.BookDao;
import com.trilogyed.bookservice.model.Book;
import com.trilogyed.bookservice.model.Note;
import com.trilogyed.bookservice.util.messages.NoteListEntry;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
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

    public static final String EXCHANGE = "queue-demo-exchange";
    public static final String ROUTING_KEY = "note.list.add.note.controller";

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public BookJdbcController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    // TODO - Not sure about this? Need to update... What is the relation for book/note?
    @RequestMapping(value = "/note", method = RequestMethod.POST)
    public String createAccount(@RequestBody Note note) {
        // create message to send to email list creation queue
        NoteListEntry msg = new NoteListEntry(note.getNoteId(), note.getBookId(), note.getNote());
        System.out.println("Sending message...");
        rabbitTemplate.convertAndSend(EXCHANGE, ROUTING_KEY, msg);
        System.out.println("Message Sent");

        return "Note Created";
    }

}
