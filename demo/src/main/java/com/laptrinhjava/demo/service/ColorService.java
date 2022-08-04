package com.laptrinhjava.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.laptrinhjava.demo.DTO.Color;
import com.laptrinhjava.demo.exception.ErrorException;
import com.laptrinhjava.demo.repository.ColorRepository;



@Service
public class ColorService {
	
	@Autowired
	ColorRepository colorRepositor;
	
	public List<Color> getAllColor() throws ErrorException{
		List<Color> list=colorRepositor.findAll();
		return list;
	}
	
}
