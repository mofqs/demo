package com.example.demo.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.dto.User;
@Mapper
public interface UserMapper {
	User getUserById(Integer id);

	void createUser(User user);


	void updateUser(User user);

	void deleteUser(Integer id);

	int getNumOfUser();

	List<User> getAllUsers();

}