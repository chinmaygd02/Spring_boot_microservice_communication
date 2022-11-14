package com.example.demo.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.example.demo.model.BookES;

public interface BookRepositoryES extends ElasticsearchRepository<BookES,String>{

}
