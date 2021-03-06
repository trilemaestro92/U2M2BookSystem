package com.trilogyed.notequeue.consumer.util.feign;

import com.trilogyed.notequeue.consumer.util.messages.Note;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

// Let the interface know of the name of the feign client. In our case, it is the note-service
@FeignClient(name = "note-service")
public interface NoteClient {

    // The interface needs the POST route of the note-service with the request body to post
    @RequestMapping(value = "/note", method = RequestMethod.POST)
    public Note createNote(@RequestBody Note note);

    // The interface needs the PUT route of the note-service with the request body to update and the id of the note
    @RequestMapping(value = "/note/{id}", method = RequestMethod.PUT)
    public String updateNote(@RequestBody Note note, @PathVariable int id);
}