package com.example.demo.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.example.demo.controller.BookController;
import com.example.demo.model.Book;
import com.example.demo.model.BookES;
import com.example.demo.service.BookService;

@Service
public class KafkaConsumer {
	
	@Autowired
	private BookService service;
	
	Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);
	
	@KafkaListener(topics = "bookmanagement",groupId = "myGroup")
	public void consume(BookES bookES) {
		logger.info(String.format("Json message recieved -> %s",bookES.toString()));
		
		//service.addBookES(bookES);
		
	}

}
