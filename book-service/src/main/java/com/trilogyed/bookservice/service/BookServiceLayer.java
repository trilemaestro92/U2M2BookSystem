package com.trilogyed.bookservice.service;

import com.trilogyed.bookservice.dao.BookDao;
import com.trilogyed.bookservice.model.BookViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookServiceLayer {

    private BookDao bookDao;

    @Autowired
    public BookServiceLayer(BookDao bookDao)
    { this.bookDao = bookDao; }

}
