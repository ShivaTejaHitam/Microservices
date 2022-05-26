package com.epam.libraryservice;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import com.epam.libraryservice.dto.BookDto;
import com.epam.libraryservice.dto.UserDto;

@Component
public class UserExchangeClientImpl implements UserExchangeClient{

	@Override
	public ResponseEntity<List<UserDto>> usersList() {
		List<UserDto> users = new ArrayList<>();
		users.add(getData());	
		return ResponseEntity.ok().body(users);
	}

	@Override
	public ResponseEntity<UserDto> createUser(@RequestBody @Valid UserDto userDto) {
		return new ResponseEntity<UserDto>(getData(),HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<UserDto> updateUser(int userId,@RequestBody @Valid UserDto userDto) {
		return new ResponseEntity<UserDto>(getData(),HttpStatus.OK);
	}

	@Override
	public ResponseEntity<UserDto> viewUser(int userId) {
		return new ResponseEntity<UserDto>(getData(),HttpStatus.OK);
	}

	@Override
	public ResponseEntity<String> deleteUser(int userId) {
		return new ResponseEntity<String>("User Deleted successfully",HttpStatus.NO_CONTENT);
	}
	
	public UserDto getData() {
		UserDto userDto = new UserDto();
		userDto.setUserId(1);
		userDto.setUsername("Fallback User");
		userDto.setDepartment("Fallback Department");
		userDto.setLocation("Fallback Location");
		return userDto;
	}

}
