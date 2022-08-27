package com.laptrinhjava.demo.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.laptrinhjava.demo.DTO.User;
import com.laptrinhjava.demo.exception.ErrorException;
import com.laptrinhjava.demo.service.UserService;

@Controller
@RequestMapping("/login")
public class LoginController {
	
	@Autowired
	UserService userService;
	
	
	@GetMapping
	public String index(Model model,@RequestParam(value="error",required =false) String error,HttpServletRequest request,HttpSession httpSession) {
			if(error!=null)
			 model.addAttribute("error", "Invalid username or password");
			if(request.getHeader("referer")!=null) {
				if(request.getHeader("referer").equals("http://localhost:8080/login")==false) {
				httpSession.setAttribute("url_pre",request.getHeader("referer") );
				
		
				}
				
			}
			else httpSession.setAttribute("url_pre","http://localhost:8080/home" );
		 return "login";
	}
	
	
	
}
