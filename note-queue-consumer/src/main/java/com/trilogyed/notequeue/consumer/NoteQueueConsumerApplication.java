package com.trilogyed.notequeue.consumer;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class NoteQueueConsumerApplication {
	public static final String TOPIC_EXCHANGE_NAME = "addQueue-note-exchange";
	public static final String ADD_QUEUE_NAME = "note-list-add-addQueue";
	public static final String ADD_ROUTING_KEY = "note.list.add.#";

	public static final String UPDATE_QUEUE_NAME = "note-list-update-addQueue";
	public static final String UPDATE_ROUTING_KEY = "note.list.update.#";

	@Bean
	Queue addQueue() {
		return new Queue(ADD_QUEUE_NAME, false);
	}

	@Bean
	TopicExchange exchange() {
		return new TopicExchange(TOPIC_EXCHANGE_NAME);
	}

	@Bean
	Binding addBinding(Queue addQueue, TopicExchange exchange) {
		return BindingBuilder.bind(addQueue).to(exchange).with(ADD_ROUTING_KEY);
	}




	@Bean
	Queue updateQueue() {
		return new Queue(UPDATE_QUEUE_NAME, false);
	}

	@Bean
	Binding updateBinding(Queue updateQueue, TopicExchange exchange) {
		return BindingBuilder.bind(updateQueue).to(exchange).with(UPDATE_ROUTING_KEY);
	}


	@Bean
	public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}

	public static void main(String[] args) {
		SpringApplication.run(NoteQueueConsumerApplication.class, args);
	}
}


