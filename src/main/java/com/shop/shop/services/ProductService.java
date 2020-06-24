package com.shop.shop.services;

import java.util.List;

import org.springframework.data.domain.Page;

import com.shop.shop.dtos.ProductDto;

public interface ProductService {

	public ProductDto getProduct(Long id);
	public void addProduct(ProductDto productDto);
	public void deleteProduct(Long id);
	public List<ProductDto> getProducts();
	public ProductDto modifyProduct(Long id, ProductDto product);
	public Page<ProductDto> getProductsPerPage(Integer page);
	public List<ProductDto> getProductbyName(String productName);

}
