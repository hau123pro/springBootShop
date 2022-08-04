package com.laptrinhjava.demo.DTO;

import java.sql.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
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
@Table(name="oder")
public class Order {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID")
	private int ID;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Column(name = "Date_Create")
	private Date date_created;
	
	@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "User_ID")
	@JsonIgnore
	private User user;
	
	@Column(name="Quantity")
	private int quantity;
	
	@Column(name="Total_Price")
	private double totalPrice;
	
	@Column(name="Status")
	public String status;
	
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
	private Set<OrderDetail> listOrderDetail;
}
