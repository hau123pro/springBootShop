package com.laptrinhjava.demo.DTO;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="size")
public class Size {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID")
	private int sizeID;
	
	@Column(name="Name")
	private String sizeName;
	
	
	@OneToMany(mappedBy = "size", cascade = CascadeType.ALL)
	@JsonIgnore
	private Set<ProductColorSize> listColor_size;
	
	@OneToMany(mappedBy = "size",cascade = CascadeType.ALL)
	@JsonIgnore
	private Set<CartItem> listCartItem;
	
	@OneToMany(mappedBy = "size", cascade = CascadeType.ALL)
	@JsonIgnore
	private Set<OrderDetail> listOrderDetail;
}
