package com.laptrinhjava.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.laptrinhjava.demo.DTO.FilterPrice;
import com.laptrinhjava.demo.exception.ErrorException;
import com.laptrinhjava.demo.repository.FilterPriceRepository;

@Service
public class FilterPriceService {
	
	@Autowired
	FilterPriceRepository filterPriceRepository;
	
	public List<FilterPrice> getAllFilterPrice() throws ErrorException{
		List<FilterPrice> list=filterPriceRepository.findAll();
		return list;
	}
}
