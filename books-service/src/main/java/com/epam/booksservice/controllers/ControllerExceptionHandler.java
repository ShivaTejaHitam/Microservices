package com.epam.booksservice.controllers;
import com.epam.booksservice.dto.ExceptionResponse;
import com.epam.booksservice.exceptions.BookNotFoundException;

import java.time.LocalDate;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;


@RestControllerAdvice
public class ControllerExceptionHandler {
	
	@ExceptionHandler(value = BookNotFoundException.class)
	public ResponseEntity<ExceptionResponse> handleBookNotFoundException(BookNotFoundException exception,WebRequest request) {
		
		ExceptionResponse exRes = new ExceptionResponse();
		exRes.setError(exception.getMessage());
		exRes.setStatus(HttpStatus.BAD_REQUEST.name());
		exRes.setTimestamp(LocalDate.now().toString());
		exRes.setPath(request.getDescription(false));
		return new ResponseEntity<ExceptionResponse>(exRes,HttpStatus.BAD_REQUEST);
	}

}
