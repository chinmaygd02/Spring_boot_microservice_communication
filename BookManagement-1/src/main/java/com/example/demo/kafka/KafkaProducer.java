package com.example.demo.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import com.example.demo.controller.BookController;
import com.example.demo.model.Book;
import com.example.demo.model.BookES;

@Service
public class KafkaProducer {
	
	@Autowired
	private NewTopic topic;
	
	Logger logger = LoggerFactory.getLogger(KafkaProducer.class);
	
	private String topicName = "bookmanagement";
	
	@Autowired
	private KafkaTemplate<String, BookES> kafkaTemplate;

	
	
	public void sendMessage(BookES bookES) {
		
		logger.info("in sendMessage method");
		logger.info(String.format("Message sent -> %s",bookES.toString()));
		
		Message<BookES> message = MessageBuilder.withPayload(bookES).setHeader(KafkaHeaders.TOPIC, topic.name()).build();
		kafkaTemplate.send(message);
		
		
		
	}
	
	
}
