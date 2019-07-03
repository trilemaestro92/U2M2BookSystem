package com.trilogyed.bookservice.feignClient;

import com.trilogyed.bookservice.model.Note;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name = "note-service")
public interface NoteClient {
    @RequestMapping(value = "note/book/{book_id}" , method = RequestMethod.GET)
    public List<Note> getNotesByBookId(@PathVariable("book_id") int id);
}
