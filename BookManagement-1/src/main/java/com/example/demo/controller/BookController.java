package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.custom.exception.BusinessException;
import com.example.demo.custom.exception.ControllerException;
import com.example.demo.kafka.KafkaProducer;
import com.example.demo.model.Book;
import com.example.demo.model.BookES;
import com.example.demo.model.BookWithCategory;
import com.example.demo.model.LibraryBook;
import com.example.demo.service.BookService;

@RestController
@RequestMapping("/books")
public class BookController {
	@Autowired
	private KafkaProducer kafkaProducer;
	
	
	
	public BookController(KafkaProducer kafkaProducer) {
		super();
		this.kafkaProducer = kafkaProducer;
	}

	Logger logger = LoggerFactory.getLogger(BookController.class);

	@Autowired
	private BookService service;
	
	@PostMapping("/")
	private ResponseEntity<?> createBook(@RequestBody Book book) {
		logger.info("in createBook method");
		try {
			Book newbook = service.addBook(book);
			return new ResponseEntity<Book>(newbook,HttpStatus.CREATED);
		}
		catch(BusinessException e) {
			ControllerException ce = new ControllerException(e.getErrorCode(),e.getErrorMessage());
			return new ResponseEntity<ControllerException>(ce,HttpStatus.BAD_REQUEST);
		}
//		catch(Exception e) {
//			ControllerException ce = new ControllerException("612","Something went wrong in Controller : "+e.getMessage());
//			return new ResponseEntity<ControllerException>(ce,HttpStatus.BAD_REQUEST);
//		}
	}
	
	@PostMapping("/ES/")
	private ResponseEntity<?> createBookES(@RequestBody BookES bookES) {
		logger.info("in createBook method");
		try {
			BookES newbookES = service.addBookES(bookES);
			return new ResponseEntity<BookES>(newbookES,HttpStatus.CREATED);
		}
		catch(BusinessException e) {
			ControllerException ce = new ControllerException(e.getErrorCode(),e.getErrorMessage());
			return new ResponseEntity<ControllerException>(ce,HttpStatus.BAD_REQUEST);
		}
//		catch(Exception e) {
//			ControllerException ce = new ControllerException("612","Something went wrong in Controller : "+e.getMessage());
//			return new ResponseEntity<ControllerException>(ce,HttpStatus.BAD_REQUEST);
//		}
	}
	
	@GetMapping("/ES/")
	private ResponseEntity<?> showBooksES() {
		logger.info("in showBooksES method");
		try {
			Page<BookES> booksES = service.getBooksES();
			return new ResponseEntity<Page<BookES>>(booksES,HttpStatus.OK);
		}
		catch(BusinessException e) {
			ControllerException ce = new ControllerException(e.getErrorCode(),e.getErrorMessage());
			return new ResponseEntity<ControllerException>(ce,HttpStatus.BAD_REQUEST);
		}
		catch(Exception e) {
			ControllerException ce = new ControllerException("613","Something ent wrong in Controller : "+e.getMessage());
			return new ResponseEntity<ControllerException>(ce,HttpStatus.BAD_REQUEST);
		}
		
	}
	
	
	
