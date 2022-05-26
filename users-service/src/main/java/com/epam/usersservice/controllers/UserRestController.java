package com.epam.usersservice.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epam.usersservice.dto.UserDto;
import com.epam.usersservice.services.UserService;

@RestController
@RefreshScope
@RequestMapping("/users")
public class UserRestController {

	@Autowired
	private UserService userService;

	@GetMapping()
	public ResponseEntity<List<UserDto>> usersList() {
		return ResponseEntity.ok().body(userService.findAll());
	}

	@PostMapping
	public ResponseEntity<UserDto> createUser(@RequestBody @Valid UserDto userDto) {
		return new ResponseEntity<UserDto>(userService.save(userDto), HttpStatus.CREATED);
	}

	@PutMapping(value = "/{user_id}")
	public ResponseEntity<UserDto> updateUser(@PathVariable("user_id") int userId,
			@RequestBody @Valid UserDto userDto) {

		UserDto user = userService.findById(userId).get();
		user.setUsername(userDto.getUsername());
		user.setDepartment(userDto.getDepartment());
		user.setLocation(userDto.getLocation());
		return new ResponseEntity<UserDto>(userService.update(user), HttpStatus.OK);
	}

	@GetMapping("/{user_id}")
	public ResponseEntity<UserDto> viewUser(@PathVariable("user_id") int userId) {
		return new ResponseEntity<UserDto>(userService.findById(userId).get(), HttpStatus.OK);
	}

	@DeleteMapping("/{user_id}")
	public ResponseEntity<String> deleteUser(@PathVariable("user_id") int userId) {
		userService.findById(userId);
		userService.delete(userId);
		return new ResponseEntity<String>("User Deleted successfully", HttpStatus.NO_CONTENT);
	}

}
