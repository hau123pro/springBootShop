package com.laptrinhjava.demo.controller;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.laptrinhjava.demo.DTO.Order;
import com.laptrinhjava.demo.DTO.User;
import com.laptrinhjava.demo.exception.ErrorException;
import com.laptrinhjava.demo.service.OrderService;
import com.laptrinhjava.demo.service.UserService;

@Controller
public class OrderController {
	
	@Autowired
	OrderService orderService;
	
	@Autowired
	UserService userService;
	
	@PostMapping(value="/addToOrder")
	public ResponseEntity<Object> addToOrder( HttpSession httpSession){
		try {
			Order order=orderService.addToOrder(String.valueOf(
					httpSession.getAttribute("username")));
//			Order order=orderService.addToOrder("hau");
			return new ResponseEntity<>(order,HttpStatus.OK);
		} catch (ErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}
	@GetMapping(value="/showOrder")
	public String getOrder(
			Model model,
			HttpServletRequest request,
			HttpSession httpSession) throws ErrorException {
		if(request.getUserPrincipal()!=null) {
			httpSession.setAttribute("username", request.getUserPrincipal().getName());
			String username=request.getUserPrincipal().getName();
			User user=userService.getUser(username);
			if(user!=null) {
				model.addAttribute("user",user);
			}
			else model.addAttribute("error","Not have any order yet!");
		}
		else {
			model.addAttribute("error","Not have user");
		}
		return "show-order";
	}
	
	@GetMapping(value="/showOrderDetails")
	public String getOrderDetail(
			Model model,
			@RequestParam("orderId") int orderId,
			HttpServletRequest request
			) throws ErrorException {
		if(request.getUserPrincipal()!=null) {
			String username=request.getUserPrincipal().getName();
			Order order=orderService.getOrderById(username, orderId);
			if(order.getListOrderDetail().size()!=0)
			model.addAttribute("order",order);
			else 
				model.addAttribute("error","Order not valid");
		}
		else {
			model.addAttribute("error","Not have user");
		}
		return "order-detail";
	}
	@DeleteMapping(value="/deleteOrder")
	public ResponseEntity<Object> deleteOrder(
			@RequestParam("orderId") int orderId
			){
		try {
			Order order=orderService.deleteOrder(orderId);
			return new ResponseEntity<>("Success",HttpStatus.OK);
		} catch (ErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
		
	}
	
}
