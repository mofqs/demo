package com.example.demo.controller;

import com.example.demo.dto.UserForm;
import com.example.demo.persistence.UserdetailMapper;
import com.example.demo.security.PasswordEncryptor;
import com.example.demo.security.DatabaseUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;

@Controller
public class RegisterController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserdetailMapper userdetailMapper;

    @Autowired
    private DatabaseUserDetailsService userDetailsService;

    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        model.addAttribute("userForm", new UserForm());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid UserForm userForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "register";
        }

        String encodedPassword = passwordEncoder.encode(userForm.getPassword());

        userdetailMapper.createUser(userForm.getUsername(), encodedPassword);

        model.addAttribute("message", "Registration successful! Please login.");
        return "redirect:/login";
    }
}