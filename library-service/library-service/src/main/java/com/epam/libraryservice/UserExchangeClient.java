package com.epam.libraryservice;

import java.util.List;

import javax.validation.Valid;

import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.epam.libraryservice.dto.UserDto;


@FeignClient(name="users-service",fallback=UserExchangeClient.class)
@LoadBalancerClient(name="users-service",configuration=UserExchangeClientImpl.class)
public interface UserExchangeClient {
	
	@GetMapping("/users")
	public ResponseEntity<List<UserDto>> usersList();
	
	@PostMapping("/users")
	public ResponseEntity<UserDto> createUser(@RequestBody @Valid UserDto userDto);
	
	@PutMapping(value="users/{user_id}")
	public ResponseEntity<UserDto> updateUser(@PathVariable("user_id") int userId,@RequestBody @Valid UserDto userDto);
	
	@GetMapping("users/{user_id}")
	public ResponseEntity<UserDto> viewUser(@PathVariable("user_id") int userId);
	
	@DeleteMapping("users/{user_id}")
	public ResponseEntity<String> deleteUser(@PathVariable("user_id") int userId);
}
