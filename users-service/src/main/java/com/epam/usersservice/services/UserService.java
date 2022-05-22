package com.epam.usersservice.services;

import java.util.List;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.epam.usersservice.dto.UserDto;
import com.epam.usersservice.entities.User;
import com.epam.usersservice.exceptions.UserNotFoundException;
import com.epam.usersservice.mapper.Mapper;
import com.epam.usersservice.repositories.UserRepository;



@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public UserDto save(UserDto userDto) {
		
		return Mapper.toDto(userRepository.save(Mapper.toEntity(userDto)));
	}
	
	public List<UserDto> findAll() {
		return Mapper.toDtoList(userRepository.findAll());
	}

	public Optional<UserDto> findById(int userId) {
		Optional<User> userOptional = userRepository.findById(userId);
		userOptional.orElseThrow(() -> new UserNotFoundException("User with User Id" + userId + " not found"));
		return Optional.of(Mapper.toDto(userOptional.get()));
	}

	public void delete(int userId) {
		userRepository.deleteById(userId);
	}
	
	public UserDto update(UserDto userDto) {
		return Mapper.toDto(userRepository.save(Mapper.toEntity(userDto)));
	}
	
}


