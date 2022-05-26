package com.epam.libraryservice.exceptions;

public class RecordNotFoundException extends RuntimeException{
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4637332541710169984L;

	public RecordNotFoundException(String message) {
		super(message);
	}

}
