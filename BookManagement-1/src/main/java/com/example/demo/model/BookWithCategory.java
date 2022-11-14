package com.example.demo.model;

public class BookWithCategory {
	
	private String bookId;
	private String bookName;
	private String description;
	private String category;
	@Override
	public String toString() {
		return "BookWithCategory [bookId=" + bookId + ", bookName=" + bookName + ", description=" + description
				+ ", category=" + category + "]";
	}
	public String getBookId() {
		return bookId;
	}
	public void setBookId(String bookId) {
		this.bookId = bookId;
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
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}

}
