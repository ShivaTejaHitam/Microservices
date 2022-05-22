package com.epam.booksservice.services;

import java.util.List;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.booksservice.dto.BookDto;
import com.epam.booksservice.entities.Book;
import com.epam.booksservice.exceptions.BookNotFoundException;
import com.epam.booksservice.mapper.Mapper;
import com.epam.booksservice.repositories.BookRepository;



@Service
public class BookService {

	@Autowired
	private BookRepository bookRepository;

	public BookDto save(BookDto bookDto) {
		return Mapper.toDto(bookRepository.save(Mapper.toEntity(bookDto)));
	}
	
	public List<BookDto> findAll() {
		return Mapper.toDtoList(bookRepository.findAll());
	}

	public Optional<BookDto> findById(int bookId) {
		Optional<Book> bookOptional = bookRepository.findById(bookId);
		bookOptional.orElseThrow(() -> new BookNotFoundException("Book with Book Id" + bookId + " not found"));
		return Optional.of(Mapper.toDto(bookOptional.get()));
	}

	public void delete(int bookId) {
		bookRepository.deleteById(bookId);
	}
	
	public BookDto update(BookDto bookDto) {
		return Mapper.toDto(bookRepository.save(Mapper.toEntity(bookDto)));
	}
	
}

