package com.epam.libraryservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.epam.libraryservice.entities.Record;


public interface RecordRepository extends JpaRepository<Record,Integer>{

}


