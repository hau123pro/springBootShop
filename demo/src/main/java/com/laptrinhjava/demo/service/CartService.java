package com.laptrinhjava.demo.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

import com.laptrinhjava.demo.DTO.Cart;
import com.laptrinhjava.demo.DTO.CartItem;
import com.laptrinhjava.demo.DTO.Color;
import com.laptrinhjava.demo.DTO.Product;
import com.laptrinhjava.demo.DTO.ProductColorSize;
import com.laptrinhjava.demo.DTO.Size;
import com.laptrinhjava.demo.DTO.User;
import com.laptrinhjava.demo.exception.ErrorException;
import com.laptrinhjava.demo.repository.CartItemRepository;
import com.laptrinhjava.demo.repository.CartRepository;
import com.laptrinhjava.demo.repository.ColorRepository;
import com.laptrinhjava.demo.repository.SizeRepository;

@Service
public class CartService {
	
	@Autowired
	UserService userService;
	
	@Autowired
	ProductService productService;
	
	@Autowired
	ColorRepository colorRepository;
	
	@Autowired
	SizeRepository sizeRepo;
	
	@Autowired
	CartRepository cartRepository;
	
	@Autowired
	CartItemRepository cartItemRepository;
	
	@Transactional
	public Cart addToCart(
			int productId, 
			int sizeId,
			int colorId,
			int quantity,
			String username
			) throws ErrorException {
		User user=userService.getUser(username);
		Product p=productService.getProductById(productId);
		Optional<Color> c=colorRepository.findById(colorId);
		Color color=c.orElseThrow(()-> new ErrorException("error"));
		Optional<Size> s=sizeRepo.findById(sizeId);
		Size size=s.orElseThrow(()-> new ErrorException("error"));
		int quantityProduct=0;
		double price=p.getPrice()*quantity;
		for(ProductColorSize item:p.getListColor_size()) {
			if(item.getColor().getColorID()==colorId
					&&item.getSize().getSizeID()==sizeId) {
				quantityProduct=item.getQuantity();
			}
		}
		CartItem cartItem=new CartItem();
		Cart cart=new Cart();
		Boolean checkType=false;
		for(ProductColorSize typeProduct:p.getListColor_size()) {
			if(typeProduct.getColor().getColorID()==colorId
					&&typeProduct.getSize().getSizeID()==sizeId) {
				checkType=true;
			}
		}
		if(checkType==false) {
			throw new ErrorException("not have product yet");
		}
		cartItem.setSize(size);
		cartItem.setColor(color);
		cartItem.setProduct(p);
		cartItem.setQuantity(quantity);
		cartItem.setTotalPrice(Double.parseDouble(String.format("%,.2f", price)));
		List<Cart> listCart=getAllCart();
		Set<CartItem> cartItems = new HashSet<CartItem>();
		Boolean checkItem=false;
		Boolean checkEmptyCart=false;
		if(!listCart.isEmpty()) {
			for(Cart cartObj: listCart) {
				if(cartObj.getUser().getUserID()==user.getUserID()) {
					
					checkEmptyCart=true;
					cart=cartObj;
					cartItems=cartObj.getListCartItem();
					cart.setQuantiy(cartObj.getQuantiy()+quantity);
					cart.setTotalPrice(cartObj.getTotalPrice()+price);
					for(CartItem cartItemObj: cartItems) {
						if(cartItemObj.getProduct().getProductID()==productId
							&&cartItemObj.getColor().getColorID()==colorId
							&&cartItemObj.getSize().getSizeID()==sizeId
							) {
							int quantityItem=cartItemObj.getQuantity()+quantity;
							if(quantityItem>quantityProduct)
								throw  new ErrorException("exceed the specified amount");
							cartItemObj.setQuantity(quantityItem);
							cartItemObj.setTotalPrice(cartItemObj.getTotalPrice()+Double.parseDouble(String.format("%,.2f", price)));
							checkItem=true;
						}
					}
					if(checkItem==false) {
						cartItem.setCart(cart);
						cartItems.add(cartItem);
					}
					
					cart.setListCartItem(cartItems);					
				}
			}
		}
		if(checkEmptyCart==false) {
			cart.setUser(user);
			cart.setQuantiy(quantity);
			cart.setTotalPrice(Double.parseDouble(String.format("%,.2f", price)));
			cartItem.setCart(cart);
			cartItems.add(cartItem);
			cart.setListCartItem(cartItems);	
		}
		cartRepository.save(cart);
		return cart;
	}
	
