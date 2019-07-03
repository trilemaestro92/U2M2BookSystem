package com.trilogyed.notequeue.consumer;

import com.trilogyed.notequeue.consumer.util.feign.NoteClient;
import com.trilogyed.notequeue.consumer.util.messages.NoteListEntry;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageListener {

    // This listener should be for adding note
    @RabbitListener(queues = NoteQueueConsumerApplication.QUEUE_NAME)
    public String receiveMessage(NoteListEntry msg) {
        System.out.println(msg.toString());
        return "note queue listener";

    }



}
