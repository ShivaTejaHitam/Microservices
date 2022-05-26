package com.epam.libraryservice.mapper;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

import com.epam.libraryservice.dto.RecordDto;

import com.epam.libraryservice.entities.Record;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class Mapper {
private static ModelMapper mapper = new ModelMapper();
	
	public static Record toEntity(RecordDto recordDto) {
		return mapper.map(recordDto, Record.class);
	}
	
	public static RecordDto toDto(Record record) {
		return mapper.map(record, RecordDto.class);
	}
	
	public static List<Record> toEntityList(List<RecordDto> records){
		return records.stream().map(b -> mapper.map(b,Record.class)).collect(Collectors.toList());
	}
	
	public static List<RecordDto> toDtoList(List<Record> records){
		return records.stream().map(b -> mapper.map(b,RecordDto.class)).collect(Collectors.toList());
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
