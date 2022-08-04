package com.laptrinhjava.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.laptrinhjava.demo.DTO.Product;
import com.laptrinhjava.demo.exception.ErrorException;
import com.laptrinhjava.demo.service.ProductService;

@RestController
public class testRest {
	
	@Autowired
	ProductService productService;
	
	// Test many to many của entity product
	@GetMapping(value="/products/{id}")
	public ResponseEntity<Product> getOneProduct (
			@PathVariable(value="id") int productId
			)
	{
		Product productByid;
		try {
			productByid = productService.getProductById(productId);
			return new ResponseEntity<>(productByid,HttpStatus.OK);
		} catch (ErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<>(null,HttpStatus.OK);
		}
		
	}
}
