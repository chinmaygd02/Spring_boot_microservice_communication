package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.example.demo.model.BookES;

public interface BookRepositoryES extends ElasticsearchRepository<BookES,String>{

	List<BookES> findByBookName(String bookName);

	Optional<BookES> findBybookId(String bookId);

}
