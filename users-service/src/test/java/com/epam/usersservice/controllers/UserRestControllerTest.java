package com.epam.usersservice.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.epam.usersservice.mapper.Mapper;
import com.epam.usersservice.dto.UserDto;
import com.epam.usersservice.services.UserService;

@WebMvcTest(UserRestController.class)
class UserRestControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserService userService;

	private UserDto userDto;

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
	void testUsersList() throws Exception {
		List<UserDto> users = new ArrayList<>();
		users.add(userDto);
		when(userService.findAll()).thenReturn(users);
		mockMvc.perform(get("/users").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.length()", is(1)));
	}

	@Test
	void testCreateUser() throws Exception {
		when(userService.save(any())).thenReturn(userDto);
		mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON_VALUE).content(Mapper.mapToJson(userDto)))
				.andExpect(status().isCreated());
	}

	@Test
	void testUpdateUser() throws Exception {
		when(userService.findById(anyInt())).thenReturn(Optional.of(userDto));
		when(userService.update(any())).thenReturn(userDto);

		mockMvc.perform(
				put("/users/1").contentType(MediaType.APPLICATION_JSON_VALUE).content(Mapper.mapToJson(userDto)))
				.andExpect(status().isOk());
	}

	@Test
	void testViewUser() throws Exception {
		when(userService.findById(anyInt())).thenReturn(Optional.of(userDto));
		mockMvc.perform(get("/users/1").accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk())
				.andExpect(jsonPath("$.*").exists());
	}

	@Test
	void testDeleteUser() throws Exception {
		when(userService.findById(anyInt())).thenReturn(Optional.of(userDto));
		mockMvc.perform(delete("/users/1")).andExpect(status().isNoContent()).andReturn().getResponse()
				.getContentAsString().equals("User Deleted successfully");
	}

}