	public Cart updateCart(int id,int sizeId,int colorId,int quantity,String username) throws ErrorException {
		User user=userService.getUser(username);
		Product product=productService.getProductById(id);
		Cart cart;
		ProductColorSize productColorSize=product.getListColor_size()
				.stream()
				.filter(p->p.getProduct().getProductID()==id)
				.filter(p->p.getColor().getColorID()==colorId)
				.filter(p->p.getSize().getSizeID()==sizeId)
				.findAny()
				.orElse(null);
		cart=user.getCart();
		CartItem item=cart.getListCartItem()
				.stream()
				.filter(p->p.getProduct().getProductID()==id)
				.filter(p->p.getColor().getColorID()==colorId)
				.filter(p->p.getSize().getSizeID()==sizeId)
				.findAny()
				.orElse(null);
		if(quantity<=productColorSize.getQuantity()) {
//			System.out.println("quantity small");
			double price=product.getPrice()*quantity;
//			System.out.println("cart total price: "+cart.getTotalPrice()+",price: "+price+",item total price:"+item.getTotalPrice());
//			System.out.println(cart.getTotalPrice()+price-item.getTotalPrice());
			cart.setTotalPrice(Double.parseDouble(String.format("%,.2f",cart.getTotalPrice()
																		+price
																		-item.getTotalPrice())));
//			System.out.println("cart total sl: "+cart.getQuantiy()+",sl: "+quantity+",item sl:"+item.getQuantity());
			cart.setQuantiy(productColorSize.getQuantity()
							+cart.getQuantiy()
							-item.getQuantity());
//			System.out.println("cart total sl: "+cart.getQuantiy()+",sl: "+quantity+",item sl:"+item.getQuantity());
			item.setQuantity(quantity);
			item.setTotalPrice(Double.parseDouble(String.format("%,.2f", price)));
//			System.out.println("cart total price: "+cart.getTotalPrice()+",price: "+price+",item total price:"+item.getTotalPrice());
			cartRepository.save(cart);
		}
		else {
//			System.out.println("quantity big");
			double price=product.getPrice()*productColorSize.getQuantity();
//			System.out.println("cart total price: "+cart.getTotalPrice()+",price: "+price+",item total price:"+item.getTotalPrice());
			cart.setTotalPrice(Double.parseDouble(String.format("%,.2f",cart.getTotalPrice()
																		+price
																		-item.getTotalPrice())));
//			System.out.println("cart total sl: "+cart.getQuantiy()+",sl: "+quantity+",item sl:"+item.getQuantity());
			cart.setQuantiy(productColorSize.getQuantity()
							+cart.getQuantiy()
							-item.getQuantity());
//			System.out.println("cart total sl: "+cart.getQuantiy()+",sl: "+quantity+",item sl:"+item.getQuantity());
			item.setQuantity(productColorSize.getQuantity());
			item.setTotalPrice(Double.parseDouble(String.format("%,.2f", price)));
			System.out.println("cart total price: "+cart.getTotalPrice()+",price: "+price+",item total price:"+item.getTotalPrice());
			cartRepository.save(cart);
			throw new ErrorException("The amount you can buy is "+ item.getQuantity());
		}
		return cart;
	}
	
	@Transactional
	public Cart deleteSingleProductCart(int productId,
										int colorId,
										int sizeId,
										String username) throws ErrorException {
		User user=userService.getUser(username);
		Cart cart=user.getCart();
		Set<CartItem> cartItems =cart.getListCartItem();
		cartItems.stream().forEach((item)->{
			if(item.getProduct().getProductID()==item.getProduct().getProductID()
				&&item.getSize().getSizeID()==item.getSize().getSizeID()
				&&item.getColor().getColorID()==item.getColor().getColorID()) {
		    	
				cartItemRepository.deleteByKey(item.getCartItemID());
				cartItems.remove(item);
			}
		});
		if(cartItems.size()==0) {
			cartRepository.deleteByKey(cart.getCartId());
		}
		return cart;
	}
	
	@Transactional
	public Cart getCart(int id) throws ErrorException {
		Optional<Cart> cart=cartRepository.findById(id);
		Cart c=cart.orElseThrow(()-> new ErrorException("error"));
		return c;
	}
	
	
	public List<Cart> getAllCart() {
		List<Cart> listCart=cartRepository.findAll();
		return listCart;
	}
}
