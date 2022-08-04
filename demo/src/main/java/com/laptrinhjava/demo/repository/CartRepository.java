package com.laptrinhjava.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.laptrinhjava.demo.DTO.Cart;

public interface CartRepository extends JpaRepository<Cart, Integer>{
	@Modifying
	@Query("DELETE FROM Cart c WHERE c.cartId=:key")
	public void deleteByKey(@Param("key") int keyword);
}
