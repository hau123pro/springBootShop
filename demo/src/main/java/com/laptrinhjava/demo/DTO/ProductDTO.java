package com.laptrinhjava.demo.DTO;

import javax.validation.constraints.NotEmpty;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
	@NotEmpty(message = "Missing Id")
	private int ID;
	
	@NotEmpty(message = "Missing Product Name")
	private String productName;
	
	@NotEmpty(message = "Missing quantity")
	private int quantity;
	
	@NotEmpty(message = "Missing color Id")
	private int colorId;
	
	@NotEmpty(message = "Missing size Id")
	private int sizeId;
	
	@NotEmpty(message = "Missing price")
	private double price;
	
	@NotEmpty(message = "Missing img file")
	private MultipartFile imgFile;
	
	
}
