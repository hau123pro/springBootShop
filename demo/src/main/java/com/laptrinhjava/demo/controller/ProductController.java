package com.laptrinhjava.demo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.laptrinhjava.demo.DTO.FilterPrice;
import com.laptrinhjava.demo.DTO.Product;
import com.laptrinhjava.demo.DTO.ProductDTO;
import com.laptrinhjava.demo.DTO.ProductManager;
import com.laptrinhjava.demo.DTO.User;
import com.laptrinhjava.demo.DTO.UserRegistration;
import com.laptrinhjava.demo.exception.ErrorException;
import com.laptrinhjava.demo.service.ColorService;
import com.laptrinhjava.demo.service.FilterPriceService;
import com.laptrinhjava.demo.service.ProductService;
import com.laptrinhjava.demo.service.SizeService;
import com.laptrinhjava.demo.service.UserService;

@Controller
public class ProductController {
	
	@Autowired
	UserService userService; 
	
	@Autowired
	ProductService productService;
	
	@Autowired
	ColorService colorService;
	
	@Autowired
	SizeService sizeService;
	
	@Autowired
	FilterPriceService filterPriceService;
	
	// Lấy all product
	@GetMapping(value="/products")
	public String allProducts(
				Model model,
				@RequestParam(value="page",defaultValue="1") String page) {
		Page<Product> pageProduct;
		try {
				pageProduct=productService.getProductPage(Integer.parseInt(page)-1);
				model.addAttribute("listProduct", pageProduct.getContent());
				model.addAttribute("countPage",pageProduct.getTotalPages());
				model.addAttribute("currentPage",Integer.parseInt(page));
				model.addAttribute("listColor",colorService.getAllColor());
				model.addAttribute("listSize",sizeService.getAllSize());
				model.addAttribute("listFilterPrice",filterPriceService.getAllFilterPrice());
		}
		catch (ErrorException e) {
			model.addAttribute("error", e.getMessage());
		}
		return "product";
	}
	
