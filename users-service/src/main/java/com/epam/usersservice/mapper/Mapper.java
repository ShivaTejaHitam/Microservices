package com.epam.usersservice.mapper;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;


import com.epam.usersservice.dto.UserDto;
import com.epam.usersservice.entities.User;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Mapper {
private static ModelMapper mapper = new ModelMapper();
	
	public static User toEntity(UserDto userDto) {
		return mapper.map(userDto, User.class);
	}
	
	public static UserDto toDto(User user) {
		return mapper.map(user, UserDto.class);
	}
	
	public static List<User> toEntityList(List<UserDto> users){
		return users.stream().map(u -> mapper.map(u,User.class)).collect(Collectors.toList());
	}
	
	public static List<UserDto> toDtoList(List<User> users){
		return users.stream().map(u -> mapper.map(u,UserDto.class)).collect(Collectors.toList());
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
