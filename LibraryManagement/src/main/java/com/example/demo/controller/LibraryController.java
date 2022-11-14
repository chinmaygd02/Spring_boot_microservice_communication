package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.LibraryBook;
import com.example.demo.service.LibraryService;

@RestController
@RequestMapping("/library")
public class LibraryController {
	
	@Autowired
	LibraryService service;
	
	@GetMapping("/{bookId}")
	private LibraryBook showCategoryById(@PathVariable String bookId) {
		LibraryBook category = service.getCategoryById(bookId);
		return category;
	}
	
	@PostMapping(value = "/", consumes = {"application/json"})
	private LibraryBook insertCategory(@RequestBody LibraryBook lib) {
		LibraryBook newCategory = service.addCategory(lib);
		return newCategory;
	}
}
