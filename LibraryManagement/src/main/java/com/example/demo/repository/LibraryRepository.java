package com.example.demo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.demo.model.LibraryBook;

public interface LibraryRepository extends MongoRepository<LibraryBook,String>{

}
