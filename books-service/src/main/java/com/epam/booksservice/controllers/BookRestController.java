package com.epam.booksservice.controllers;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epam.booksservice.dto.BookDto;
import com.epam.booksservice.exceptions.BookNotFoundException;
import com.epam.booksservice.services.BookService;


@RestController
@RequestMapping("/books")
public class BookRestController {

	@Autowired
	private BookService bookService;
	
	@GetMapping()
	public ResponseEntity<List<BookDto>> booksList(){
		return  ResponseEntity.ok().body(bookService.findAll());
	}
	
	@PostMapping
	public ResponseEntity<BookDto> createBook(@RequestBody @Valid BookDto bookDto){
		return new ResponseEntity<BookDto>(bookService.save(bookDto),HttpStatus.CREATED);
	}

	@PutMapping("/{book_id}")
	public ResponseEntity<BookDto> updateBook(@PathVariable("book_id") int bookId,@RequestBody @Valid BookDto bookDto){
		
		BookDto book = bookService.findById(bookId).get();
		book.setBookname(bookDto.getBookname());
		book.setAuthor(bookDto.getAuthor());
		book.setPublisher(bookDto.getPublisher());
		return new ResponseEntity<BookDto>(bookService.update(book),HttpStatus.OK);
	}
	
	@GetMapping("/{book_id}")
	public ResponseEntity<BookDto> viewBook(@PathVariable("book_id") int bookId) {
		
		return new ResponseEntity<BookDto>(bookService.findById(bookId).get(),HttpStatus.OK);
	}
	
	@DeleteMapping("/{book_id}")
	public ResponseEntity<String> deleteBook(@PathVariable("book_id") int bookId){
		bookService.findById(bookId);
		bookService.delete(bookId);
		return new ResponseEntity<String>("Book Deleted successfully",HttpStatus.NO_CONTENT);
	}
	
}

