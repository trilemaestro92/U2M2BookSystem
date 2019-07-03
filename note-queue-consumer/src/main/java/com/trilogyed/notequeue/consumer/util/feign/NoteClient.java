package com.trilogyed.notequeue.consumer.util.feign;

import com.trilogyed.notequeue.consumer.util.messages.NoteListEntry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "note-service")
public interface NoteClient {

    @RequestMapping(value = "/note", method = RequestMethod.POST)
    public NoteListEntry createNote(@RequestBody NoteListEntry note);
}