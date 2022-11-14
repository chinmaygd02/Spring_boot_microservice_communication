package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.LibraryBook;
import com.example.demo.repository.LibraryRepository;

@Service
public class LibraryService {
	
	@Autowired
	LibraryRepository repo;
	
	public LibraryBook getCategoryById(String bookId) {
		return repo.findById(bookId).get();
	}
	
	public LibraryBook addCategory(LibraryBook lib) {
		return repo.save(lib);
	}

}
