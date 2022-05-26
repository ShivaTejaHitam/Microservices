package com.epam.libraryservice.exceptions;

public class UserNotFoundException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2628832513208432002L;

	public UserNotFoundException(String message) {
		super(message);
	}
	
}
