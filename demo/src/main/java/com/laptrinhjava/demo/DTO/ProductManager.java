package com.laptrinhjava.demo.DTO;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductManager {
	
	private int productID;

	private String name;
	
	private double price;
	
	private String imgUrl;
	
	private Set<ProductColorSize> listColor_size;
	
	private Set<CartItem> listCartItem;

	private Set<OrderDetail> listOrderDetail;
	
	private int quantity;
	
	private String status;
}
