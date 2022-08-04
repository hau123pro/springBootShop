package com.laptrinhjava.demo.DTO;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Entity
@Table(name="order_detail")
public class OrderDetail {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID")
	public int ID;
	
	@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Order_ID")
	@JsonIgnore
	public Order order;
	
	@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Product_ID")
	private Product product;
	
	@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Color_ID")
	private Color color;
	
	@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Size_ID")
	private Size size;
	
	@Column(name="Quantity")
	public int quantity;
	
	@Column(name="Price")
	private double totalPrice;
	
	
	
}
