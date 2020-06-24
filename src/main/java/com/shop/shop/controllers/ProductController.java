package com.shop.shop.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.shop.shop.dtos.ProductDto;
import com.shop.shop.services.ProductService;


@RestController
@RequestMapping("/product")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,  RequestMethod.DELETE, RequestMethod.PUT})
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	//GET
	@GetMapping("/{id}")
	public ProductDto getProduct(@Validated @PathVariable Long id) {
		return productService.getProduct(id);
	}
	

	//POST
	@PostMapping
	public void addProduct(@Validated @RequestBody ProductDto productDto) {
		productService.addProduct(productDto);
	};

	//DELETE
	@DeleteMapping("/{id}")
	public void deleteProduct(@PathVariable Long id) {
		productService.deleteProduct(id);
	}

	//GET
	@GetMapping
	public List<ProductDto> getProducts() {
		return productService.getProducts();
	}

	//PUT
	@PutMapping("/{id}")
	public ProductDto modifyProduct(@Validated @PathVariable Long id, @Validated @RequestBody ProductDto product) {
		return productService.modifyProduct(id, product);
	}
	
	//GET
	@GetMapping("/page/{page}")
	public Page<ProductDto> getProductsPerPage(@PathVariable Integer page) {
		return productService.getProductsPerPage(page);
	}

	//GET
	@GetMapping("/page/*/{productName}")
	public List<ProductDto> getProductbyName(@PathVariable String productName) {
		return productService.getProductbyName(productName);
	}

}
