package com.epam.booksservice.exceptions;

public class BookNotFoundException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7030553672058498947L;
	
	public BookNotFoundException(String message) {
		super(message);
	}
}
