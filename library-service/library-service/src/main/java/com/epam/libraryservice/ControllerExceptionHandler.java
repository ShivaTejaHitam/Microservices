package com.epam.libraryservice;

import java.time.LocalDate;


import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.epam.libraryservice.exceptions.BookNotFoundException;
import com.epam.libraryservice.exceptions.RecordNotFoundException;
import com.epam.libraryservice.dto.ExceptionResponse;

import feign.FeignException;

@RestControllerAdvice
public class ControllerExceptionHandler {
	
	@ExceptionHandler(FeignException.class)
    public String handleFeignStatusException(FeignException e, HttpServletResponse response) {
        response.setStatus(e.status());
        return e.getMessage();
    }
	
	@ExceptionHandler(value = BookNotFoundException.class)
	public ResponseEntity<ExceptionResponse> handleBookNotFoundException(BookNotFoundException exception,WebRequest request) {
		
		ExceptionResponse exRes = new ExceptionResponse();
		exRes.setError(exception.getMessage());
		exRes.setStatus(HttpStatus.BAD_REQUEST.name());
		exRes.setTimestamp(LocalDate.now().toString());
		exRes.setPath(request.getDescription(false));
		return new ResponseEntity<ExceptionResponse>(exRes,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = RecordNotFoundException.class)
	public ResponseEntity<ExceptionResponse> handleRecordNotFoundException(RecordNotFoundException exception,WebRequest request) {
		
		ExceptionResponse exRes = new ExceptionResponse();
		exRes.setError(exception.getMessage());
		exRes.setStatus(HttpStatus.BAD_REQUEST.name());
		exRes.setTimestamp(LocalDate.now().toString());
		exRes.setPath(request.getDescription(false));
		return new ResponseEntity<ExceptionResponse>(exRes,HttpStatus.BAD_REQUEST);
	}
	
}

