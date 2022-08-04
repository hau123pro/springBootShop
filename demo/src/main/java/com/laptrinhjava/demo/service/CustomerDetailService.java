package com.laptrinhjava.demo.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.laptrinhjava.demo.DTO.User;
import com.laptrinhjava.demo.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;


@Service
public class CustomerDetailService implements UserDetailsService{
	@Autowired
	UserRepository userRepo;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		 Optional<User> userOptional = userRepo.findUserByUserName(username);
	     User user = userOptional.orElseThrow(()-> new UsernameNotFoundException("User not found"));
	    
	     //tạo list authoritis để nhận quyền của tài khoản
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		GrantedAuthority authority=new SimpleGrantedAuthority("ROLE_USER");
		authorities.add(authority);
		//trả về userdetail chứa username,password và list quyền của nó
		return new org.springframework.security.core.userdetails.User(user.getUserName(),user.getPassword(),authorities);


	}

}
