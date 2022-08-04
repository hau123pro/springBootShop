package com.laptrinhjava.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.laptrinhjava.demo.DTO.Size;
import com.laptrinhjava.demo.exception.ErrorException;
import com.laptrinhjava.demo.repository.SizeRepository;

@Service
public class SizeService {

	@Autowired
	SizeRepository repository;
	
	public List<Size> getAllSize() throws ErrorException{
		List<Size> list=repository.findAll();
		return list;
	}
}
