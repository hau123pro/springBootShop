package com.laptrinhjava.demo.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.swing.text.html.Option;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.laptrinhjava.demo.DTO.Cart;
import com.laptrinhjava.demo.DTO.CartItem;
import com.laptrinhjava.demo.DTO.Order;
import com.laptrinhjava.demo.DTO.OrderDetail;
import com.laptrinhjava.demo.DTO.ProductColorSize;
import com.laptrinhjava.demo.DTO.User;
import com.laptrinhjava.demo.exception.ErrorException;
import com.laptrinhjava.demo.repository.CartItemRepository;
import com.laptrinhjava.demo.repository.CartRepository;
import com.laptrinhjava.demo.repository.OrderDetailRepository;
import com.laptrinhjava.demo.repository.OrderRepository;
import com.laptrinhjava.demo.repository.ProductFillRepository;

@Service
public class OrderService {
	
	@Autowired
	UserService userService;
	
	@Autowired
	OrderRepository orderRepository;
	
	@Autowired
	OrderDetailRepository orderDetailRepo;
	
	@Autowired
	CartRepository cartRepository;
	
	@Autowired
	CartItemRepository cartItemRepository;
	
	@Autowired
	ProductFillRepository prFillRepo;
	
	@Transactional
	public Order addToOrder(String username) throws ErrorException {
		User user=userService.getUser(username);
		Cart cart=user.getCart();
		Order order=new Order();
		order.setUser(user);
		order.setStatus("On Process");
		order.setTotalPrice(cart.getTotalPrice());
		order.setQuantity(cart.getQuantiy());
		long millis=System.currentTimeMillis();   
	    // creating a new object of the class Date  
	    java.sql.Date date = new java.sql.Date(millis);  
	    order.setDate_created(date);
	    Set<OrderDetail> Items = new HashSet<OrderDetail>();
		Set<CartItem> cartItems = new HashSet<CartItem>();
		List<ProductColorSize> productSCItem=new ArrayList<>();
	    for(CartItem i:cart.getListCartItem()) {
	    	ProductColorSize productColorSize=prFillRepo.findQuantityByProductColorSize(
	    			i.getProduct().getProductID(),
	    			i.getColor().getColorID(),
	    			i.getSize().getSizeID());
	    	if(i.getQuantity()<=productColorSize.getQuantity()) {
		    	OrderDetail detail=new OrderDetail();
		    	detail.setColor(i.getColor());
		    	detail.setOrder(order);
		    	detail.setProduct(i.getProduct());
		    	detail.setSize(i.getSize());
		    	detail.setQuantity(i.getQuantity());
		    	detail.setTotalPrice(i.getTotalPrice());
		    	Items.add(detail);
		    	cartItems.add(i);
		    	productSCItem.add(productColorSize);
	    	}
	    	else throw new ErrorException("Sản phẩm: "
	    								+i.getProduct().getName()
	    								+" chỉ còn lại số lượng là "
	    								+productColorSize.getQuantity());
	    }
	    cartItems.forEach((item)->{
	    	for(ProductColorSize i :productSCItem) {
	    		if(i.getProduct().getProductID()==item.getProduct().getProductID()
    				&&i.getSize().getSizeID()==item.getSize().getSizeID()
    				&&i.getColor().getColorID()==item.getColor().getColorID()) {
	    			i.setQuantity(i.getQuantity()-item.getQuantity());
	    			prFillRepo.save(i);
	    			break;
	    		}
	    	}
	    	cartItemRepository.deleteByKey(item.getCartItemID());
	    });
	    order.setListOrderDetail(Items);
	    orderRepository.save(order);
	    cartRepository.deleteByKey(cart.getCartId());
		return order;
	}
	
	public Set<Order> getListOrderByUserName(String username) throws ErrorException{
		User user=userService.getUser(username);
		Set<Order> orderItems = user.getListOrder();
		return orderItems;
	}
	
	public Order getOrderById(String username,int id) throws ErrorException{
		User user=userService.getUser(username);
		Order orderItems = user.getListOrder().stream()
				.filter(p->p.getID()==id)
				.findAny().orElse(null);
		return orderItems;
	}
	@Transactional
	public Order deleteOrder(int OrderId) throws ErrorException {
		Optional<Order>  orderItem=orderRepository.findById(OrderId);
		Order order=orderItem.orElseThrow(()-> new ErrorException("Order not found"));
		Set<OrderDetail> OrderItems =order.getListOrderDetail();
		if(OrderItems.size()>0) {
			OrderItems.stream().forEach((item)->{
					orderDetailRepo.deleteByKey(item.getID());
			});
		}
		orderRepository.deleteByKey(OrderId);
		return order;
	}
	
}