	@GetMapping("/")
	private ResponseEntity<?> showBooks() {
		logger.info("in showBooks method");
		try {
			List<Book> books = service.getBooks();
			return new ResponseEntity<List<Book>>(books,HttpStatus.OK);
		}
		catch(BusinessException e) {
			ControllerException ce = new ControllerException(e.getErrorCode(),e.getErrorMessage());
			return new ResponseEntity<ControllerException>(ce,HttpStatus.BAD_REQUEST);
		}
		catch(Exception e) {
			ControllerException ce = new ControllerException("613","Something ent wrong in Controller : "+e.getMessage());
			return new ResponseEntity<ControllerException>(ce,HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@GetMapping("/library/{bookId}")
	private ResponseEntity<?> showLibraryBook(@PathVariable String bookId) {
		Book book = service.getBookById(bookId);
		LibraryBook cat = service.getBookCategory(bookId);
		BookWithCategory res = new BookWithCategory();
		res.setBookId(book.getBookId());
		res.setBookName(book.getBookName());
		res.setDescription(book.getDescription());
		res.setCategory(cat.getCategory());
		return new ResponseEntity<BookWithCategory>(res,HttpStatus.OK);
		
	}
	
	@GetMapping("/{bookId}")
	private ResponseEntity<?> showBookById(@PathVariable String bookId) {
		logger.info("in showBookbyId method");
		try {
			Book book = service.getBookById(bookId);
			return new ResponseEntity<Book>(book,HttpStatus.OK);
		}
		catch(BusinessException e) {
			ControllerException ce = new ControllerException(e.getErrorCode(),e.getErrorMessage());
			return new ResponseEntity<ControllerException>(ce,HttpStatus.BAD_REQUEST);
		}
//		catch(Exception e) {
//			ControllerException ce = new ControllerException("614","Something ent wrong in Controller : "+e.getMessage());
//			return new ResponseEntity<ControllerException>(ce,HttpStatus.BAD_REQUEST);
//		}
		
	}
	
	@GetMapping("/ES/bookID/{bookid}")
	private ResponseEntity<?> showBookByIdES(@PathVariable String bookId) {
		logger.info("in showBookbyIdES method");
		try {
			Optional<BookES> bookES = service.getBookByIdES(bookId);
			return new ResponseEntity<Optional<BookES>>(bookES,HttpStatus.OK);
		}
		catch(BusinessException e) {
			ControllerException ce = new ControllerException(e.getErrorCode(),e.getErrorMessage());
			return new ResponseEntity<ControllerException>(ce,HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/bookname/{bookName}")
	private ResponseEntity<?> showBookByName(@PathVariable String bookName) {
		logger.info("in showBookByName method");
		try {
			List<Book> book = service.getBookByName(bookName);
			return new ResponseEntity<List<Book>>(book,HttpStatus.OK);
		}
		catch(BusinessException e) {
			ControllerException ce = new ControllerException(e.getErrorCode(),e.getErrorMessage());
			return new ResponseEntity<ControllerException>(ce,HttpStatus.BAD_REQUEST);
		}
		catch(Exception e) {
			ControllerException ce = new ControllerException("615","Something went wrong in Controller : "+e.getMessage());
			return new ResponseEntity<ControllerException>(ce,HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@GetMapping("/bookname/ES/{bookName}")
	private ResponseEntity<?> showBookByNameES(@PathVariable String bookName) {
		logger.info("in showBookByName method");
		try {
			List<BookES> bookES = service.getBookByNameES(bookName);
			return new ResponseEntity<List<BookES>>(bookES,HttpStatus.OK);
		}
		catch(BusinessException e) {
			ControllerException ce = new ControllerException(e.getErrorCode(),e.getErrorMessage());
			return new ResponseEntity<ControllerException>(ce,HttpStatus.BAD_REQUEST);
		}
		catch(Exception e) {
			ControllerException ce = new ControllerException("615","Something went wrong in Controller : "+e.getMessage());
			return new ResponseEntity<ControllerException>(ce,HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@PutMapping("/")
	private ResponseEntity<?> editBook(@RequestBody Book book )  {
		logger.info("in editBook method");
		try {
			Book bk = service.updateBook(book);
			return new ResponseEntity<Book>(bk,HttpStatus.OK);
		}
		catch(BusinessException e) {
			ControllerException ce = new ControllerException(e.getErrorCode(),e.getErrorMessage());
			return new ResponseEntity<ControllerException>(ce,HttpStatus.BAD_REQUEST);
		}
//		catch(Exception e) {
//			ControllerException ce = new ControllerException("616","Something ent wrong in Controller : "+e.getMessage());
//			return new ResponseEntity<ControllerException>(ce,HttpStatus.BAD_REQUEST);
//		}
	}
	
	@DeleteMapping("/{bookId}")
	private ResponseEntity<?> removeBookById(@PathVariable String bookId) {
		logger.info("in removeBookById method");
		try {
			String s = service.deleteBookById(bookId);
			return new ResponseEntity<String>(s,HttpStatus.OK);
		}
		catch(BusinessException e) {
			ControllerException ce = new ControllerException(e.getErrorCode(),e.getErrorMessage());
			return new ResponseEntity<ControllerException>(ce,HttpStatus.BAD_REQUEST);
		}
//		catch(Exception e) {
//			ControllerException ce = new ControllerException("617","Something ent wrong in Controller : "+e.getMessage());
//			return new ResponseEntity<ControllerException>(ce,HttpStatus.BAD_REQUEST);
//		}
	}
	
	@PostMapping("/publish")
    public ResponseEntity<String> publish(@RequestBody BookES bookES){
		logger.info("in publish method");
        kafkaProducer.sendMessage(bookES);
        return ResponseEntity.ok("Json message sent to kafka topic");
    }

}
