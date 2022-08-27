package com.laptrinhjava.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.laptrinhjava.demo.DTO.Product;
import com.laptrinhjava.demo.DTO.User;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>{
	@Query("SELECT DISTINCT p "
			+ "FROM Product p , ProductColorSize c "
			+ "WHERE p.productID=c.product.productID "
			+ "and p.name LIKE %:key% "
			+ "and ( c.color.colorID=:color or :color is null ) "
			+ "and ( c.size.sizeID=:size or :size is null ) "
			+ "and ( p.price > :minprice or :minprice is null ) "
			+ "and ( p.price < :maxprice or :maxprice is null ) "
			+ "and p.status='Active' ")
	public Page<Product> findByKeyword(
			@Param("key") String keyword,
			@Param("color") Integer colorId,
			@Param("size") Integer sizeId,
			@Param("minprice") Double minprice,
			@Param("maxprice") Double maxprice,
			Pageable pageable);
	
	@Query("SELECT p "
			+ "FROM Product p "
			+ "WHERE p.status=:status ")
	public Page<Product> findByStatus(
			@Param("status") String status,
			Pageable pageable);
	
	@Query("SELECT DISTINCT p "
			+ "FROM Product p , ProductColorSize c "
			+ "WHERE p.productID=c.product.productID "
			+ "and p.name LIKE %:key% "
			+ "and ( c.color.colorID=:color or :color is null ) "
			+ "and ( c.size.sizeID=:size or :size is null ) "
			+ "and ( p.price > :minprice or :minprice is null ) "
			+ "and ( p.price < :maxprice or :maxprice is null ) ")
	public Page<Product> findByAdminkey(
			@Param("key") String adminkey,
			@Param("color") Integer colorId,
			@Param("size") Integer sizeId,
			@Param("minprice") Double minprice,
			@Param("maxprice") Double maxprice,
			Pageable pageable);
	
	@Query("SELECT SUM(c.quantity) "
			+ "FROM Product p , ProductColorSize c "
			+ "WHERE p.productID=c.product.productID "
			+ "and p.productID=:product "
			+ "and ( c.color.colorID=:color or :color is null ) "
			+ "and ( c.size.sizeID=:size or :size is null ) "
			+ "and ( p.price > :minprice or :minprice is null ) "
			+ "and ( p.price < :maxprice or :maxprice is null ) " )
	public Integer findByProductid(
			@Param("product") Integer productid,
			@Param("color") Integer colorId,
			@Param("size") Integer sizeId,
			@Param("minprice") Double minprice,
			@Param("maxprice") Double maxprice
			);
	
	public Optional<Product> findProductByName(String name);
}
