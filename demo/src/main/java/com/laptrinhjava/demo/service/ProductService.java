package com.laptrinhjava.demo.service;

import java.awt.print.Pageable;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.laptrinhjava.demo.DTO.Color;
import com.laptrinhjava.demo.DTO.FilterPrice;
import com.laptrinhjava.demo.DTO.Product;
import com.laptrinhjava.demo.DTO.ProductColorSize;
import com.laptrinhjava.demo.DTO.ProductDTO;
import com.laptrinhjava.demo.DTO.ProductManager;
import com.laptrinhjava.demo.DTO.Size;
import com.laptrinhjava.demo.exception.ErrorException;
import com.laptrinhjava.demo.repository.FilterPriceRepository;
import com.laptrinhjava.demo.repository.ProductRepository;

@Service
public class ProductService {
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	FilterPriceRepository priceRepo;
	
	@Autowired
	ColorService colorService;
	
	@Autowired
	SizeService sizeService;
	
	@Transactional
	public List<Product> getAllProduct() throws ErrorException{
		 List<Product> list = new ArrayList<>();
		 list=productRepository.findAll();
		 return list;
	}
	
	@Transactional
	public Page<Product> getProductPage(int i) throws ErrorException{
		Page<Product> page = productRepository.findByStatus("Active", PageRequest.of(i, 2));
		
		 return page;
	}
	
	@Transactional
	public Page<Product> getProductByKeyWord(int i,String keyWord,Integer colorid,Integer sizeId,Double minPrice,Double maxPrice) throws ErrorException{
		System.out.println(keyWord);
		Page<Product> page = productRepository.findByKeyword(keyWord,colorid,sizeId,minPrice,maxPrice ,PageRequest.of(i, 2));
		
		 return page;
	}
	@Transactional
	public Page<Product> getProductByAdminKeyWord(int i,String keyWord,Integer colorid,Integer sizeId,Double minPrice,Double maxPrice) throws ErrorException{
		System.out.println(keyWord);
		Page<Product> page = productRepository.findByAdminkey(keyWord,colorid,sizeId,minPrice,maxPrice ,PageRequest.of(i, 2));
		
		 return page;
	}
	
	@Transactional
	public Integer getQuantityProductBySearchAPI(Integer productId,Integer colorid,Integer sizeId,Double minPrice,Double maxPrice) throws ErrorException{
		Integer quantity=productRepository.findByProductid(productId, colorid, sizeId, minPrice, maxPrice);
		return quantity;
	}
	
	public Product getProductById(int id)throws ErrorException{
		Optional<Product> product=productRepository.findById(id);
		Product productByid=product.orElseThrow(()-> new ErrorException("Product not found"));
		if(productByid.getStatus().equals("Inactive")) 
			throw new ErrorException("Product not available");
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
	public List<ProductManager> getProductManagerAll(List<Product> listItem) throws ErrorException{
		List<ProductManager> listProductManager=new ArrayList<>();
		for(Product item:listItem) {
			int quantity=0;
			ProductManager manager=new ProductManager();
			manager.setProductID(item.getProductID());
			manager.setName(item.getName());
			manager.setImgUrl(item.getImgUrl());
			manager.setListCartItem(item.getListCartItem());
			manager.setListColor_size(item.getListColor_size());
			manager.setListOrderDetail(item.getListOrderDetail());
			manager.setPrice(item.getPrice());
			for(ProductColorSize items:item.getListColor_size()) {
				quantity=quantity+items.getQuantity();
			}
			manager.setQuantity(quantity);
			manager.setStatus(item.getStatus());
			listProductManager.add(manager);
		}
		return listProductManager;
	}
	
	@Transactional
	public Product updateProduct(ProductDTO productDto) throws ErrorException {
		Product product=getProductById(productDto.getID());
		if(product==null) throw new ErrorException("No products found");
		product.setName(productDto.getProductName());
		product.setPrice(productDto.getPrice());
		int index=0;
		for(ProductColorSize colorSize:product.getListColor_size()) {
			if(		
				colorSize.getColor().getColorID()==productDto.getColorId()
				&&colorSize.getSize().getSizeID()==productDto.getSizeId()
					) {
				index=1;
				colorSize.setQuantity(productDto.getQuantity());
			}
		}
		if(index==0) {
			Set<ProductColorSize> listColor_size=product.getListColor_size();
			ProductColorSize colorSize=new ProductColorSize();
			Color color=colorService.getColorById(productDto.getColorId());
			Size size=sizeService.getSizeById(productDto.getSizeId());
			colorSize.setColor(color);
			colorSize.setSize(size);
			colorSize.setProduct(product);
			colorSize.setQuantity(productDto.getQuantity());
			listColor_size.add(colorSize);
			product.setListColor_size(listColor_size);
		}
		MultipartFile img=productDto.getImgFile();
		Path path=Paths.get("src/main/resources/static/images/");
		Path resolvedPath
        = path.resolve(img.getOriginalFilename());
		 System.out.println("Resolved Path:"+ resolvedPath);
		 try {
				InputStream inputStream=img.getInputStream();
				Files.copy(inputStream, resolvedPath, StandardCopyOption.REPLACE_EXISTING);
		}catch(Exception e) {
				e.printStackTrace();
		}
		product.setImgUrl("images/"+img.getOriginalFilename());
		product.setStatus("Active");
		productRepository.save(product);
		return product;
	}
	public Product addProduct(ProductDTO productDto) throws ErrorException {
		Optional<Product> optional=productRepository.findProductByName(productDto.getProductName());
		Product product=optional.orElse(null);
		if(product!=null) throw new ErrorException("Name has exist!!!");
		else product=new Product();
		MultipartFile img=productDto.getImgFile();
		Path path=Paths.get("src/main/resources/static/images/");
		Path resolvedPath
        = path.resolve(img.getOriginalFilename());
		 System.out.println("Resolved Path:"+ resolvedPath);
		 if(Files.exists(resolvedPath) && !Files.isDirectory(resolvedPath)) 
			 throw new ErrorException("File image has exist!!!");
		 product.setName(productDto.getProductName());
		 product.setPrice(productDto.getPrice());
		 try {
				InputStream inputStream=img.getInputStream();
				Files.copy(inputStream, resolvedPath, StandardCopyOption.REPLACE_EXISTING);
		}catch(Exception e) {
				e.printStackTrace();
		}
		 product.setImgUrl("images/"+img.getOriginalFilename());
		 Set<ProductColorSize> listColor_size=new HashSet<>();
		ProductColorSize colorSize=new ProductColorSize();
		Color color=colorService.getColorById(productDto.getColorId());
		Size size=sizeService.getSizeById(productDto.getSizeId());
		colorSize.setColor(color);
		colorSize.setSize(size);
		colorSize.setProduct(product);
		colorSize.setQuantity(productDto.getQuantity());
		listColor_size.add(colorSize);
		product.setListColor_size(listColor_size);
		product.setStatus("Active");
		productRepository.save(product);
		return product;
	}
	public Product updateStatusProduct(Integer id,String status) throws ErrorException {
		Optional<Product> optional=productRepository.findById(id);
		Product product=optional.orElse(null);
		if(product==null) throw new ErrorException("No product found");
		if(product.getStatus().equals(status)) throw new ErrorException("Status not match!!!");
		product.setStatus(status);
		productRepository.save(product);
		return product;
	}
}
