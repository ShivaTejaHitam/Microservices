package com.epam.libraryservice.controllers;

import java.util.List;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.RestController;

import com.epam.libraryservice.BookExchangeClient;
import com.epam.libraryservice.UserExchangeClient;
import com.epam.libraryservice.dto.BookDto;
import com.epam.libraryservice.dto.RecordDto;
import com.epam.libraryservice.dto.UserDto;
import com.epam.libraryservice.services.RecordService;

@RestController
@RefreshScope
@RequestMapping("/library")
public class LibraryController {
	
	
	@Value("${my.firstname:default value}")
	private String firstname;
	
	@Autowired
	BookExchangeClient bookExchangeClient;
	
	@Autowired
	UserExchangeClient userExchangeClient;
	
	@Autowired
	private RecordService recordService;
	
	
	@GetMapping("/firstname")
	public String getFirstname() {
		return "My firstname: " + firstname;
	}
	
	@GetMapping("/books")
	public ResponseEntity<List<BookDto>> getBooks(){
		return bookExchangeClient.getBooks();
	}
	
	@GetMapping("/books/{book_id}")
	public ResponseEntity<BookDto> getBookById(@PathVariable("book_id") int bookId){
		return bookExchangeClient.getBookById(bookId);
	}
	
	@PostMapping("/books")
	public ResponseEntity<BookDto> createBook(@RequestBody @Valid BookDto bookDto){
		return bookExchangeClient.createBook(bookDto);
	}
	
	@PutMapping("/books/{book_id}")
	public ResponseEntity<BookDto> updateBook(@PathVariable("book_id") int bookId,@RequestBody @Valid BookDto bookDto){
		return bookExchangeClient.updateBook(bookId, bookDto);
	}
	
	@DeleteMapping("/books/{book_id}")
	public ResponseEntity<String> deleteBook(@PathVariable("book_id") int bookId) {
		return bookExchangeClient.deleteBook(bookId);
	}
	
	@GetMapping("users")
	public ResponseEntity<List<UserDto>> usersList(){
		return userExchangeClient.usersList();
	}
	
	@PostMapping("users")
	public ResponseEntity<UserDto> createUser(@RequestBody @Valid UserDto userDto){
		return userExchangeClient.createUser(userDto);
	}
	
	@PutMapping(value="users/{user_id}")
	public ResponseEntity<UserDto> updateUser(@PathVariable("user_id") int userId,@RequestBody @Valid UserDto userDto){
		return userExchangeClient.updateUser(userId, userDto);
	}
	
	@GetMapping("users/{user_id}")
	public ResponseEntity<UserDto> viewUser(@PathVariable("user_id") int userId){
		return userExchangeClient.viewUser(userId);
	}
	
	@DeleteMapping("users/{user_id}")
	public ResponseEntity<String> deleteUser(@PathVariable("user_id") int userId){
		return userExchangeClient.deleteUser(userId);
	}
	
	@PostMapping(value="users/{user_id}/books/{book_id}")
	public ResponseEntity<RecordDto> createRecord(@PathVariable("user_id") int userId,@PathVariable("book_id") int bookId){
		userExchangeClient.viewUser(userId);
		bookExchangeClient.getBookById(bookId);
		
		RecordDto record = new RecordDto();
		record.setBookId(bookId);
		record.setUserId(userId);
		return new ResponseEntity<RecordDto>(recordService.save(record),HttpStatus.CREATED);
	}
	
	@DeleteMapping(value="users/{user_id}/books/{book_id}")
	public ResponseEntity<String> deleteRecord(@PathVariable("user_id") int userId,@PathVariable("book_id") int bookId){
		userExchangeClient.viewUser(userId);
		bookExchangeClient.getBookById(bookId);
		recordService.delete(userId,bookId);
		return new ResponseEntity<String>("Record Deleted Successfully",HttpStatus.NO_CONTENT);
	}
	 
}
