package com.epam.libraryservice.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

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

import com.epam.libraryservice.entities.Record;
import com.epam.libraryservice.exceptions.BookNotFoundException;
import com.epam.libraryservice.exceptions.RecordNotFoundException;
import com.epam.libraryservice.mapper.Mapper;
import com.epam.libraryservice.dto.RecordDto;
import com.epam.libraryservice.repositories.RecordRepository;

@ExtendWith(MockitoExtension.class)
class RecordServiceTest {

	@Mock
	private RecordRepository recordRepository;
	
	@InjectMocks
	private RecordService recordService;
	
	private RecordDto recordDto;
	@Captor
	private ArgumentCaptor<Record> argumentCaptorForRecord;
	
	@BeforeEach
	void setUp() throws Exception {
		recordDto = new RecordDto();
		recordDto.setRecordId(1);
		recordDto.setBookId(1);
		recordDto.setUserId(1);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testSave() {
		List<RecordDto> records = new ArrayList();
		
		RecordDto record1 = new RecordDto();
		record1.setRecordId(2);
		record1.setBookId(2);
		record1.setUserId(2);
		records.add(record1);
		given(recordRepository.findAll()).willReturn(Mapper.toEntityList(records));
		given(recordRepository.save(any())).willReturn(Mapper.toEntity(recordDto));
		recordService.save(recordDto);
		verify(recordRepository, times(1)).save(argumentCaptorForRecord.capture());
		assertEquals(1, argumentCaptorForRecord.getValue().getRecordId());
	}

	@Test
	void testSaveForNoBook() {
		List<RecordDto> records = new ArrayList();
		records.add(recordDto);
		given(recordRepository.findAll()).willReturn(Mapper.toEntityList(records));
		assertThrows(BookNotFoundException.class,() -> {
			recordService.save(recordDto);
		});
		verify(recordRepository, times(0)).save(argumentCaptorForRecord.capture());
	}
	

	@Test
	void testDelete() {
		List<RecordDto> records = new ArrayList();
		records.add(recordDto);
		given(recordRepository.findAll()).willReturn(Mapper.toEntityList(records));
		recordService.delete(recordDto.getUserId(),recordDto.getBookId());
		verify(recordRepository, times(1)).deleteById(anyInt());
	}
	
	@Test
	void testDeleteForNoRecord() {
		List<RecordDto> records = new ArrayList();
		given(recordRepository.findAll()).willReturn(Mapper.toEntityList(records));
		assertThrows(RecordNotFoundException.class,() -> {
			recordService.delete(recordDto.getUserId(),recordDto.getBookId());
		});
		verify(recordRepository, times(0)).deleteById(anyInt());
	}

}
