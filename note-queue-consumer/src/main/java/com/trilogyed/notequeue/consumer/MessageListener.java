package com.trilogyed.notequeue.consumer;

import com.trilogyed.notequeue.consumer.util.feign.NoteClient;
import com.trilogyed.notequeue.consumer.util.messages.Note;
import feign.FeignException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.listener.exception.ListenerExecutionFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

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
    public void receiveAddMessage(Note msg) {

        if (msg.getNoteId() == 0) {

            // this is the client (connection to the note-service) creating a POST with the msg comming in
            Note note = client.createNote(msg);
            System.out.println("Created: " + note.toString());

            // Print the note for confirmation

        } else {
            Note note = msg;
            client.updateNote(msg, msg.getNoteId());
            System.out.println("Updated: " + note.toString());
        }


        // ------ DO NOT -------
        // do not have a return for this listener. IF you use a return, the app will get stuck in a loop and make
        // infinite queues and addQueue exceptions. The purpose of THIS consumer is ONLY to create/update notes. The
        // produce does not need to received the finished addQueue; the notes are retrieved directly from note-service
    }

}