	@GetMapping(value="/search")
	public String searchProduct(
			Model model,
			@RequestParam(value="page",defaultValue="1") String page,
			@RequestParam(value="keyWord",defaultValue="") String keyWord,
			@RequestParam(value="colorId",required=false) Integer colorid,
			@RequestParam(value="sizeId",required=false) Integer sizeid,
			@RequestParam(value="minPrice",required=false) Double minPrice,
			@RequestParam(value="maxPrice",required=false) Double maxPrice
			) {
		Page<Product> pageProduct;
		try {
			pageProduct=productService.getProductByKeyWord(
															Integer.parseInt(page)-1,
															keyWord,
															colorid,
															sizeid,
															minPrice,
															maxPrice);
			model.addAttribute("listProduct", pageProduct.getContent());
			model.addAttribute("countPage",pageProduct.getTotalPages());
			model.addAttribute("currentPage",Integer.parseInt(page));
			model.addAttribute("keyWord",keyWord);
			model.addAttribute("listColor",colorService.getAllColor());
			model.addAttribute("listSize",sizeService.getAllSize());
			model.addAttribute("listFilterPrice",filterPriceService.getAllFilterPrice());
//			System.out.println("So san pham: "+pageProduct.getTotalElements());
//			System.out.println("Key word: "+model.getAttribute(keyWord)+" sdsds"+keyWord);
//			System.out.println("Current: "+Integer.parseInt(page));
//			System.out.println("Count: "+pageProduct.getTotalPages());
		} catch (ErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "search";
	}
	@GetMapping(value="/searchAPI")
	public ResponseEntity<Object> searchProductAPI(
			@RequestParam(value="id") Integer productId,
			@RequestParam(value="colorId",required=false) Integer colorid,
			@RequestParam(value="sizeId",required=false) Integer sizeid,
			@RequestParam(value="minPrice",required=false) Double minPrice,
			@RequestParam(value="maxPrice",required=false) Double maxPrice
			) {
		if(productId!=null) {
			try {
				Integer quantity=productService.getQuantityProductBySearchAPI(
																				productId,
																				colorid,
																				sizeid,
																				minPrice,
																				maxPrice
																			);
				if(quantity==null)
					return new ResponseEntity<>(0,HttpStatus.OK);
				return new ResponseEntity<>(quantity,HttpStatus.OK);
			} catch (ErrorException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
			}
		}
		System.out.print("null");
		return new ResponseEntity<>("Not have Id product",HttpStatus.BAD_REQUEST);
		
	}
	
	// Lấy ra chi tiết product theo id
	@GetMapping("/productDetail-{id}")
	public String productDetail(
			@PathVariable(value="id") String productId,
			Model model,
			HttpServletRequest request,
			HttpSession httpSession) {
		System.out.print(productId);
		if(request.getUserPrincipal()!=null) {
			httpSession.setAttribute("username", request.getUserPrincipal().getName());
			}
		if(productId!=null) {
			try {
				Product productByid=productService.getProductById(Integer.parseInt(productId));
				model.addAttribute("productByid", productByid);
			}
			catch (ErrorException e) {
				model.addAttribute("error", e.getMessage());
			}
		}
		return "product-detail";
	}
	
	@GetMapping("/productDetail/getQuantityByColorSize")
	public ResponseEntity<Object> getQuantityByColorId_SizeId(
			@RequestParam("id") int productId,
			@RequestParam("sizeId") int sizeId,
			@RequestParam("colorId") int colorId
			) throws ErrorException {
			int productCount=productService.getProductQuantity_ByColorSize(productId, colorId, sizeId);
			System.out.println(productCount);
			if(productCount>-1) {
				return new ResponseEntity<>(productCount, HttpStatus.OK);
			}
		return new ResponseEntity<>(-1, HttpStatus.BAD_REQUEST);
		
	}
	
	
	
	
	@GetMapping("/productDetail/getQuantityAll")
	public ResponseEntity<Object> getQuantityAll(
			@RequestParam("id") int productId
			) {
			try {
				int productCount=productService.getProductQuantityAll(productId);
				System.out.println(productCount);
					return new ResponseEntity<>(productCount, HttpStatus.OK);
			}
			catch (ErrorException e) {
			}
		return new ResponseEntity<>(-1, HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping("/admin/Product")
	public String productManager(
			Model model,
			@RequestParam(value="page",defaultValue="1") String page,
			@RequestParam(value="keyWord",defaultValue="") String keyWord,
			@RequestParam(value="colorId",required=false) Integer colorid,
			@RequestParam(value="sizeId",required=false) Integer sizeid,
			@RequestParam(value="minPrice",required=false) Double minPrice,
			@RequestParam(value="maxPrice",required=false) Double maxPrice
			) {
		Page<Product> pageProduct;
		List<ProductManager> listItem;
		ProductDTO product=new ProductDTO();
		model.addAttribute("productDto",product);
		try {
			pageProduct=productService.getProductByAdminKeyWord(
															Integer.parseInt(page)-1, 
															keyWord,
															colorid,
															sizeid,
															minPrice,
															maxPrice
														);		
			// Đưa list DTO ProductManager cho trang admin Product
			listItem=productService.getProductManagerAll(pageProduct.getContent());
			System.out.println(pageProduct.getContent().size());
			model.addAttribute("listProduct", listItem);
			model.addAttribute("countPage",pageProduct.getTotalPages());
			model.addAttribute("currentPage",Integer.parseInt(page));
			model.addAttribute("listColor",colorService.getAllColor());
			model.addAttribute("listSize",sizeService.getAllSize());
				
		}
		catch (ErrorException e) {
			model.addAttribute("error", e.getMessage());
		}
		return "product-manager";
	}
	@PutMapping("/admin/updateProduct")
	public String updateProduct(
			@ModelAttribute ProductDTO productDto,
			BindingResult result,
			Model model,
			RedirectAttributes redirectAttributes
			) {
		Product product;
		if(result.hasErrors()) {
			result.getFieldErrors()
			.stream()
			.forEach(f -> System.out.println(f.getField() + ": " + f.getDefaultMessage()));
			redirectAttributes.addFlashAttribute("error", "Not successful!!");
			return "redirect:/admin/Product";
		}
		try {
			product=productService.updateProduct(productDto);
			redirectAttributes.addFlashAttribute("success", "Update product successful! ");
			return "redirect:/admin/Product";
			
		} catch (ErrorException e) {
			System.out.println("loi~ "+e.getMessage());
			redirectAttributes.addFlashAttribute("error", e.getMessage());
			return "redirect:/admin/Product";
		}
		
	}
	@PostMapping("/admin/addProduct")
	public String addProduct(
			@ModelAttribute ProductDTO u,
			BindingResult result,
			Model model,
			RedirectAttributes redirectAttributes
			) {
		Product product;
		if(result.hasErrors()) {
			result.getFieldErrors()
			.stream()
			.forEach(f -> System.out.println(f.getField() + ": " + f.getDefaultMessage()));
			redirectAttributes.addFlashAttribute("error", "Not successful!!");
			return "redirect:/admin/Product";
		}
		try {
			product=productService.addProduct(u);
			redirectAttributes.addFlashAttribute("success", "Add product successful! ");
			return "redirect:/admin/Product";
		} catch (ErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("error", e.getMessage());
			return "redirect:/admin/Product";
		}
	}
	@PutMapping("/admin/updateStatusProduct")
	public ResponseEntity<Object> updateStatusProduct(
			@RequestParam("id") Integer productId,
			@RequestParam("status") String status
			){
		Product product;
		try {
			product=productService.updateStatusProduct(productId, status);
			return new ResponseEntity<>(product,HttpStatus.OK);
		} catch (ErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}
}
