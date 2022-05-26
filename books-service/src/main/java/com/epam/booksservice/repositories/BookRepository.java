package com.epam.booksservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.epam.booksservice.entities.Book;

public interface BookRepository extends JpaRepository<Book,Integer>{
	
}


