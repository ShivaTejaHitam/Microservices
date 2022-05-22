package com.epam.libraryservice;

import java.util.ArrayList;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import com.epam.libraryservice.dto.BookDto;


@Component
public class BookExchangeClientImpl implements BookExchangeClient{

	@Override
	public ResponseEntity<BookDto> getBookById(int bookId) {
		return new ResponseEntity<BookDto>(getData(),HttpStatus.OK);
	}

	@Override
	public ResponseEntity<List<BookDto>> getBooks() {
		List<BookDto> books = new ArrayList<>();
		books.add(getData());	
		return ResponseEntity.ok().body(books);
	}

	@Override
	public ResponseEntity<BookDto> createBook(@RequestBody @Valid BookDto bookDto) {
		return new ResponseEntity<BookDto>(getData(),HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<BookDto> updateBook(int bookId,@RequestBody @Valid BookDto bookDto) {
		return new ResponseEntity<BookDto>(getData(),HttpStatus.OK);
	}

	@Override
	public ResponseEntity<String> deleteBook(int bookId) {
		return new ResponseEntity<String>("Book Deleted successfully",HttpStatus.NO_CONTENT);
	}
	
	public BookDto getData() {
		BookDto bookDto = new BookDto();
		bookDto.setBookId(1);
		bookDto.setBookname("Fallback Book");
		bookDto.setAuthor("Fallback Author");
		bookDto.setPublisher("Fallback Publisher");
		return bookDto;
	}

}
