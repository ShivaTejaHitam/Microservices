package com.epam.libraryservice.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.libraryservice.dto.RecordDto;
import com.epam.libraryservice.mapper.Mapper;
import com.epam.libraryservice.repositories.RecordRepository;
import com.epam.libraryservice.entities.Record;
import com.epam.libraryservice.exceptions.BookNotFoundException;
import com.epam.libraryservice.exceptions.RecordNotFoundException;

@Service
public class RecordService {

	@Autowired
	private RecordRepository recordRepository;

	public RecordDto save(RecordDto recordDto){
		List<Record> records = recordRepository.findAll().stream()
				.filter(record -> record.getBookId() == recordDto.getBookId()).collect(Collectors.toList());
		if (records.size() > 0) {
			throw new BookNotFoundException("The requested book is not available");
		}
		return Mapper.toDto(recordRepository.save(Mapper.toEntity(recordDto)));
	}

	
	public void delete(int userId,int bookId) {
		Optional<Record> record = recordRepository.findAll().stream().filter(r->r.getBookId()==bookId&& r.getUserId()==userId).findAny();
		if(record.isEmpty()) {
			throw new RecordNotFoundException("Record not found");
		}
		recordRepository.deleteById(record.get().getRecordId());
	}

}
