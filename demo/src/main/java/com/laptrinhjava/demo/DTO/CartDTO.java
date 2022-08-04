package com.laptrinhjava.demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartDTO {
	
	private int productID;
	
	private int sizeID;
	
	private int colorID;
	
	private double totalPrice;
	
	private int quantity;
	
}
