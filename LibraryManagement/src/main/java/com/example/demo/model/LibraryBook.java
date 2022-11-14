package com.example.demo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "library")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LibraryBook {
	
	@Id
	String bookId;
	String category;
	public String getBookId() {
		return bookId;
	}
	public void setBookId(String bookId) {
		this.bookId = bookId;
	}
	
	
	@Override
	public String toString() {
		return "LibraryBook [bookId=" + bookId 
				+ ", category=" + category + "]";
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}

}
