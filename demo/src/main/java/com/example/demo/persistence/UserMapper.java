package com.example.demo.persistence;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.dto.User;
@Mapper
public interface UserMapper {
	User getUserById(Integer id);
	/*
	 * void createUser(User user); void updateCard(User user); void
	 * deleteCard(Integer id); int getNumOfUser();
	 */
}