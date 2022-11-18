package com.example.demo.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.domain.Page;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.demo.custom.exception.BusinessException;
import com.example.demo.custom.exception.EmptyInputException;
import com.example.demo.model.Book;
import com.example.demo.model.BookES;
import com.example.demo.model.DbSequence;
import com.example.demo.model.LibraryBook;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.BookRepositoryES;
import com.example.demo.repository.IdRepository;





@Service
@EnableCaching
public class BookService {
	
	

	Logger logger = LoggerFactory.getLogger(BookService.class);
	
	

	@Value("${library.url}")
	String uri;

	@Autowired
	private BookRepository repo;
	@Autowired
	private IdRepository idrepo;
	@Autowired
	private BookRepositoryES repoES;

	public Book addBook(Book book) {
		logger.info("in addBook method");
		if (book.getBookName().isEmpty() || book.getBookName().length() == 0) {
			throw new EmptyInputException("601", "Please Send proper name,it is blank");
		}
		try {
			DbSequence dbs = idrepo.findById("id").get();
			int old_id = dbs.getSeq();
			dbs.setSeq(old_id + 1);
			idrepo.save(dbs);
			book.setBookId("DSCE-" + (old_id + 1));
			return repo.save(book);
		}
		// null object passed
		catch (IllegalArgumentException e) {
			throw new BusinessException("602", "the given info about book is null : " + e.getMessage());
		} catch (Exception e) {
			throw new BusinessException("603", "Something went wrong in the service layer : " + e.getMessage());
		}
	}
	
	public BookES addBookES(BookES bookES) {
//		logger.info("in addBookES method");
//		if (bookES.getBookName().isEmpty() || bookES.getBookName().length() == 0) {
//			throw new EmptyInputException("601", "Please Send proper name,it is blank");
//		}
//		try {
//			DbSequence dbs = idrepo.findById("id").get();
//			int old_id = dbs.getSeq();
//			dbs.setSeq(old_id);
//			idrepo.save(dbs);
//			bookES.setBookId("DSCE-" + (old_id));
//			return repoES.save(bookES);
//		}
//		
		return repoES.save(bookES);
		
		// null object passed
//		catch (IllegalArgumentException e) {
//			throw new BusinessException("602", "the given info about book is null : " + e.getMessage());
//		} catch (Exception e) {
//			throw new BusinessException("603", "Something went wrong in the service layer : " + e.getMessage());
//		}
	}

	public List<Book> getBooks() {
		logger.info("in getBooks method");
		try {
			List<Book> book = repo.findAll();
			if (book.isEmpty()) {
				throw new BusinessException("604", "There are no books available");
			}
			return repo.findAll();
		} catch (Exception e) {
			throw new BusinessException("605","Something went wrong in the service layer while fetching all employees : " + e.getMessage());
		}

	}
	
	public Page<BookES> getBooksES() {
		logger.info("in getBooksES method");
		try {
			Page<BookES> bookES =  (Page<BookES>) repoES.findAll();
			if (bookES.isEmpty()) {
				throw new BusinessException("604", "There are no books available");
			}
			return (Page<BookES>) repoES.findAll();
		} catch (Exception e) {
			throw new BusinessException("605","Something went wrong in the service layer while fetching all employees : " + e.getMessage());
		}

	}

	@Cacheable(key = "#bookId",value = "books")
	public Book getBookById(String bookId) {
		logger.info("in getBookById method");
		try {
			return repo.findById(bookId).get();
		} catch (IllegalArgumentException e) {
			throw new BusinessException("606", "The given id id null,please enter the id");
		}
//		catch(NoSuchElementException e) {
//			throw new BusinessException("607","No book with given id exists");
//		}
		//

	}

	public List<Book> getBookByName(String bname) {
		logger.info("in getBookByName method");
		try {
			// return repo.findByBookName(bname);
			List<Book> books = (List<Book>) repo.findByBookName(bname);
			if (books.size() == 0) {
				throw new BusinessException("607", "The given bookname does not exist in the database");
			} else {
				return books;
			}

		} catch (IllegalArgumentException e) {
			throw new BusinessException("608", "The given bookname is null,please enter the bookname");
		}

	}

	public Book updateBook(Book book) {
		logger.info("in updateBook method");
		try {
			Book bk = repo.findById(book.getBookId()).get();
			bk.setBookName(book.getBookName());
			bk.setDescription(book.getDescription());
			return repo.save(bk);
		} catch (IllegalArgumentException e) {
			throw new BusinessException("610", "The given book values are null,please enter the book details");
		}
//		catch(NoSuchElementException e) {
//			throw new BusinessException("611","No book with given bookid exists");
//		}

	}

	@CacheEvict(key = "#bookId",value = "books")
	public String deleteBookById(String bookId) {
		logger.info("in deleteBookById method");
		try {
			Book book = repo.findById(bookId).get();
			repo.deleteById(bookId);
			return "book deleted";
		} catch (IllegalArgumentException e) {
			throw new BusinessException("612", "The given bookid is null,please enter the bookid");
		}
//		catch(NoSuchElementException e) {
//			throw new BusinessException("613","No book with given bookid exists");
//		}
	}

	public LibraryBook getBookCategory(String bookId) {
		// TODO Auto-generated method stub
		
		String uri = "http://localhost:8082/library/";
        RestTemplate restTemplate = new RestTemplate();
        LibraryBook book = restTemplate.getForObject(uri+bookId,LibraryBook.class);
        return book;
	}

	public Optional<BookES> getBookByIdES(String bookId) {
		logger.info("in getBookByIdES method");
		try {
			return repoES.findBybookId(bookId);
		} catch (IllegalArgumentException e) {
			throw new BusinessException("606", "The given id id null,please enter the id");
		}
		// TODO Auto-generated method stub
		
	}

	public List<BookES> getBookByNameES(String bookName) {
		// TODO Auto-generated method stub
		logger.info("in getBookByName method");
		try {
			// return repo.findByBookName(bname);
			List<BookES> books =  repoES.findByBookName(bookName);
			if (books.size() == 0) {
				throw new BusinessException("607", "The given bookname does not exist in the database");
			} else {
				return books;
			}

		} catch (IllegalArgumentException e) {
			throw new BusinessException("608", "The given bookname is null,please enter the bookname");
		}

	}

//	//new code written on 28/10
//	public int getSequenceNumber(String sequenceName) {
//		//to get sequence no
//		Query query = new Query(Criteria.where("id").is(sequenceName));
//		//update seq num
//		Update update = new Update().inc("seq",1);
//		//modify in document
//		DbSequence counter = mongoOperations.findAndModify(query, update, options().returnNew(true).upsert(true), DbSequence.class);
//		
//		return !Objects.isNull(counter) ? counter.getSeq() : 1;

}
