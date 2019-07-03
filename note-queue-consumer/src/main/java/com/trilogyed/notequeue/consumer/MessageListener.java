package com.trilogyed.notequeue.consumer;

import com.trilogyed.notequeue.consumer.util.messages.NoteListEntry;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class MessageListener {

    // This listener should be for adding note
    @RabbitListener(queues = NoteQueueConsumerApplication.QUEUE_NAME)
    public void receiveMessage(NoteListEntry msg) {
        // Send note to the note microservice

    }


    // Another listener should be for updating a note

}
