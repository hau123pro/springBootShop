package com.laptrinhjava.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.laptrinhjava.demo.DTO.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Integer>{
	@Modifying
	@Query("DELETE FROM CartItem c WHERE c.cartItemID=:key")
	public void deleteByKey(@Param("key") int keyword);
}
