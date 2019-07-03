package com.trilogyed.bookservice.service;

import com.trilogyed.bookservice.dao.BookDao;
import com.trilogyed.bookservice.model.Book;
import com.trilogyed.bookservice.model.BookViewModel;
import com.trilogyed.bookservice.model.Note;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
