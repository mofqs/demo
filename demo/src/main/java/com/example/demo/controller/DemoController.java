package com.example.demo.controller;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.demo.dto.User;
import com.example.demo.form.PersonForm;
import com.example.demo.persistence.UserMapper;

@Controller

public class DemoController implements WebMvcConfigurer {



    @Autowired
    private UserMapper mapper;
    public void map() {
    	mapper.getUserById(2);
    }
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/results").setViewName("results");
	}
	@GetMapping ("/")
	public String showForm(PersonForm personForm, Model model) {
		User user = mapper.getUserById(1);
		model.addAttribute("username22","a");
		return "form";
	}
	@PostMapping("/")
	public String checkPersonInfo(@Valid PersonForm personForm, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return "form";
		}
		return "redirect:/results";
	}


}
