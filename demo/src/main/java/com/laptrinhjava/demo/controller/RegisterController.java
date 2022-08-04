package com.laptrinhjava.demo.controller;


import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.servlet.ServletContext;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.laptrinhjava.demo.DTO.User;
import com.laptrinhjava.demo.DTO.UserRegistration;
import com.laptrinhjava.demo.DTO.test;
import com.laptrinhjava.demo.exception.ErrorException;
import com.laptrinhjava.demo.service.UserService;

@Controller
@RequestMapping("/register")
public class RegisterController {
	ObjectMapper object=new ObjectMapper();
	@Autowired
	UserService userService;
	@Autowired
	ServletContext application;
	//trả về trang đăng kí
	@GetMapping
	public String index(Model model) {
		UserRegistration u=new UserRegistration();
		model.addAttribute("user",u);
		 return "register";
	}
	//Thêm mới 1 user 
	@PostMapping
	public String addUser(@ModelAttribute UserRegistration u,BindingResult result,Model model,RedirectAttributes redirectAttributes) {
		System.out.println(u.getEmail());
		if(result.hasErrors()) {
			redirectAttributes.addFlashAttribute("errors", "!!!");
			return "redirect:/register";
		}
		else if(!u.getPassword().equals(u.getConfirmPassword())) {
			
			try {
				userService.registrationUser(u);
				redirectAttributes.addFlashAttribute("success", "Account created successfully.Please login! ");
				return "redirect:/login";
			}
			catch(ErrorException e) {
				System.out.println(e.getMessage());
				redirectAttributes.addFlashAttribute("errors", e.getMessage());
				return "redirect:/register";
			}
		}
		model.addAttribute("user",u);
		return "register";
	}
	//test api send giá trị null attribute
	@PostMapping(value="/api")
	@ResponseBody
	public String addUser2(@RequestBody @Valid UserRegistration u,BindingResult result,Model model) {
		if(result.hasErrors()) {
//			System.out.println(u.toString());
			return "error";
		}
//		System.out.println(u.toString());
		return "hehe";

	}
	//test send image file and json
	@PostMapping(value="/img",consumes = { "multipart/form-data" })
	@ResponseBody
	public String testImg(@RequestParam ("File") MultipartFile u ,@RequestParam ("json") String a) throws JsonMappingException, JsonProcessingException{
		Path path=Paths.get("upload/");
		try {
			InputStream inputStream=u.getInputStream();
			Files.copy(inputStream, path.resolve(u.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
		}catch(Exception e) {
			e.printStackTrace();
		}
		test b=object.readValue(a, test.class);
		System.out.println(b.getImgUrl());
		return "error";
	}
	@GetMapping(value="/test")
	public String test() {
		
		 return "index";
	}
}
