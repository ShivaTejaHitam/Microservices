package com.epam.usersservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.epam.usersservice.entities.User;

public interface UserRepository extends JpaRepository<User,Integer>{
	
}
