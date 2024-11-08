package com.example.demo.controller;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.dto.User;
import com.example.demo.form.PersonForm;
import com.example.demo.persistence.UserMapper;

@Controller

public class DemoController implements WebMvcConfigurer {



    @Autowired
    private UserMapper mapper;
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/results").setViewName("results");
	}
	@GetMapping ("/")
	public String showForm(PersonForm personForm, Model model) {

		int userCount = mapper.getNumOfUser();

	    model.addAttribute("userCount", userCount);

		return "form";
	}

	@PostMapping("/")
	public String checkPersonInfo(@Valid PersonForm personForm, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {

	    int userCount = mapper.getNumOfUser();
	    model.addAttribute("userCount", userCount);

		if (bindingResult.hasErrors()) {
			return "form";
		}

		/*
		 * User existingUser = mapper.getUserById(personForm.getId());
		 *
		 * if (existingUser != null) { model.addAttribute("message", "用户已存在");
		 * model.addAttribute("username", existingUser.getName());
		 * model.addAttribute("userage", existingUser.getAge()); } else { User newUser =
		 * new User(); newUser.setId(personForm.getId());
		 * newUser.setName(personForm.getName()); newUser.setAge(personForm.getAge());
		 *
		 * mapper.createUser(newUser); model.addAttribute("message", "用户已创建");
		 * model.addAttribute("username", newUser.getName());
		 * model.addAttribute("userage", newUser.getAge()); } return "results";
		 */
	    User existingUser = mapper.getUserById(personForm.getId());

	    if (existingUser != null) {
	        boolean isUpdated = false;

	        if (!existingUser.getName().equals(personForm.getName())) {
	            existingUser.setName(personForm.getName());
	            isUpdated = true;
	        }

	        if (existingUser.getAge() != personForm.getAge()) {
	            existingUser.setAge(personForm.getAge());
	            isUpdated = true;
	        }

	        if (isUpdated) {
	            mapper.updateUser(existingUser);
	            redirectAttributes.addFlashAttribute("message", "用户信息已更新");
	        } else {
	            redirectAttributes.addFlashAttribute("message", "用户信息没有变化");
	        }
	        redirectAttributes.addFlashAttribute("username", existingUser.getName());
	        redirectAttributes.addFlashAttribute("userage", existingUser.getAge());
	    } else {
	        User newUser = new User();
	        newUser.setId(personForm.getId());
	        newUser.setName(personForm.getName());
	        newUser.setAge(personForm.getAge());

	        mapper.createUser(newUser);

	        redirectAttributes.addFlashAttribute("message", "用户已创建");
	        redirectAttributes.addFlashAttribute("username", newUser.getName());
	        redirectAttributes.addFlashAttribute("userage", newUser.getAge());
	    }

	    return "redirect:/results";
	}
	@PostMapping("/delete")
	public String deleteUser(@RequestParam("id") Integer id, RedirectAttributes redirectAttributes) {

	    User existingUser = mapper.getUserById(id);

	    if (existingUser != null) {
	        mapper.deleteUser(id);
	        redirectAttributes.addFlashAttribute("message", "用户已删除");
	    } else {
	        redirectAttributes.addFlashAttribute("message", "用户不存在");
	    }
	    redirectAttributes.addFlashAttribute("username", null);
	    redirectAttributes.addFlashAttribute("userage", null);
	    return "redirect:/results";
	}

}
