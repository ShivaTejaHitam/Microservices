package com.epam.usersservice.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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

import com.epam.usersservice.mapper.Mapper;
import com.epam.usersservice.dto.UserDto;
import com.epam.usersservice.entities.User;
import com.epam.usersservice.exceptions.UserNotFoundException;
import com.epam.usersservice.repositories.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

	@Mock
	private UserRepository userRepository;

	@Autowired
	@InjectMocks
	private UserService userService;

	private UserDto userDto;
	@Captor
	private ArgumentCaptor<User> argumentCaptorForUser;

	@BeforeEach
	void setUp() throws Exception {
		userDto = new UserDto();
		userDto.setUserId(1);
		userDto.setUsername("ShivaTeja");
		userDto.setDepartment("CSE");
		userDto.setLocation("Hyderabad");
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testSave() {
		given(userRepository.save(any())).willReturn(Mapper.toEntity(userDto));
		userService.save(userDto);
		verify(userRepository, times(1)).save(argumentCaptorForUser.capture());
		assertEquals("ShivaTeja", argumentCaptorForUser.getValue().getUsername());
	}

	@Test
	void testFindAll() {
		List<UserDto> mockUsers = Arrays.asList(userDto);
		given(userRepository.findAll()).willReturn(Mapper.toEntityList(mockUsers));
		List<UserDto> users = userService.findAll();
		assertEquals(1, users.size());
		verify(userRepository, times(1)).findAll();
	}

	@Test
	void testFindById() {
		given(userRepository.findById(anyInt())).willReturn(Optional.of(Mapper.toEntity(userDto)));
		assertEquals(userDto.getClass(), userService.findById(1).get().getClass());
		verify(userRepository, times(1)).findById(anyInt());
	}

	@Test
	void testFindByIdForNoUser() {
		given(userRepository.findById(anyInt())).willReturn(Optional.empty());
		assertThrows(UserNotFoundException.class, () -> {
			userService.findById(userDto.getUserId());
		});
		verify(userRepository, times(1)).findById(anyInt());
	}

	@Test
	void testDelete() {
		userService.delete(userDto.getUserId());
		verify(userRepository, times(1)).deleteById(anyInt());
	}

	@Test
	void testUpdate() {
		given(userRepository.save(any())).willReturn(Mapper.toEntity(userDto));
		userService.update(userDto);
		verify(userRepository, times(1)).save(argumentCaptorForUser.capture());
		assertEquals("ShivaTeja", argumentCaptorForUser.getValue().getUsername());
	}

}
