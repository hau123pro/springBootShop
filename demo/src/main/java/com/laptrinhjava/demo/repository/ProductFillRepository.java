package com.laptrinhjava.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.laptrinhjava.demo.DTO.ProductColorSize;

public interface ProductFillRepository extends JpaRepository<ProductColorSize, Integer>{
	
	@Query("SELECT c FROM ProductColorSize c "
			+ "WHERE c.product.productID=:productId and"
			+ " c.color.colorID=:colorId and"
			+ " c.size.sizeID=:sizeId")
	public ProductColorSize findQuantityByProductColorSize(
			@Param("productId") int productId,
			@Param("colorId") int colorId,
			@Param("sizeId") int sizeId);
}
