package com.epam.libraryservice;

import java.util.List;


import javax.validation.Valid;

import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.epam.libraryservice.dto.BookDto;

@FeignClient(name="books-service",fallback=BookExchangeClientImpl.class)
@LoadBalancerClient(name="books-service",configuration=BookExchangeClientImpl.class)
public interface BookExchangeClient{
	
	@GetMapping("/books/{book_id}")
	public ResponseEntity<BookDto> getBookById(@PathVariable("book_id") int bookId);
	
	@GetMapping("/books")
	public ResponseEntity<List<BookDto>> getBooks();
	
	@PostMapping("/books")
	public ResponseEntity<BookDto> createBook(@RequestBody @Valid BookDto bookDto);
	
	@PutMapping("/books/{book_id}")
	public ResponseEntity<BookDto> updateBook(@PathVariable("book_id") int bookId,@RequestBody @Valid BookDto bookDto);
	
	@DeleteMapping("/books/{book_id}")
	public ResponseEntity<String> deleteBook(@PathVariable("book_id") int bookId);
	
}
