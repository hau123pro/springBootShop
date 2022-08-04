package com.laptrinhjava.demo.controller;

import java.net.URI;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.laptrinhjava.demo.DTO.Cart;
import com.laptrinhjava.demo.DTO.User;
import com.laptrinhjava.demo.exception.ErrorException;
import com.laptrinhjava.demo.service.CartService;
import com.laptrinhjava.demo.service.UserService;

@Controller
public class CartController {
	
	@Autowired
	CartService cartService;
	
	@Autowired
	UserService userService;
	
	@GetMapping(value="/addtoCart")
	public ResponseEntity<Object> addtoCart(
			@RequestParam("id") int productId,
			@RequestParam("sizeId") int sizeId,
			@RequestParam("colorId") int colorId,
			@RequestParam("quantity") int quantity,
			HttpSession httpSession
			)  {
		Cart cart;
		if(httpSession.getAttribute("username")==null) {
			System.out.print("ko co tai khoan");
		     return new ResponseEntity<>("Not have account", HttpStatus.OK);
		     }
		try {
			System.out.println("quantity:"+ quantity);
			cart = cartService.addToCart(
					productId, 
					sizeId, 
					colorId, 
					quantity,
					String.valueOf(httpSession.getAttribute("username"))
					);
			return new ResponseEntity<>(cart, HttpStatus.OK);
		} catch (ErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			if(e.getMessage()=="exceed the specified amount")
			return new ResponseEntity<>(" Số lượng Sản phẩm đã vượt quá mức đặt hàng", HttpStatus.BAD_REQUEST);
			else if(e.getMessage()=="not have product yet")
				return new ResponseEntity<>("Loại sản phẩm không tồn tại", HttpStatus.BAD_REQUEST);
			else 
				return new ResponseEntity<>("error", HttpStatus.BAD_REQUEST);
		}
	}
	@PostMapping(value="/getCart")
	public ResponseEntity<Object> getCart(
			
			) throws ErrorException {
		List<Cart> cart=cartService.getAllCart();
		return new ResponseEntity<Object>(cart, HttpStatus.OK);
	}
	
	@DeleteMapping(value="/deleteProductCart")
	public ResponseEntity<Object> deleteCart(
			@RequestParam("id") int productId,
			@RequestParam("sizeId") int sizeId,
			@RequestParam("colorId") int colorId,
			 HttpSession httpSession
			){
		try {
			Cart cart=cartService.deleteSingleProductCart(
					productId, 
					colorId, 
					sizeId, 
					String.valueOf(httpSession.getAttribute("username")));
			return new ResponseEntity<>(cart,HttpStatus.OK);
		} catch (ErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(),HttpStatus.OK);
		}
		
	}
	
	@PutMapping(value="/updateQuantityCart")
	public ResponseEntity<Object> updateQuantityCart(
			@RequestParam("id") int productId,
			@RequestParam("sizeId") int sizeId,
			@RequestParam("colorId") int colorId,
			@RequestParam("quantity") int quantity,
			HttpSession httpSession
			){
		System.out.println("size:"+ sizeId);
		System.out.println("color:"+ colorId);
		System.out.println("quantity:"+ quantity);
		try {
			Cart cart=cartService.updateCart(productId, sizeId, colorId, quantity, "hau");
			return new ResponseEntity<>(cart,HttpStatus.OK);
		} catch (ErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@GetMapping(value="/showCart")
	public String showCart(Model model,
			HttpSession httpSession,
			HttpServletRequest request
			) throws ErrorException {
		if(request.getUserPrincipal()!=null) {
			String username=request.getUserPrincipal().getName();
			httpSession.setAttribute("username", username);
			User user=userService.getUser(username);
			if(user.getCart()!=null) {
			Cart cart=user.getCart();
			model.addAttribute("listCart",cart);
			}
			else
				model.addAttribute("error","Nothing in cart");
			}		
		else {
			model.addAttribute("error","Nothing in cart");
		}
		return "shoping-cart";
	}
}
