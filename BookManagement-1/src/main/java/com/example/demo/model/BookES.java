package com.example.demo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(indexName="my-application")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonSerialize
public class BookES{
	@Override
	public String toString() {
		return "BookES [bookId=" + bookId + ", bookName=" + bookName + ", description=" + description + "]";
	}
	@Id
	private String bookId;
	private String bookName;
	private String description;
	public String getBookId() {
		// TODO Auto-generated method stub
		return this.bookId;
	}
	public String getBookName() {
		return bookName;
	}
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

	
}
