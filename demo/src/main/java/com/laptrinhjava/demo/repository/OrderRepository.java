package com.laptrinhjava.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.laptrinhjava.demo.DTO.Order;
import com.laptrinhjava.demo.DTO.ProductColorSize;

public interface OrderRepository extends JpaRepository<Order, Integer>{
	@Modifying
	@Query("DELETE FROM Order c WHERE c.ID=:key")
	public void deleteByKey(@Param("key") int keyword);
}
