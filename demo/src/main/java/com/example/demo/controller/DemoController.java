package com.example.demo.controller;

import jakarta.validation.Valid;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
    private MessageSource messageSource;

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

	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
	        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
	        model.addAttribute("currentuser", userDetails.getUsername());
	    } else {
	        model.addAttribute("currentuser", "Guest");
	    }

		return "form";
	}

	@PostMapping("/")
	public String checkPersonInfo(@Valid PersonForm personForm, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

		if (bindingResult.hasErrors()) {
			return "form";
		}
		//不跳转
		/*
		 * User existingUser = mapper.getUserById(personForm.getId());
		 *
		 * if (existingUser != null) { model.addAttribute("message", messageSource.getMessage("form.userExists", null, LocaleContextHolder.getLocale()));
		 * model.addAttribute("username", existingUser.getName());
		 * model.addAttribute("userage", existingUser.getAge()); } else { User newUser =
		 * new User(); newUser.setId(personForm.getId());
		 * newUser.setName(personForm.getName()); newUser.setAge(personForm.getAge());
		 *
		 * mapper.createUser(newUser); model.addAttribute("message", messageSource.getMessage("form.userCreated", null, LocaleContextHolder.getLocale()));
		 * model.addAttribute("username", newUser.getName());
		 * model.addAttribute("userage", newUser.getAge()); } return "results";
		 */
		//跳转
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
	            redirectAttributes.addFlashAttribute("message", messageSource.getMessage("form.userUpdated", null, LocaleContextHolder.getLocale()));
	        } else {
	            redirectAttributes.addFlashAttribute("message", messageSource.getMessage("form.userExists", null, LocaleContextHolder.getLocale()));
	        }
	        redirectAttributes.addFlashAttribute("username", existingUser.getName());
	        redirectAttributes.addFlashAttribute("userage", existingUser.getAge());
	    } else {
	        User newUser = new User();
	        newUser.setId(personForm.getId());
	        newUser.setName(personForm.getName());
	        newUser.setAge(personForm.getAge());

	        mapper.createUser(newUser);

	        redirectAttributes.addFlashAttribute("message", messageSource.getMessage("form.userCreated", null, LocaleContextHolder.getLocale()));
	        redirectAttributes.addFlashAttribute("username", newUser.getName());
	        redirectAttributes.addFlashAttribute("userage", newUser.getAge());
	    }

	    return "redirect:/results";
	}
	@PostMapping("/delete")
	public String deleteUser(@RequestParam Integer id, RedirectAttributes redirectAttributes) {

	    User existingUser = mapper.getUserById(id);

	    if (existingUser != null) {
	        mapper.deleteUser(id);
	        redirectAttributes.addFlashAttribute("message", messageSource.getMessage("form.userDeleted", null, LocaleContextHolder.getLocale()));
	    } else {
	        redirectAttributes.addFlashAttribute("message", messageSource.getMessage("form.userNotFound", null, LocaleContextHolder.getLocale()));
	    }
	    redirectAttributes.addFlashAttribute("username", null);
	    redirectAttributes.addFlashAttribute("userage", null);
	    return "redirect:/results";
	}

	@GetMapping("/login")
	public String showLoginPage() {
	    return "login";
	}
}
