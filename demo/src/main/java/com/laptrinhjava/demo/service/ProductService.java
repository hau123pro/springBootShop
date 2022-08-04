package com.laptrinhjava.demo.service;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.laptrinhjava.demo.DTO.FilterPrice;
import com.laptrinhjava.demo.DTO.Product;
import com.laptrinhjava.demo.DTO.ProductColorSize;
import com.laptrinhjava.demo.exception.ErrorException;
import com.laptrinhjava.demo.repository.FilterPriceRepository;
import com.laptrinhjava.demo.repository.ProductRepository;

@Service
public class ProductService {
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	FilterPriceRepository priceRepo;
	
	@Transactional
	public List<Product> getAllProduct() throws ErrorException{
		 List<Product> list = new ArrayList<>();
		 list=productRepository.findAll();
		 return list;
	}
	
	@Transactional
	public Page<Product> getProductPage(int i) throws ErrorException{
		Page<Product> page = productRepository.findAll(PageRequest.of(i, 2));
		 return page;
	}
	
	@Transactional
	public Page<Product> getProductByKeyWord(int i,String keyWord,Integer colorid,Double minPrice,Double maxPrice) throws ErrorException{
		System.out.println(keyWord);
		Page<Product> page = productRepository.findByKeyword(keyWord,colorid,minPrice,maxPrice ,PageRequest.of(i, 2));
		 return page;
	}
	
	
	public Product getProductById(int id)throws ErrorException{
		Optional<Product> product=productRepository.findById(id);
		Product productByid=product.orElseThrow(()-> new ErrorException("Product not found"));
		return productByid;
	}
	
	public int getProductQuantity_ByColorSize(int id,int colorId,int sizeId) throws ErrorException {
		Optional<Product> product=productRepository.findById(id);
		Product productByid=product.orElseThrow(()-> new ErrorException("Product not found"));
		for(ProductColorSize p:productByid.getListColor_size()) {
			if(p.getColor().getColorID()==colorId&&p.getSize().getSizeID()==sizeId) {
				return p.getQuantity();
			}
		}
		return -1;
	}
	
	public int getProductQuantityAll(int id)throws ErrorException {
		Optional<Product> product=productRepository.findById(id);
		Product productByid=product.orElseThrow(()-> new ErrorException("Product not found"));
		int count=0;
		for(ProductColorSize p:productByid.getListColor_size()) {
			count=count+p.getQuantity();
		}
		return count;
	}
}
