package com.example.demo.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.UserDetail;
import com.example.demo.persistence.UserdetailMapper;

@Service
public class DatabaseUserDetailsService implements UserDetailsService {

    private final UserdetailMapper userdetailMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DatabaseUserDetailsService(UserdetailMapper userdetailMapper, PasswordEncoder passwordEncoder) {
        this.userdetailMapper = userdetailMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetail userDetail = userdetailMapper.loadUserByUsername(username);
        if (userDetail == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return User.builder()
                .username(userDetail.getUsername())
                .password(userDetail.getPassword())
                .roles(userDetail.getRoles().split(","))
                .build();
    }
}