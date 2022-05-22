package com.epam.booksservice.mapper;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

import com.epam.booksservice.dto.BookDto;
import com.epam.booksservice.entities.Book;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;



public class Mapper {
	
	private static ModelMapper mapper = new ModelMapper();
	
	public static Book toEntity(BookDto bookDto) {
		return mapper.map(bookDto, Book.class);
	}
	
	public static BookDto toDto(Book book) {
		return mapper.map(book, BookDto.class);
	}
	
	public static List<Book> toEntityList(List<BookDto> books){
		return books.stream().map(b -> mapper.map(b,Book.class)).collect(Collectors.toList());
	}
	
	public static List<BookDto> toDtoList(List<Book> books){
		return books.stream().map(b -> mapper.map(b,BookDto.class)).collect(Collectors.toList());
	}
	
	public static String mapToJson(Object obj) throws JsonProcessingException {
	      ObjectMapper objectMapper = new ObjectMapper();
	      return objectMapper.writeValueAsString(obj);
	}
	
	public static <T> T mapFromJson(String json, Class<T> className)
	      throws JsonParseException, JsonMappingException, IOException {
	      
	      ObjectMapper objectMapper = new ObjectMapper();
	      return objectMapper.readValue(json, className); 
	}
	
}
