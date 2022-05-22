package com.epam.booksservice.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import com.epam.booksservice.dto.BookDto;
import com.epam.booksservice.entities.Book;
import com.epam.booksservice.exceptions.BookNotFoundException;
import com.epam.booksservice.repositories.BookRepository;
import com.epam.booksservice.mapper.Mapper;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

	@Mock
	private BookRepository bookRepository;

	@Autowired
	@InjectMocks
	private BookService bookService;

	private BookDto bookDto;
	@Captor
	private ArgumentCaptor<Book> argumentCaptorForBook;

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
	void testSave() {
		given(bookRepository.save(any())).willReturn(Mapper.toEntity(bookDto));
		bookService.save(bookDto);
		verify(bookRepository, times(1)).save(argumentCaptorForBook.capture());
		assertEquals("Five Point Someone", argumentCaptorForBook.getValue().getBookname());
	}

	@Test
	void testFindAll() {
		List<BookDto> mockBooks = Arrays.asList(bookDto);
		given(bookRepository.findAll()).willReturn(Mapper.toEntityList(mockBooks));
		List<BookDto> books = bookService.findAll();
		assertEquals(1, books.size());
		verify(bookRepository, times(1)).findAll();
	}

	@Test
	void testFindById() {
		given(bookRepository.findById(anyInt())).willReturn(Optional.of(Mapper.toEntity(bookDto)));
		assertEquals(bookDto.getClass(),
				bookService.findById(1).get().getClass());
		verify(bookRepository, times(1)).findById(anyInt());
	}
	
	@Test
	void testGetForNoBook() {
		given(bookRepository.findById(anyInt())).willReturn(Optional.empty());
		assertThrows(BookNotFoundException.class, () -> {
			bookService.findById(bookDto.getBookId());
		});
		verify(bookRepository, times(1)).findById(anyInt());
	}

	@Test
	void testDelete() {
		bookService.delete(bookDto.getBookId());
		verify(bookRepository, times(1)).deleteById(anyInt());
	}

	@Test
	void testUpdate() {
		given(bookRepository.save(any())).willReturn(Mapper.toEntity(bookDto));
		bookService.update(bookDto);
		verify(bookRepository, times(1)).save(argumentCaptorForBook.capture());
		assertEquals("Five Point Someone", argumentCaptorForBook.getValue().getBookname());
	}

}
