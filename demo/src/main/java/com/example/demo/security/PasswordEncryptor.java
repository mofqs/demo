package com.example.demo.security;

import com.example.demo.dto.UserDetail;
import com.example.demo.persistence.UserdetailMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.List;

//@Component
public class PasswordEncryptor implements CommandLineRunner {

    @Autowired
    private UserdetailMapper userdetailMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        List<UserDetail> users = userdetailMapper.findAllUsers();
        for (UserDetail user : users) {
            String encodedPassword = passwordEncoder.encode(user.getPassword());
            userdetailMapper.updatePassword(user.getUsername(), encodedPassword);
            System.out.println("Updated password for user: " + user.getUsername());
        }
    }
}