package com.example.demo.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.demo.dto.UserDetail;

@Mapper
public interface UserdetailMapper {
    @Select("SELECT * FROM public.userdetail WHERE username = #{username}")
    UserDetail loadUserByUsername(String username);
    @Update("UPDATE userdetail SET password = #{password} WHERE username = #{username}")
    void updatePassword(String username, String password);
    @Select("SELECT * FROM userdetail")
    List<UserDetail> findAllUsers();
}