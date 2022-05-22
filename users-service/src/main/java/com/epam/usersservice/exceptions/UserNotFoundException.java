package com.epam.usersservice.exceptions;

public class UserNotFoundException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6304711887124078631L;

	public UserNotFoundException(String message) {
		super(message);
	}
	
}
