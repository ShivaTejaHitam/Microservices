package com.epam.booksservice.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.epam.booksservice.dto.BookDto;
import com.epam.booksservice.services.BookService;
import com.epam.booksservice.mapper.Mapper;

@WebMvcTest(BookRestController.class)
class BookRestControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private BookService bookService;

	private BookDto bookDto;

	@BeforeEach
	void setUp() throws Exception {
		bookDto = new BookDto();
		bookDto.setBookId(1);
		bookDto.setBookname("Five Point Someone");
		bookDto.setAuthor("Chetan Bhagat");
		bookDto.setPublisher("Rupa & co");
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testBooksList() throws Exception {
		List<BookDto> books = new ArrayList<>();
		books.add(bookDto);
		when(bookService.findAll()).thenReturn(books);
		mockMvc.perform(get("/books").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.length()", is(1)));
	}

	@Test
	void testCreateBook() throws Exception {
		when(bookService.save(any())).thenReturn(bookDto);

		mockMvc.perform(post("/books").contentType(MediaType.APPLICATION_JSON_VALUE).content(Mapper.mapToJson(bookDto)))
				.andExpect(status().isCreated());
	}

	@Test
	void testUpdateBook() throws Exception {
		when(bookService.findById(anyInt())).thenReturn(Optional.of(bookDto));
		when(bookService.update(any())).thenReturn(bookDto);

		mockMvc.perform(
				put("/books/1").contentType(MediaType.APPLICATION_JSON_VALUE).content(Mapper.mapToJson(bookDto)))
				.andExpect(status().isOk());
	}

	@Test
	void testViewBook() throws Exception {
		when(bookService.findById(anyInt())).thenReturn(Optional.of(bookDto));
		mockMvc.perform(get("/books/1").accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk())
				.andExpect(jsonPath("$.*").exists());
	}

	@Test
	void testDeleteBook() throws Exception {
		when(bookService.findById(anyInt())).thenReturn(Optional.of(bookDto));
		mockMvc.perform(delete("/books/1")).andExpect(status().isNoContent()).andReturn().getResponse()
				.getContentAsString().equals("Book Deleted successfully");
	}

}
