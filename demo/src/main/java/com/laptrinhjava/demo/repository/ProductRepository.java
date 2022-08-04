package com.laptrinhjava.demo.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.laptrinhjava.demo.DTO.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>{
	@Query("SELECT DISTINCT p "
			+ "FROM Product p , ProductColorSize c "
			+ "WHERE p.productID=c.product.productID "
			+ "and p.name LIKE %:key% "
			+ "and ( c.color.colorID=:color or :color is null )"
			+ "and ( p.price > :minprice or :minprice is null )"
			+ "and ( p.price < :maxprice or :maxprice is null )")
	public Page<Product> findByKeyword(
			@Param("key") String keyword,
			@Param("color") Integer colorId,
			@Param("minprice") Double minprice,
			@Param("maxprice") Double maxprice,
			Pageable pageable);
}
