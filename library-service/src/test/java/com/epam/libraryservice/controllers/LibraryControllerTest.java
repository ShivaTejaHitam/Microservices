package com.epam.libraryservice.controllers;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import com.epam.libraryservice.mapper.Mapper;
import com.epam.libraryservice.BookExchangeClient;
import com.epam.libraryservice.UserExchangeClient;
import com.epam.libraryservice.dto.BookDto;
import com.epam.libraryservice.dto.RecordDto;
import com.epam.libraryservice.dto.UserDto;
import com.epam.libraryservice.exceptions.BookNotFoundException;
import com.epam.libraryservice.services.RecordService;

@WebMvcTest(LibraryController.class)
class LibraryControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private RecordService recordService;

	@MockBean
	BookExchangeClient bookExchangeClient;

	@MockBean
	UserExchangeClient userExchangeClient;

	private RecordDto recordDto;
	private BookDto bookDto;
	private UserDto userDto;

	@BeforeEach
	void setUp() throws Exception {
		recordDto = new RecordDto();
		recordDto.setRecordId(1);
		recordDto.setBookId(1);
		recordDto.setUserId(1);

		bookDto = new BookDto();
		bookDto.setBookId(1);
		bookDto.setBookname("Five Point Someone");
		bookDto.setAuthor("Chetan Bhagat");
		bookDto.setPublisher("Rupa & co");

		userDto = new UserDto();
		userDto.setUserId(1);
		userDto.setUsername("ShivaTeja");
		userDto.setDepartment("CSE");
		userDto.setLocation("Hyderabad");
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testGetBooks() throws Exception {
		List<BookDto> books = new ArrayList<>();
		books.add(bookDto);
		when(bookExchangeClient.getBooks()).thenReturn(new ResponseEntity<List<BookDto>>(books, HttpStatus.OK));
		mockMvc.perform(get("/library/books").contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk());
	}

	@Test
	void testGetBookById() throws Exception {
		when(bookExchangeClient.getBookById(anyInt())).thenReturn(new ResponseEntity<BookDto>(bookDto, HttpStatus.OK));
		mockMvc.perform(get("/library/books/1").contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk());
	}

	@Test
	void testCreateBook() throws Exception {
		when(bookExchangeClient.createBook(any())).thenReturn(new ResponseEntity<BookDto>(bookDto, HttpStatus.CREATED));
		mockMvc.perform(
				post("/library/books").contentType(MediaType.APPLICATION_JSON_VALUE).content(Mapper.mapToJson(bookDto)))
				.andExpect(status().isCreated());
	}


	@Test
	void testDeleteBook() throws Exception {
		when(bookExchangeClient.getBookById(anyInt())).thenReturn(new ResponseEntity<BookDto>(bookDto, HttpStatus.OK));
		mockMvc.perform(delete("/library/books/1")).andExpect(status().isOk()).andReturn().getResponse()
				.getContentAsString().equals("Book Deleted successfully");
	}

	@Test
	void testUsersList() throws Exception {
		List<UserDto> users = new ArrayList<>();
		users.add(userDto);
		when(userExchangeClient.usersList()).thenReturn(new ResponseEntity<List<UserDto>>(users, HttpStatus.OK));
		mockMvc.perform(get("/library/users").contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk());
	}

	@Test
	void testCreateUser() throws Exception {
		when(userExchangeClient.createUser(any())).thenReturn(new ResponseEntity<UserDto>(userDto, HttpStatus.CREATED));
		mockMvc.perform(
				post("/library/users").contentType(MediaType.APPLICATION_JSON_VALUE).content(Mapper.mapToJson(userDto)))
				.andExpect(status().isCreated());
	}

	
	
	@Test
	void testUpdateBook() throws Exception {
		when(bookExchangeClient.getBookById(anyInt())).thenReturn(new ResponseEntity<BookDto>(bookDto, HttpStatus.OK));
		when(bookExchangeClient.updateBook(anyInt(), any()))
				.thenReturn(new ResponseEntity<BookDto>(bookDto, HttpStatus.OK));

		mockMvc.perform(put("/library/books/1").contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(Mapper.mapToJson(bookDto))).andExpect(status().isOk());
	}
	
	@Test
	void testUpdateUser() throws Exception {
		when(userExchangeClient.viewUser(anyInt())).thenReturn(new ResponseEntity<UserDto>(userDto, HttpStatus.OK));
		when(userExchangeClient.updateUser(anyInt(), any()))
				.thenReturn(new ResponseEntity<UserDto>(userDto, HttpStatus.OK));

		mockMvc.perform(put("/library/users/1").contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(Mapper.mapToJson(userDto))).andExpect(status().isOk());
	}

	@Test
	void testViewUser() throws Exception {
		when(userExchangeClient.viewUser(anyInt())).thenReturn(new ResponseEntity<UserDto>(userDto, HttpStatus.OK));
		mockMvc.perform(get("/library/users/1").contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk());
	}

	@Test
	void testDeleteUser() throws Exception {
		when(userExchangeClient.viewUser(anyInt())).thenReturn(new ResponseEntity<UserDto>(userDto, HttpStatus.OK));
		mockMvc.perform(delete("/library/users/1")).andExpect(status().isOk()).andReturn().getResponse()
				.getContentAsString().equals("User Deleted successfully");
	}

	@Test
	void testCreateRecord() throws Exception {
		when(bookExchangeClient.getBookById(anyInt())).thenReturn(new ResponseEntity<BookDto>(bookDto, HttpStatus.OK));
		when(userExchangeClient.viewUser(anyInt())).thenReturn(new ResponseEntity<UserDto>(userDto, HttpStatus.OK));
		when(recordService.save(any())).thenReturn(recordDto);
		mockMvc.perform(post("/library/users/1/books/1").contentType(MediaType.APPLICATION_JSON_VALUE).content(Mapper.mapToJson(recordDto)))
				.andExpect(status().isCreated());
	}
	
	@Test
	void testCreateRecordForNoBook() throws Exception {
		when(bookExchangeClient.getBookById(anyInt())).thenReturn(new ResponseEntity<BookDto>(bookDto, HttpStatus.OK));
		when(userExchangeClient.viewUser(anyInt())).thenReturn(new ResponseEntity<UserDto>(userDto, HttpStatus.OK));
		when(recordService.save(any())).thenThrow(BookNotFoundException.class);
		mockMvc.perform(post("/library/users/1/books/1").contentType(MediaType.APPLICATION_JSON_VALUE).content(Mapper.mapToJson(recordDto)))
				.andExpect(status().isBadRequest());
	}

	@Test
	void testDeleteRecord() throws Exception {
		when(bookExchangeClient.getBookById(anyInt())).thenReturn(new ResponseEntity<BookDto>(bookDto, HttpStatus.OK));
		when(userExchangeClient.viewUser(anyInt())).thenReturn(new ResponseEntity<UserDto>(userDto, HttpStatus.OK));
		mockMvc.perform(delete("/library/users/1/books/1")).andExpect(status().isNoContent()).andReturn().getResponse()
				.getContentAsString().equals("Record Deleted successfully");
	}

}
