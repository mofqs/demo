package com.example.demo.persistence;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import com.example.demo.dto.UserDetail;

@Mapper
public interface UserdetailMapper {
    @Select("SELECT * FROM public.userdetail WHERE username = #{username}")
    UserDetail loadUserByUsername(String username);
}