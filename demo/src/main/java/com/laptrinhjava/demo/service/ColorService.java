package com.laptrinhjava.demo.service;

import java.util.List;
import java.util.Optional;

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
	public Color getColorById (int id) throws ErrorException {
		Optional<Color> OptionalColor=colorRepositor.findById(id);
		Color color=OptionalColor.orElseThrow(()-> new ErrorException("Color not found"));
		return color;
		
	}
}
