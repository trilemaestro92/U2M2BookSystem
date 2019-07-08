package com.trilogyed.notequeue.consumer;

import com.trilogyed.notequeue.consumer.util.feign.NoteClient;
import com.trilogyed.notequeue.consumer.util.messages.NoteListEntry;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageListener {

    // Autowire an instance of the interface
    @Autowired
    NoteClient client;

    // Make a constructor for the autowiring to assign the client
    MessageListener(NoteClient client) {
        this.client = client;
    }

    // This listener should accept a msg and use that message to send to the client's createNote method which takes the
    // msg and goes to POST [note-service-host-PORT]/note
    @RabbitListener(queues = NoteQueueConsumerApplication.QUEUE_NAME)
    public void receiveAddMessage(NoteListEntry msg) {

        // this is the client (connection to the note-service) creating a POST with the msg comming in
        NoteListEntry note = client.createNote(msg);

        // Print the note for confirmation
        System.out.println(note.toString());

        // ------ DO NOT -------
        // do not have a return for this listener. IF you use a return, the app will get stuck in a loop and make
        // infinite queues and addQueue exceptions. The purpose of THIS consumer is ONLY to create/update notes. The
        // produce does not need to received the finished addQueue; the notes are retrieved directly from note-service
    }


//    @RabbitListener(queues = NoteQueueConsumerApplication.UPDATE_QUEUE_NAME)
//    public void receiveUpdateMessage(NoteListEntry msg) {
//
//        String updateResponse = client.updateNote(msg, msg.getNoteId());
//
//        System.out.println(updateResponse);
//    }
//



}
