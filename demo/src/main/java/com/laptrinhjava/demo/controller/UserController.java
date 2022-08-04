package com.laptrinhjava.demo.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.laptrinhjava.demo.DTO.User;
import com.laptrinhjava.demo.exception.ErrorException;
import com.laptrinhjava.demo.service.UserService;

@Controller
public class UserController {
	@Autowired
	UserService userService;
	
	@GetMapping(value ="/home")
	public String home(HttpServletRequest request,HttpSession httpSession) {
		if(request.getUserPrincipal()!=null) {
		httpSession.setAttribute("username", request.getUserPrincipal().getName());
		
		}
        return "index";
	}
	
	@GetMapping(value="/getAllUser")
	public ResponseEntity<Object> getAllUser() throws ErrorException{
		List<User> list=userService.getAllUser();
		return new ResponseEntity<>(list,HttpStatus.OK);
	}
	
	@GetMapping(value="/getUser")
	public ResponseEntity<Object> getUser(@RequestParam("username") String userName) 
			throws ErrorException{
		User user=userService.getUser(userName);
		return new ResponseEntity<>(user,HttpStatus.OK);
	}
}
