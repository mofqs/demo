package com.example.demo.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.demo.dto.UserDetail;

@Mapper
public interface UserdetailMapper {

    @Select("SELECT * FROM public.userdetail WHERE username = #{username}")
    UserDetail loadUserByUsername(String username);

    @Update("UPDATE public.userdetail SET password = #{password} WHERE username = #{username}")
    void updatePassword(String username, String password);

    @Update("UPDATE public.userdetail SET username = #{newUsername} WHERE username = #{username}")
    void updateUsername(@Param("username") String username, @Param("newUsername") String newUsername);

    @Select("SELECT * FROM public.userdetail")
    List<UserDetail> findAllUsers();

    @Insert("INSERT INTO public.userdetail (username, password, roles) VALUES (#{username}, #{password}, 'USER')")
    void createUser(String username, String password);
}