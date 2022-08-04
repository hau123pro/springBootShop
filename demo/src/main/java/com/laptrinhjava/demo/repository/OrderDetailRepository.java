package com.laptrinhjava.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.laptrinhjava.demo.DTO.OrderDetail;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer>{
	@Modifying
	@Query("DELETE FROM OrderDetail c WHERE c.ID=:key")
	public void deleteByKey(@Param("key") int keyword);
}
