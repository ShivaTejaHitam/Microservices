package com.epam.libraryservice.exceptions;

public class BookNotFoundException extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6591756064041314527L;

	public BookNotFoundException(String message) {
		super(message);
	}
}
