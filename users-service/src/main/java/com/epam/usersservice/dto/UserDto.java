package com.epam.usersservice.dto;

import javax.validation.constraints.NotBlank;

public class UserDto {
	
	private int userId;
	@NotBlank(message ="Username cannot be null")
	private String username;
	private String department;
	private String location;
	
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	
}
