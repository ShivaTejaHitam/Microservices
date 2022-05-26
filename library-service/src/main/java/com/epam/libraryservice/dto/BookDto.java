package com.epam.libraryservice.dto;

import javax.validation.constraints.NotBlank;

public class BookDto {

	private int bookId;
	@NotBlank(message = "Book Name cannot be empty")
	private String bookname;
	private String author;
	private String publisher;

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public int getBookId() {
		return bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	public String getBookname() {
		return bookname;
	}

	public void setBookname(String bookname) {
		this.bookname = bookname;
	}

}
