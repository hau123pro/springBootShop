package com.laptrinhjava.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.laptrinhjava.demo.DTO.User;
import com.laptrinhjava.demo.DTO.UserRegistration;
import com.laptrinhjava.demo.exception.ErrorException;
import com.laptrinhjava.demo.repository.UserRepository;



@Service
public class UserService {
	@Autowired
	UserRepository userrepo;
	@Autowired
	PasswordEncoder encoder;
	
	@Transactional
	public User registrationUser(UserRegistration userRegister) throws ErrorException{
		Optional existing=userrepo.findUserByUserName(userRegister.getUserName());
		if(existing.isPresent()) {
			throw new ErrorException("User already exist!");
		}
		User user = new User();
		
		user.setEmail(userRegister.getEmail());
		user.setMaVT("1");
		user.setMaTT("1");
		user.setPassword(encoder.encode(userRegister.getPassword()));
		user.setUserName(userRegister.getUserName());
		userrepo.save(user);
		return user;
	}
	public User getUser(String username) throws ErrorException{
		Optional<User> userOptional = userrepo.findUserByUserName(username);
	     User user = userOptional.orElseThrow(()-> new ErrorException("User not found"));
		return user;
	}
	
	public List<User> getAllUser() throws ErrorException{
		List<User> list = userrepo.findAll();
		return list;
	}
}
